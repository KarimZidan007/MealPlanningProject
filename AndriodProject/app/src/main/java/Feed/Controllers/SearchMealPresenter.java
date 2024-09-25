package Feed.Controllers;

import android.widget.Toast;

import java.util.List;

import Feed.ui.search.IsearchMealView;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;

public class SearchMealPresenter implements SearchMealPresenterContract, NetworkCallback.NetworkCallbackFirstChar, NetworkCallback.NetworkCallbackByName {
public MealsRemoteDataSource remoteSrc;
public IsearchMealView  Search;
private DataSrcRepository searchRepo;
    public SearchMealPresenter(MealsRemoteDataSource remoteSrc , IsearchMealView Search)
            {
                this.Search=Search;
                this.remoteSrc=remoteSrc;
            }


    @Override
    public void onSuccessResultFirstChar(List<Meal> meal) {
        Search.displayFirstLMeals(meal);
    }

    @Override
    public void onFailureResultFirstChar(String errorMsg) {
        Search.displayError(errorMsg);
    }


    @Override
    public void reqSearchByFirstCharacter(char firstChar) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsByFirstChar(firstChar,this);
    }

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
}
