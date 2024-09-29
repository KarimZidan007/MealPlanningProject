package Feed.Controllers;


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
}



