package com.shawnrucker.demo.controllers;

import com.shawnrucker.demo.models.Meal;
import com.shawnrucker.demo.models.User;
import com.shawnrucker.demo.models.nutritionix.Food;
import com.shawnrucker.demo.models.nutritionix.Nutritionix;
import com.shawnrucker.demo.models.nutritionix.PossibleFood;
import com.shawnrucker.demo.models.nutritionix.StandardizedResult;
import com.shawnrucker.demo.repositories.MealRepository;
import com.shawnrucker.demo.repositories.UserRepository;
import com.shawnrucker.demo.services.CustomUserDetailsService;
import com.shawnrucker.demo.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/meal")
public class MealController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealService mealService;

    // Allow for MM/dd/yyyy date format on input to the API
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true, 10));
    }

    @SuppressWarnings("rawtypes")
    @CrossOrigin
    @GetMapping("/mealmanagement")
    public ResponseEntity getMeals(@RequestParam Date dateToPull) {
        return ok(mealService.getMealsForGivenDay(dateToPull));
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/mealmanagement/{mealId}")
    public ResponseEntity getMealsByUser(@PathVariable String mealId) {
        return ok(mealService.findMealByMealId(mealId));
    }

    /**
     * Adds a new meal
     * @param meal  - The Meal record itself
     * @return A standard Response Entity
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/mealmanagement")
    public ResponseEntity saveMeal(@RequestBody Meal meal) {
        // If a meal comes in with an existing ID it is an edit and should be sent via PATCH
        Meal mealExists = mealService.findMealByMealId(meal.getId());
        if(mealExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meal already on file, use Patch to modify existing meal");
        }

        // If a userId is provided and does match the current logged in user then the user
        // must be an admin
        User currentUser = customUserDetailsService.getLoggedInUser();

        if(meal.getUserid() != null && !customUserDetailsService.isUserAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only Admins are allowed to save meals for specific users");
        }

        // Set the owner of the meal
        if(meal.getUserid() == null) meal.setUserid(currentUser.getId());

        // If a userId was provided is it valid?
        if(!customUserDetailsService.isUserIdValid(meal.getUserid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided User is not valid");
        }

        mealService.saveMeal(meal);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "meal saved successfully");
        model.put("id", meal.getId());
        return ok(model);
    }

    /**
     * Removed a Meal
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/mealmanagement")
    public ResponseEntity deleteMeal(@RequestParam String id) {
       try {
           mealService.deleteMeal(id);
       } catch(Exception e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meal ID provided is invalid.  Unable to delete.");
       }
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Meal Deleted Successfully");
        return ok(model);
    }

    /**
     * Updates an existing meal with new information
     * @param meal The meal item containing the updates
     * @return Standard ResponseEntity
     */
    @SuppressWarnings("rawtypes")
    @PatchMapping("/mealmanagement")
    public ResponseEntity updateMeal(@RequestBody Meal meal) {
        // Verify that the requested meal exists
        if(!mealService.doesMealExist(meal.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meal ID provided is invalid.");
        }

        // Get the Meal object itself
        Meal originalMealRecord = mealService.findMealByMealId(meal.getId());

        // Does the given mealID belong the current user || is the user an Admin
        if(!mealService.doesMealBelongToUser(originalMealRecord) && !customUserDetailsService.isUserAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meal ID provided does not belong the current user.");
        }

        meal.setUserid(originalMealRecord.getUserid());
        mealService.updateMeal(meal);

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Meal Updated Successfully");
        return ok(model);
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/calorielookup")
    public ResponseEntity getCalorieCount(@RequestParam String mealItemToLookup) {

        try {
            final String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

            // Add headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");

            // Add Parameters
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("Content-Type", "application/json")
                    .queryParam("x-app-id", "0b11c866")
                    .queryParam("x-app-key", "aa9d2ef6a32d615d9a4154514d019f2b");

            // Add body
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("query", mealItemToLookup);

            // Put it all together
            final HttpEntity<?> entity = new HttpEntity<Object>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Nutritionix> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, entity, Nutritionix.class);

            // Standardise the result (Remove all the stuff we dont care about)
            StandardizedResult standardizedResult = new StandardizedResult();
            standardizedResult.setPossibleFoods(
                    response.getBody().getFoods().stream().map(convertFood)
                            .collect(Collectors.toList()));

            return ok(standardizedResult);
        } catch(Exception exc) {
            Map<Object, Object> model = new HashMap<>();
            model.put("message", "No food items matched you request");
            return ok(model);
        }
    }

    /**
     * Converter that converts Nutritonix food list to internal standardised object
     */
    Function<Food, PossibleFood> convertFood = t -> {
        PossibleFood possibleFood = new PossibleFood(t.getFoodName(), t.getNfCalories());
        return possibleFood;
    };

}
