package Feed.ui.favourite.Controller;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Meal;


public class FavoriteManager {
    private static FavoriteManager instance;
    private static Set<String> favoriteMealIds; // Store favorite meal IDs
    private static FavMealPresenter presenter;
    private  static LifecycleOwner owner ;
    private FavoriteManager(FavMealPresenter presenter, LifecycleOwner owner) {
        FavoriteManager.owner =owner;
        FavoriteManager.presenter =presenter;
        favoriteMealIds = new HashSet<>();
        loadFavoritesFromDatabase();
    }

    public static synchronized FavoriteManager getInstance(FavMealPresenter presenter, LifecycleOwner owner) {
        if (instance == null) {
            instance = new FavoriteManager(presenter,owner);
        }
        return instance;
    }

    public static synchronized void loadFavoritesFromDatabase() {

        LiveData<List<Meal>> liveData = presenter.getFavouriteMeals();
        Observer<List<Meal>> observer=  new Observer<List<Meal>>()
        {
            @Override
            public void onChanged(List<Meal> meals) {
                if(meals!=null)
                {
                    for(Meal i : meals)
                    {
                        favoriteMealIds.add(i.idMeal);
                    }
                }
            }
        };
        liveData.observe(owner,observer);

    }

    public static synchronized boolean isFavorite(String mealId) {
        return favoriteMealIds.contains(mealId);
    }

    public static synchronized void toggleFavorite(Meal meal) {
        if (favoriteMealIds.contains(meal.getIdMeal())) {
            favoriteMealIds.remove(meal.getStrMeal());
            meal.setFavorite(false);
            Log.i("NAMEEE", "removed: ");
        } else {
            favoriteMealIds.add(meal.getStrMeal());
            meal.setFavorite(true);
            Log.i("NAMEEE", "added: ");
        }
    }
}


