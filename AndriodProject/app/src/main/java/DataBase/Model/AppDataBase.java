package DataBase.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import DataBase.controller.MealDAO;
import Model.Meal;


@Database(entities = {Meal.class},version=2)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase dataBaseInstance = null;
    public abstract MealDAO getMealsDao();
    public static synchronized AppDataBase getDbInstance(Context context) {
        if (dataBaseInstance == null)
        {
            dataBaseInstance = Room.databaseBuilder( context,AppDataBase.class,"MealDAO").fallbackToDestructiveMigration().build();
        }
        return dataBaseInstance;
    }
}
