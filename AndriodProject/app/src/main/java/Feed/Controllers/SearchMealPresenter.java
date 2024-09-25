package Feed.Controllers;

import android.widget.Toast;

import java.util.List;

import Feed.NavActivity;
import Feed.ui.search.IsearchByFirstLMealView;
import Feed.ui.search.SearchFragment;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback;
import Repository.DataSrcRepository;

public class SearchMealPresenter implements SearchMealPresenterContract, NetworkCallback {
public MealsRemoteDataSource remoteSrc;
public IsearchByFirstLMealView  searchByFirstLMealView;
private DataSrcRepository searchRepo;
    public SearchMealPresenter(MealsRemoteDataSource remoteSrc , IsearchByFirstLMealView searchByFirstLMealView)
            {
                this.searchByFirstLMealView=searchByFirstLMealView;
                this.remoteSrc=remoteSrc;
            }


    @Override
    public void onSuccessResult(List<Meal> meal) {
        searchByFirstLMealView.displayFirstLMeals(meal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        searchByFirstLMealView.displayError(errorMsg);
    }

    @Override
    public void searchByFirstCharacter(char firstChar) {
        searchRepo = new DataSrcRepository(remoteSrc,this);
        searchRepo.getMealsByFirstChar(firstChar);
    }
}
