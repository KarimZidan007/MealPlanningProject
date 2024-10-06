package Repository;


import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import DataBase.Model.localSrcImplementation;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;

public class DataSrcRepository implements MealsRepository {
    private MealsRemoteDataSource remoteSrc;
    private MealDAO localSrc;
    private MealDateDao plannerSrc;
    public DataSrcRepository(MealsRemoteDataSource remoteSrc)
    {
        this.remoteSrc=remoteSrc;
    }
    public DataSrcRepository(MealDAO localSrc)
    {
        this.localSrc=localSrc;
    }
    public DataSrcRepository(MealDateDao plannerSrc)
    {
        this.plannerSrc=plannerSrc;
    }


    @Override
    public void getRandomMeal(NetworkCallback.NetworkCallbackRandom networkCallback) {
        remoteSrc.getRandomMeal( networkCallback);
    }

    @Override
    public void getMealsByFirstChar(char firstChar ,NetworkCallback.NetworkCallbackFirstChar networkCallback) {
        remoteSrc.searchMealsByFirstLetter(firstChar,networkCallback);
    }

    @Override
    public void getMealsByName(String Name,NetworkCallback.NetworkCallbackByName networkCallback ) {
        remoteSrc.searchMealsByName(Name, networkCallback);
    }

    @Override
    public void getMealsCategories(NetworkCallback.NetworkCallbackGetCateogries networkCallBack) {
        remoteSrc.getSearchCategories(networkCallBack);
    }

    @Override
    public void getMealsCountries(NetworkCallback.NetworkCallbackGetCountries networkCallBack) {
        remoteSrc.getSearchCountries(networkCallBack);
    }

    @Override
    public void getMealsIngredients(NetworkCallback.NetworkCallbackGetIngredients networkCallBack) {
        remoteSrc.getSearchIngredients(networkCallBack);
    }

    @Override
    public void FilterMealsByIngredient(String ingredient, NetworkCallback.NetworkCallbackFilterByIngredient networkCallBack) {
        remoteSrc.reqFilterByIngredient(ingredient,networkCallBack);
    }

    @Override
    public void FilterMealsByCateogry(String category, NetworkCallback.NetworkCallbackFilterByCateogry networkCallBack) {
        remoteSrc.reqFilterByCateogry(category,networkCallBack);
    }

    @Override
    public void FilterMealsByCountry(String country, NetworkCallback.NetworkCallbackFilterByCountry networkCallback) {
        remoteSrc.reqFilterByCountry(country,networkCallback);
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return localSrc.getAllMeals();
    }

    @Override
    public void delFavMeal( Meal meal) {
        new Thread(){
            @Override
            public void run() {
                localSrc.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertFavMeal( Meal meal) {
        new Thread(){
            @Override
            public void run() {
                localSrc.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public LiveData<List<MealDate>> getPlannedMeals(String date) {
        return plannerSrc.getMealsForDate(date);
    }

    @Override
    public void delPlannedMeal(MealDate meal) {
        new Thread(){
            @Override
            public void run() {
                plannerSrc.deletePlannedMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertPlannedMeal(MealDate meal) {
        new Thread(){
            @Override
            public void run() {
                plannerSrc.insertPlannedMeal(meal);
            }
        }.start();
    }
}
