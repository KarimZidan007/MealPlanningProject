package Feed.Controllers;

import java.util.List;

import Feed.ui.home.IRandomMealView;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback;
import Repository.DataSrcRepository;
import Repository.MealsRepository;

public class RandomMealPresenter implements RandomMealPresenterContract, NetworkCallback {
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
        mealsRepo = new DataSrcRepository(remoteSrc,this);
        mealsRepo.getRandomMeal();
    }

    @Override
    public void onSuccessResult(List<Meal> meal) {
        RandomMealView.displayRandomMeal(meal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        RandomMealView.displayError(errorMsg);
    }
}
