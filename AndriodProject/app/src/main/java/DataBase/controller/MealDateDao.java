package DataBase.controller;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;


import java.util.List;

import Model.MealDate;

@Dao
public interface MealDateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlannedMeal(MealDate mealDate);

    @Query("SELECT * FROM meal_date_table WHERE date = :date")
    LiveData<List<MealDate>> getMealsForDate(String date);

    @Delete
    void deletePlannedMeal(MealDate mealDate);

    @Query("SELECT * FROM meal_date_table")
    LiveData<List<MealDate>> getAllMealsForDate();
}

