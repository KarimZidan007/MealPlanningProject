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
    private localSrcImplementation localSrc;
    public DataSrcRepository(MealsRemoteDataSource remoteSrc , localSrcImplementation localSrc)
    {
            this.remoteSrc=remoteSrc;
            this.localSrc=localSrc;
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
        return localSrc.getAllFavouriteMeals();
    }

    @Override
    public void delFavMeal( Meal meal) {

        localSrc.deleteFavMeal(meal);
    }


    @Override
    public void insertFavMeal( Meal meal) {

        localSrc.insertFavMeal(meal);
    }

    @Override
    public LiveData<List<MealDate>> getPlannedMeals(String date) {
        return localSrc.getMealsForDate(date);
    }

    @Override
    public void delPlannedMeal(MealDate meal) {

        localSrc.deletePlannedMeal(meal);
    }

    @Override
    public void insertPlannedMeal(MealDate meal) {

        localSrc.insertPlannedMeal(meal);
    }
}
