package Feed.Controllers.InsertingDBPresenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import Feed.Controllers.MealsIPresenter;
import Feed.ui.favourite.View.FavouriteMealView;
import Model.Meal;
import Repository.DataSrcRepository;

public class addFavMealPresenter implements MealsIPresenter.addFavMeal {
    private DataSrcRepository Src;
    FavouriteMealView iView;
    public addFavMealPresenter(DataSrcRepository Src)
    {
        this.Src=Src;
    }

    @Override
    public void insertFavMeal(Meal meal) {
        Src.insertFavMeal(meal);

    }
}
