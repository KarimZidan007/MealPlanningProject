package DataBase.Model;

import androidx.lifecycle.LiveData;

import java.util.List;

import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Model.Meal;
import Model.MealDate;

public class localSrcImplementation implements  localSrcInterface{
    MealDAO localSrc;
    MealDateDao plannerSrc;

    public localSrcImplementation(MealDAO localSrc)
    {
        this.localSrc=localSrc;
    }

    public localSrcImplementation(MealDateDao plannerSrc)
    {
        this.plannerSrc=plannerSrc;
    }

    @Override
    public LiveData<List<Meal>> getAllFavouriteMeals() {
        return localSrc.getAllMeals();
    }

    @Override
    public void insertFavMeal(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                localSrc.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void deleteFavMeal(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                localSrc.deleteMeal(meal);
            }
        }.start();

    }

    @Override
    public LiveData<List<MealDate>> getMealsForDate(String date) {
        return    plannerSrc.getMealsForDate(date);

    }

    @Override
    public void insertPlannedMeal(MealDate mealDate) {
        new Thread(){
            @Override
            public void run() {
                plannerSrc.insertPlannedMeal(mealDate);
            }
        }.start();

    }

    @Override
    public LiveData<List<MealDate>> getAllPlannedMeals() {
        return null;
    }

    @Override
    public void deletePlannedMeal(MealDate mealDate) {
        new Thread(){
            @Override
            public void run() {
                plannerSrc.deletePlannedMeal(mealDate);
            }
        }.start();

    }
}
