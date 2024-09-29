package Feed.ui.search;

import java.util.List;

import Model.Category;
import Model.Country;
import Model.Ingredient;
import Model.Meal;


public interface IsearchMealView
{
        interface IsearchAllViewsMeals
        {
            void  displayFirstLMeals(List<Meal> meals);
            void  displayError(String errorMsg);

            void  displayMealsByName(List<Meal> meals);
            void  displayErrorByName(String errorMsg);
        }
        interface IgetMealCategoriesView
        {
            void  displayMealsCateogries(List<Category> categories);
            void  displayErrorByName(String errorMsg);
        }
        interface IgetMealFilterCategoriesView
        {
            void  displayFilterMealsCateogries(List<Meal> meals);
            void  displayFilterErrorCateogries(String errorMsg);
        }
        interface IgetMealCountriesView
        {
            void  displayMealsCountries(List<Country> countries);
            void  displayErrorByCountries(String errorMsg);
        }
        interface IgetMealFilterCountriesView
        {
            void  displayFilterMealsCountries(List<Meal> meals);
            void  displayFilterErrorByCountries(String errorMsg);
        }
        interface IgetMealIngredientsView
        {
            void  displayMealsIngredients(List<Ingredient> ingredients);
            void  displayErrorByIngredients(String errorMsg);
        }
        interface IgetMealFilterIngredientsView
        {
            void  displayFilterMealsIngredients(List<Meal> meals);
            void  displayFilterErrorByIngredients(String errorMsg);
        }


}

