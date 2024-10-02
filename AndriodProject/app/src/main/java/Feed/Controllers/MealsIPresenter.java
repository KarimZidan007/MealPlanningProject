package Feed.Controllers;


import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Meal;
import Model.MealDate;
import Repository.DataSrcRepository;

public interface MealsIPresenter {
    public interface RandomMealPresenterContract {
        public abstract void getRandomMealRemotly();
    }

    interface SearchMealPresenterContract {
        void getMealByNameRemotly(String name);

        void getMealByFirstCharRemotly(char firtChar);
    }

     interface getCategoriesPresenterContract {
        void reqMealsCategories();
    }

    interface getMealsFilterdByCateogry {
        void reqFilteringByCateogry(String category);
    }
     interface getCountriesPresenterContract {
        void reqMealsCountries();
    }

    interface getMealsFilterdByCountry {
        void reqFilteringByCountry(String country);
    }
    interface getIngredientsPresenterContract {
        void reqMealsIngredients();
    }

    interface getMealsFilterdByIngredient {
        void reqFilteringByIngredient(String ingredient);    }

    interface getFavMeals {
        LiveData<List<Meal>> getFavouriteMeals();
        void deleteMeal(Meal meal);
    }
    interface addFavMeal {
        void insertFavMeal(Meal meal);
    }
    interface getPlannedMeals {
        LiveData<List<MealDate>> getPlannedMeals(String date);

        void deletePlannedMeal(MealDate meal);
    }
    interface addPlannedMeal {
        void insertPlannedMeal(MealDate meal);
    }
}



