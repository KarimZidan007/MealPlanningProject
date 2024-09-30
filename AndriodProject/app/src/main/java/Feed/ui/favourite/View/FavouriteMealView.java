package Feed.ui.favourite.View;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Meal;

public interface FavouriteMealView {
     void displayFavMeals(LiveData<List<Meal>> favMeals);
}
