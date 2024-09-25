package Feed.Controllers;

import java.util.List;

import Feed.ui.home.IRandomMealView;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;
import Repository.MealsRepository;

public class RandomMealPresenter implements RandomMealPresenterContract, NetworkCallback.NetworkCallbackRandom {
public MealsRemoteDataSource remoteSrc;
public IRandomMealView  RandomMealView;
public MealsRepository mealsRepo;
    public RandomMealPresenter(MealsRemoteDataSource remoteSrc , IRandomMealView RandomMealView)
            {
                this.RandomMealView=RandomMealView;
                this.remoteSrc=remoteSrc;
            }
    @Override
    public void getRandomMealRemotly()
    {
        mealsRepo = new DataSrcRepository(remoteSrc);
        mealsRepo.getRandomMeal(this);
    }

    @Override
    public void onSuccessResultRandom(List<Meal> meal) {
        RandomMealView.displayRandomMeal(meal);
    }

    @Override
    public void onFailureResultRandom(String errorMsg) {
        RandomMealView.displayError(errorMsg);
    }
}
