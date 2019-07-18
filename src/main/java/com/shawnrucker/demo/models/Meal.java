package com.shawnrucker.demo.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Document
public class Meal {

    @NotNull
    @Id
    private String id = UUID.randomUUID().toString();

    @Field
    @NotNull
    private String userid;

    @Field
    private Date filterdate;

    @Field
    private String mealtime;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private List<MealItem> mealItems = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getFilterdate() {
        return filterdate;
    }

    public String getMealtime() {
        return mealtime;
    }

    public void setMealtime(String mealtime) {
        this.mealtime = mealtime;
        String pattern = "MM/dd/yyyy hh:mm";
        DateTime actualMealTime = DateTime.parse(mealtime, DateTimeFormat.forPattern(pattern));
        this.filterdate = actualMealTime.toDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MealItem> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<MealItem> mealItems) {
        this.mealItems = mealItems;
    }

}
