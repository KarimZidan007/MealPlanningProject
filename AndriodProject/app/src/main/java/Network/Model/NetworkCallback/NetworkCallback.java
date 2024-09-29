package Network.Model.NetworkCallback;

import java.util.List;

import Model.Category;
import Model.Country;
import Model.Ingredient;
import Model.Meal;

public interface NetworkCallback {

     interface NetworkCallbackByName {
         void onSuccessResultByName(List<Meal> product_);

         void onFailureResultByName(String errorMsg);
    }
     interface NetworkCallbackFirstChar {
         void onSuccessResultFirstChar(List<Meal> product_);

         void onFailureResultFirstChar(String errorMsg);
    }
     interface NetworkCallbackRandom {
         void onSuccessResultRandom(List<Meal> product_);

         void onFailureResultRandom(String errorMsg);
    }
     interface NetworkCallbackGetCateogries{

        void onSuccessResultgetCategories(List<Category> categories);

        void onFailureResultgetCategories(String errorMsg);

    }
     interface NetworkCallbackFilterByCateogry {

        void onSuccessResultFilterByCateogries(List<Meal> meals);

        void onFailureResultFilterByCateogries(String errorMsg);

    }
     interface NetworkCallbackGetCountries {
        void onSuccessResultgetCountries(List<Country> categories);

        void onFailureResultgetCountries(String errorMsg);
    }
     interface NetworkCallbackFilterByCountry {
        void onSuccessResultFilterByCountries(List<Meal> meals);

        void onFailureResultFilterByCountries(String errorMsg);
    }
     interface NetworkCallbackGetIngredients {
        void onSuccessResultGetIngredients(List<Ingredient> Ingredients);

        void onFailureResultGetIngredients(String errorMsg);
    }
     interface NetworkCallbackFilterByIngredient {
        void onSuccessResultFilterByIngredients(List<Meal> meals);

        void onFailureResultFilterByIngredients(String errorMsg);
    }
}
