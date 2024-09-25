package Feed.Controllers;

import java.util.List;

import Feed.ui.search.IsearchMealView;
import Model.Category;
import Model.Country;
import Model.Ingredient;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;

public class SearchMealPresenter implements SearchMealPresenterContract, NetworkCallback.NetworkCallbackFirstChar, NetworkCallback.NetworkCallbackByName , NetworkCallback.NetworkCallbackGetIngredients, NetworkCallback.NetworkCallbackGetCountries, NetworkCallback.NetworkCallbackGetCateogries, NetworkCallback.NetworkCallbackFilterByCateogry, NetworkCallback.NetworkCallbackFilterByCountry, NetworkCallback.NetworkCallbackFilterByIngredient {
public MealsRemoteDataSource remoteSrc;
public IsearchMealView  Search;
private DataSrcRepository searchRepo;

    public SearchMealPresenter(MealsRemoteDataSource remoteSrc , IsearchMealView Search) {
        this.Search=Search;
        this.remoteSrc=remoteSrc;
    }


    // Search by First Charachter
    @Override
    public void reqSearchByFirstCharacter(char firstChar) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsByFirstChar(firstChar,this);
    }
    @Override
    public void onSuccessResultFirstChar(List<Meal> meal) {
        Search.displayFirstLMeals(meal);
    }
    @Override
    public void onFailureResultFirstChar(String errorMsg) {
        Search.displayError(errorMsg);
    }
    // Search by First Charachter

    /*************************************************************************************/
    // Search by Name
    @Override
    public void reqSearchByName(String name) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsByName(name,this);
    }

    @Override
    public void onSuccessResultByName(List<Meal> meals) {
        Search.displayMealsByName(meals);
    }

    @Override
    public void onFailureResultByName(String errorMsg) {
        Search.displayErrorByName(errorMsg);
    }
    // Search by Name
    /***********************************************************************************/
    //Ask for Meals Categories
    @Override
    public void reqMealsCategories() {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsCategories(this);
    }
    @Override
    public void onSuccessResultgetCategories(List<Category> categories) {

    }

    @Override
    public void onFailureResultgetCategories(String errorMsg) {

    }

    //Ask for Meals Categories

    /***********************************************************************************/

    //Ask for Meals Countries
    @Override
    public void reqMealsCountries() {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsCountries(this);
    }
    @Override
    public void onSuccessResultgetCountries(List<Country> categories) {

    }

    @Override
    public void onFailureResultgetCountries(String errorMsg) {

    }

    //Ask for Meals Countries
    /***********************************************************************************/
    //Ask for Meals Ingredients

    @Override
    public void reqMealsIngredients() {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsIngredients(this);
    }
    @Override
    public void onSuccessResultGetIngredients(List<Ingredient> Ingredients) {

    }

    @Override
    public void onFailureResultGetIngredients(String errorMsg) {

    }
    //Ask for Meals Ingredients



    /***********************************************************************************/
    //Req Filter By Cateogry
    @Override
    public void reqFilteringByCateogry(String category) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.FilterMealsByCateogry(category,this);
    }
    @Override
    public void onSuccessResultFilterByCateogries(List<Meal> meals) {

    }

    @Override
    public void onFailureResultFilterByCateogries(String errorMsg) {

    }
    //Req Filter By Cateogry

    /***********************************************************************************/

    //Req Filter By Country

    @Override
    public void reqFilteringByCountry(String country) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.FilterMealsByCountry(country,this);
    }

    @Override
    public void onSuccessResultFilterByCountries(List<Meal> meals) {

    }

    @Override
    public void onFailureResultFilterByCountries(String errorMsg) {

    }

    //Req Filter By Country
    /***********************************************************************************/


    //Req Filter By Ingredient

    @Override
    public void reqFilteringByIngredient(String ingredient) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.FilterMealsByIngredient(ingredient,this);
    }


    @Override
    public void onSuccessResultFilterByIngredients(List<Meal> meals) {

    }

    @Override
    public void onFailureResultFilterByIngredients(String errorMsg) {

    }
    //Req Filter By Ingredient

    /***********************************************************************************/







}
