package com.shawnrucker.demo.services;

import com.shawnrucker.demo.models.Meal;
import com.shawnrucker.demo.models.User;
import com.shawnrucker.demo.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {

    SimpleDateFormat filterDateFormat = new SimpleDateFormat("MMddyyyy");

    @Autowired
    MealRepository mealRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /**
     * Find a meal based on its ID
     * @param mealId The Meal ID to find
     * @return The Meal object based on the provided ID
     */
    public Meal findMealByMealId(String mealId) {
        Optional<Meal> meal = mealRepository.findById(mealId);
        if(meal.isPresent()) return meal.get();
        return null;
    }

    public List<Meal> getMealsForGivenDay(Date dateToPull) {
       String userID = customUserDetailsService.getLoggedInUser().getId();
       return getMealsForGivenDay(userID, dateToPull);
    }

    public List<Meal> getMealsForGivenDay(String userId, Date dateToPull) {
        List<Meal> mealList = mealRepository.findAllByUserid(userId);
        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        String filterDate = dateformat.format(dateToPull);
        return mealList.stream().filter(f->dateformat.format(f.getFilterdate()).equals(filterDate)).collect(Collectors.toList());
    }

    /**
     * Saves a new Meal to the database
     * @param meal The Meal object to save to the document persistent storage
     */
    public void saveMeal(Meal meal) {
        mealRepository.save(meal);
    }

    /**
     * Updates an already existing meal
     * @param meal The Meal record containing the updates
     */
    public void updateMeal(Meal meal) {
        mealRepository.save(meal);
    }

    /**
     * Removes a meal for the database
     * @param mealId The MealID to remove
     * @throws Exception
     */
    public void deleteMeal(String mealId) throws Exception {
        Optional<Meal> meal = mealRepository.findById(mealId);
        if(!meal.isPresent()) {
            throw new Exception("mealID Provided is invalid");
        }
        mealRepository.delete(meal.get());
    }

    /**
     * Checks to see if a given meal is on file based on the ID
     * @param mealId The MealID to verify
     * @return True if the Meal is on file / False otherwise
     */
    public boolean doesMealExist(String mealId) {
        return mealRepository.existsById(mealId);
    }

    /**
     * Compares the userID assigned to the given meal to the current logged in user
     * @param meal The Meal to compare to
     * @return True if the meal belongs to the user / False otherwise
     */
    public boolean doesMealBelongToUser(Meal meal) {
      User currentUser =  customUserDetailsService.getLoggedInUser();
      return currentUser.getId().equals(meal.getUserid());
    }

}
