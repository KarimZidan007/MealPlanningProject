package Feed.Controllers;

import Network.Model.NetworkCallback.NetworkCallback;

public interface SearchMealPresenterContract {
      void reqSearchByFirstCharacter(char firstChar);
      void reqSearchByName(String name);
      void reqMealsCategories();
      void reqMealsCountries();
      void reqMealsIngredients();
      void reqFilteringByCateogry(String category);
      void reqFilteringByCountry(String country);
      void reqFilteringByIngredient(String ingredient);
}
