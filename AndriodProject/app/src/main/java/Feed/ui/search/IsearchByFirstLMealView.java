package Feed.ui.search;

import java.util.List;

import Network.Model.Meal;

public interface IsearchByFirstLMealView {

    abstract void  displayFirstLMeals(List<Meal> meals);
    abstract void  displayError(String errorMsg);

}
