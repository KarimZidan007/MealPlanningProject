package Network.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Category;
import Model.Meal;
import Network.Model.NetworkCallback.NetworkCallback;
import Network.Model.Responses.CategoriesResponse;
import Network.Model.Responses.CountryResponse;
import Network.Model.Responses.IngredientsResponse;
import Network.Model.Responses.mealsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MealsRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private mealsServices service;
    private static MealsRemoteDataSource MealsRemoteDataSourceClient = null;
    private MealsRemoteDataSource() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         service = retrofit.create(mealsServices.class);
    }
    public void getRandomMeal(NetworkCallback.NetworkCallbackRandom networkCallback) {
        Call<mealsResponse> call = service.getRandomMeals();
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultRandom(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultRandom(throwable.getStackTrace().toString());
            }
        });
    }
    public void searchMealsByFirstLetter(char firstLetter, NetworkCallback.NetworkCallbackFirstChar networkCallback) {
        Call<mealsResponse> call = service.searchMealsByFirstLetter(firstLetter);
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultFirstChar(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultFirstChar(throwable.getMessage());
            }
        });
    }
    public void searchMealsByName(String Name, NetworkCallback.NetworkCallbackByName networkCallback) {
        Call<mealsResponse> call = service.searchMealsByName(Name);
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultByName(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultByName(throwable.getMessage());
            }
        });
    }
    public void getSearchCategories( NetworkCallback.NetworkCallbackGetCateogries networkCallback) {
        Call<CategoriesResponse> call = service.getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> catResponse) {
                networkCallback.onSuccessResultgetCategories(catResponse.body().categories);
            }
            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                networkCallback.onFailureResultgetCategories(throwable.getMessage());
            }
        });
    }
    public void getSearchCountries( NetworkCallback.NetworkCallbackGetCountries networkCallback) {
        Call<CountryResponse> call = service.getCountries();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                networkCallback.onSuccessResultgetCountries(response.body().meals);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable throwable) {
                networkCallback.onFailureResultgetCountries(throwable.getMessage());
            }
        });
    }
    public void getSearchIngredients( NetworkCallback.NetworkCallbackGetIngredients networkCallback) {
        Call<IngredientsResponse> call = service.getIngredients();
        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                networkCallback.onSuccessResultGetIngredients(response.body().meals);
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultGetIngredients(throwable.getMessage());
            }
        });
    }
    public void reqFilterByIngredient(String ingrident, NetworkCallback.NetworkCallbackFilterByIngredient networkCallback) {
        Call<mealsResponse> call = service.filterByIngredient(ingrident);
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultFilterByIngredients(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultFilterByIngredients(throwable.getMessage());
            }
        });
    }
    public void reqFilterByCateogry(String category, NetworkCallback.NetworkCallbackFilterByCateogry networkCallback) {
        Call<mealsResponse> call = service.filterByCategory(category);
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultFilterByCateogries(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultFilterByCateogries(throwable.getMessage());
            }
        });
    }
    public void reqFilterByCountry(String country, NetworkCallback.NetworkCallbackFilterByCountry networkCallback) {
        Call<mealsResponse> call = service.filterByCountry(country);
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResultFilterByCountries(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResultFilterByCountries(throwable.getMessage());
            }
        });
    }
    public static MealsRemoteDataSource getRemoteSrcClient() {
        if(MealsRemoteDataSourceClient == null)
        {
            MealsRemoteDataSourceClient = new MealsRemoteDataSource();
        }
        return MealsRemoteDataSourceClient;
    }
}

interface mealsServices
{
    @GET("random.php")
    Call<mealsResponse> getRandomMeals();

    @GET("search.php")
    Call<mealsResponse> searchMealsByFirstLetter(@Query("f") char firstLetter);

    @GET("search.php")
    Call<mealsResponse> searchMealsByName(@Query("s") String Name);

    @GET("categories.php")
    Call<CategoriesResponse> getCategories();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredients();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountries();

    @GET("filter.php")
    Call<mealsResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<mealsResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<mealsResponse> filterByCountry(@Query("a") String area);

}





