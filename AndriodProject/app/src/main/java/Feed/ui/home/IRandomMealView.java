package Feed.ui.home;

import java.util.List;

import Model.Meal;

public interface IRandomMealView {

    abstract void  displayRandomMeal(List<Meal> meal);
    abstract void  displayError(String errorMsg);

}
