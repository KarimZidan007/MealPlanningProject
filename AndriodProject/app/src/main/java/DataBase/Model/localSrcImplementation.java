package DataBase.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Model.Meal;
import Model.MealDate;

public class localSrcImplementation implements  localSrcInterface{
    MealDAO localSrc;
    MealDateDao plannerSrc;
    Context context;
    public localSrcImplementation(MealDateDao plannerSrc,MealDAO localSrc, Context context)
    {
        this.context=context;
        this.localSrc=localSrc;
        this.plannerSrc = plannerSrc;
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
        return plannerSrc.getAllMealsForDate();
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
