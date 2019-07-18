package com.shawnrucker.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shawnrucker.demo.models.Role;
import com.shawnrucker.demo.models.User;
import com.shawnrucker.demo.repositories.RoleRepository;
import com.shawnrucker.demo.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Save a new user in the persistent storage
     * @param user The User object to save
     */
    public void saveUser(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Register New Users
     * This function is used by both registered and unregistered users
     * @param user
     */
    public void registerUser(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User userExists = findUserByUserName(user.getUsername());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " is already on file");
        }

        // Current user is authenticated and trying to register another user
        // This is only allowed by Admins and User Managers
        if(!isUserAnonymous() && !user.getRoles().isEmpty()) {

            // Verify the user is an administrator or user manager
            if(!isUserAdmin() && !isUserUserManager()) {
                throw new SecurityException("Invalid access to preform this action");
            }

            // Verify the roles are valid
            if(!isRolesValid(user.getRoles())){
                throw new SecurityException("Provided roles are invalid");
            }

            // Verify that the role being set is not above the role access of the current user
            if(isUserUserManager() && user.containsRole("ADMIN")){
                // User is attempting to create an ADMIN role while they themselves are only User Manager
                throw new SecurityException("User Managers are not allowed to create Admin Accounts");
            }

        }else{
            // Standard registration by new user
            // Even if role information was passed in blow it away and use USER
            Role userRole = roleRepository.findByRole("USER");
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }
        saveUser(user);
    }

    /**
     * Verifies that all Roles provided are valid (They exist in the persistent storage)
     * @param roles A Set of roles to check
     * @return True if all roles are valid / False otherwise
     */
    public boolean isRolesValid(Set<Role> roles) {
        for(Role rl: roles) {
            if(roleRepository.findByRole(rl.getRole()) == null){
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the User object of the current logged in user
     * @return The User Object of the current logged in user
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if(user == null) {
            throw new UsernameNotFoundException("Logged in User is Invalid");
        }
        return user;
    }

    /**
     * Is the provided UserId valid.  Valid means the uer exists in the persistent storage
     * @param userId The UUID userID
     * @return True is the user is found / False otherwise
     */
    public boolean isUserIdValid(String userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Is the current user a member of the ADMIN role
     * @return True if the user is a member of the ADMIN role / False otherwise
     */
    public boolean isUserAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Is the current user a member of the USERMANAGER role
     * @return True if the user is a member of the USERMANAGER role / False otherwise
     */
    public boolean isUserUserManager() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("USERMANAGER"));
    }

    /**
     * Determines if the current user account is Anonymous
     * @return True if the account is Anonymous / False otherwise
     */
    public boolean isUserAnonymous() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
    }

    /**
     * Loads a user object based on a provided unique username
     * @param username The unique username assigned by the user (Normally an email address)
     * @return UserDetail object containing the user details
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }else{
            throw new UsernameNotFoundException("Provided username not found");
        }
    }

    /**
     * Gets a list of Granted Authorities from a provided set of Roles
     * @param userRoles The List of Roles to convert
     * @return The converted list of Roles
     */
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    /**
     * Combines a User and Authorities into a UserDetail object
     * @param user         The User Object to include
     * @param authorities  The Authorities to include
     * @return A UserDetails object containing the user and authorities
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    /**
     * Password Encoder
     * @return a BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
