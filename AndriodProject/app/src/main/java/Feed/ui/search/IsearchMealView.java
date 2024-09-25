package Feed.ui.search;

import java.util.List;

import Network.Model.Meal;


public interface IsearchMealView
{

        abstract void  displayFirstLMeals(List<Meal> meals);
        abstract void  displayError(String errorMsg);



        abstract void  displayMealsByName(List<Meal> meals);
        abstract void  displayErrorByName(String errorMsg);




}

