package Feed.Controllers;

import java.util.List;

import Feed.ui.search.IsearchMealView;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;

public class searchFragPresenter implements MealsIPresenter.SearchMealPresenterContract , NetworkCallback.NetworkCallbackByName, NetworkCallback.NetworkCallbackFirstChar {
    public IsearchMealView.IsearchAllViewsMeals Search;
    private DataSrcRepository searchRepo;

    public searchFragPresenter(DataSrcRepository repository, IsearchMealView.IsearchAllViewsMeals Iview) {
        this.searchRepo = repository;
        this.Search = Iview;
    }

    // Search by First Charachter
    @Override
    public void getMealByFirstCharRemotly(char firstChar) {
        searchRepo.getMealsByFirstChar(firstChar, this);
    }

    @Override
    public void onSuccessResultFirstChar(List<Meal> meal) {
        Search.displayFirstLMeals(meal);
    }

    @Override
    public void onFailureResultFirstChar(String errorMsg) {
        Search.displayError(errorMsg);
    }

    /*************************************************************************************/

    // Search by Name
    @Override
    public void getMealByNameRemotly(String name) {
        searchRepo.getMealsByName(name, this);
    }


    @Override
    public void onSuccessResultByName(List<Meal> meals) {
        Search.displayMealsByName(meals);
    }

    @Override
    public void onFailureResultByName(String errorMsg) {
        Search.displayErrorByName(errorMsg);
    }
}
