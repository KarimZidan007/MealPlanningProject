package DataBase.Model;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Meal;
import Model.MealDate;

public interface localSrcInterface {
    LiveData<List<Meal>>  getAllFavouriteMeals();
    void insertFavMeal(Meal meal);
    void deleteFavMeal(Meal meal);
    LiveData<List<MealDate>> getMealsForDate(String date);
    void insertPlannedMeal(MealDate mealDate);
    LiveData<List<MealDate>> getAllPlannedMeals();
    void deletePlannedMeal(MealDate mealDate);

}
