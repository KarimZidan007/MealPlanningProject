package Feed.ui.search;

import java.util.List;

import Model.Category;
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



}

