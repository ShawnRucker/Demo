package com.shawnrucker.demo.repositories;

import com.shawnrucker.demo.models.Meal;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Date;
import java.util.List;

public interface MealRepository extends CouchbaseRepository<Meal, String> {
    Meal findMealById(String id);
    List<Meal> findAllByUserid(String id);
}
