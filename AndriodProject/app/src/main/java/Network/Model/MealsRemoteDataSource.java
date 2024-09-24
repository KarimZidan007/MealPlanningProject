package Network.Model;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MealsRemoteDataSource {
    public static final String BASE_URL = "www.themealdb.com/api/json/v1/1/";
    private mealsServices service;
    private Retrofit retrofit = null;
    private static MealsRemoteDataSource retroFitClient = null;
    private Context context;
    public static List<Meal> pojoElements;
    private MealsRemoteDataSource() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         service = retrofit.create(mealsServices.class);
    }

    public void getDataOverNetwork(NetworkCallback networkCallback) {
        Call<mealsResponse> call = service.getMeals();
        call.enqueue(new Callback<mealsResponse>() {
            @Override
            public void onResponse(Call<mealsResponse> call, Response<mealsResponse> response) {
                networkCallback.onSuccessResult(response.body().meals);
            }

            @Override
            public void onFailure(Call<mealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getStackTrace().toString());
            }
        });
    }
    //method provide only one retrofitclient
    public static MealsRemoteDataSource getRetrofitClient() {
        if(retroFitClient == null)
        {
             retroFitClient = new MealsRemoteDataSource();
        }
        return retroFitClient;
    }
}

interface mealsServices
{
    @GET("random.php")
    Call<mealsResponse> getMeals();
}

class mealsResponse
{
    List<Meal> meals	;
}





