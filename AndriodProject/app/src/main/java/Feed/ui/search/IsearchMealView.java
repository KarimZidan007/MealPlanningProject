package Feed.ui.search;

import java.util.List;

import Model.Meal;


public interface IsearchMealView
{

         void  displayFirstLMeals(List<Meal> meals);
         void  displayError(String errorMsg);


         void  displayMealsByName(List<Meal> meals);
         void  displayErrorByName(String errorMsg);

}

