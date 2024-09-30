package Feed.ui.favourite.Controller;

import androidx.lifecycle.LiveData;

import java.util.List;

import DataBase.controller.MealDAO;
import Feed.Controllers.MealsIPresenter;
import Feed.ui.favourite.View.FavouriteMealView;
import Model.Meal;
import Repository.DataSrcRepository;

public class FavMealPresenter implements MealsIPresenter.getFavMeals {
    private DataSrcRepository Src;
    public FavMealPresenter(DataSrcRepository Src)
    {
        this.Src=Src;
    }
    @Override
    public LiveData<List<Meal>> getFavouriteMeals() {
        return Src.getFavMeals();
    }

    @Override
    public void deleteMeal( Meal meal) {
        Src.delFavMeal(meal);
    }
}
