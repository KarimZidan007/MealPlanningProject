package DataBase.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import DataBase.controller.MealDateDao;
import Model.MealDate;

@Database(entities = {MealDate.class}, version = 1)
public abstract class calAppDataBase extends RoomDatabase {
    private static calAppDataBase dataBaseInstance = null;
    public abstract MealDateDao getDateMealsDao();
    public static synchronized calAppDataBase getDbInstance(Context context) {
        if (dataBaseInstance == null)
        {
            dataBaseInstance = Room.databaseBuilder( context,calAppDataBase.class,"MealDateDAO").fallbackToDestructiveMigration().build();
        }
        return dataBaseInstance;
    }
}
