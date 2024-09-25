package Feed.ui.search;

import java.util.List;

import Model.Meal;


public interface IsearchMealView
{
        interface IsearchFirstLMeals
        {
            void  displayFirstLMeals(List<Meal> meals);
            void  displayError(String errorMsg);
        }
        interface IsearchByNameMeals
        {

            void  displayMealsByName(List<Meal> meals);
            void  displayErrorByName(String errorMsg);
        }



}

