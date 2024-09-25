package Network.Model;

import android.content.Context;

import java.util.List;

import Network.Model.NetworkCallback.NetworkCallback;
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
    private Retrofit retrofit = null;
    private static MealsRemoteDataSource MealsRemoteDataSourceClient = null;
    private Context context;
    public static List<Meal> pojoElements;
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
    //method provide only one retrofitclient
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

}

class mealsResponse
{
    List<Meal> meals;
}





