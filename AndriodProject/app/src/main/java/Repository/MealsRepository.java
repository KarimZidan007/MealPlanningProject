package Repository;


import androidx.lifecycle.LiveData;

import java.util.List;

import DataBase.controller.MealDAO;
import Model.Meal;
import Network.Model.NetworkCallback.NetworkCallback;
import Network.Model.Responses.mealsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface MealsRepository {

     void getRandomMeal(NetworkCallback.NetworkCallbackRandom networkCallBack);
     void getMealsByFirstChar(char firstChar,NetworkCallback.NetworkCallbackFirstChar networkCallBack);
     void getMealsByName(String Name,NetworkCallback.NetworkCallbackByName networkCallBack);
     void getMealsCategories(NetworkCallback.NetworkCallbackGetCateogries networkCallBack);
     void getMealsCountries(NetworkCallback.NetworkCallbackGetCountries networkCallBack);
     void getMealsIngredients(NetworkCallback.NetworkCallbackGetIngredients networkCallBack);
     void FilterMealsByIngredient(String ingredient ,NetworkCallback.NetworkCallbackFilterByIngredient networkCallBack);
     void FilterMealsByCateogry(String category ,NetworkCallback.NetworkCallbackFilterByCateogry networkCallBack);
     void FilterMealsByCountry(String country, NetworkCallback.NetworkCallbackFilterByCountry networkCallback) ;
      LiveData<List<Meal>> getFavMeals( );
      void delFavMeal(Meal meal);
      void insertFavMeal(Meal meal);

}
