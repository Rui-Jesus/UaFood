package com.example.masterdetaildemo.model;


public class MealCourse {

    private int courseOrder;
    private String foodOptionDescription;

    public int getMealCourseOrder() {
        return courseOrder;
    }

    public String getFoodOptionDescription() {
        return foodOptionDescription;
    }

    public MealCourse(int mealCourseOrder, String foodOption) {
        this.courseOrder = mealCourseOrder;
        this.foodOptionDescription = foodOption;
    }
}

