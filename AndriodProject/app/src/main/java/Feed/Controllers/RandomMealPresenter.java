package Feed.Controllers;

import java.util.List;

import Feed.ui.home.IRandomMealView;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback;

public class RandomMealPresenter implements RandomMealPresenterContract, NetworkCallback {
public MealsRemoteDataSource remoteSrc;
public IRandomMealView  RandomMealView;
    public RandomMealPresenter(MealsRemoteDataSource remoteSrc , IRandomMealView RandomMealView)
            {
                this.RandomMealView=RandomMealView;
                this.remoteSrc=remoteSrc;
            }
    @Override
    public void getRandomMealRemotly()
    {
        remoteSrc.getDataOverNetwork(this);
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
