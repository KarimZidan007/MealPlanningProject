package Network.Model.NetworkCallback;

import java.util.List;

import Model.Category;
import Model.Country;
import Model.Ingredient;
import Model.Meal;

public interface NetworkCallback {

    public interface NetworkCallbackByName {
        public void onSuccessResultByName(List<Meal> product_);

        public void onFailureResultByName(String errorMsg);
    }
    public interface NetworkCallbackFirstChar {
         void onSuccessResultFirstChar(List<Meal> product_);

         void onFailureResultFirstChar(String errorMsg);
    }
    public interface NetworkCallbackRandom {
         void onSuccessResultRandom(List<Meal> product_);

         void onFailureResultRandom(String errorMsg);
    }
    public interface NetworkCallbackGetCateogries{

        void onSuccessResultgetCategories(List<Category> categories);

        void onFailureResultgetCategories(String errorMsg);

    }
    public interface NetworkCallbackFilterByCateogry {

        void onSuccessResultFilterByCateogries(List<Meal> meals);

        void onFailureResultFilterByCateogries(String errorMsg);

    }
    public interface NetworkCallbackGetCountries {
        void onSuccessResultgetCountries(List<Country> categories);

        void onFailureResultgetCountries(String errorMsg);
    }
    public interface NetworkCallbackFilterByCountry {
        void onSuccessResultFilterByCountries(List<Meal> meals);

        void onFailureResultFilterByCountries(String errorMsg);
    }
    public interface NetworkCallbackGetIngredients {
        void onSuccessResultGetIngredients(List<Ingredient> Ingredients);

        void onFailureResultGetIngredients(String errorMsg);
    }
    public interface NetworkCallbackFilterByIngredient {
        void onSuccessResultFilterByIngredients(List<Meal> meals);

        void onFailureResultFilterByIngredients(String errorMsg);
    }
}
