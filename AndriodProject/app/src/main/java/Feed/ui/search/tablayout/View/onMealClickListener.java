package Feed.ui.search.tablayout.View;

import Model.Meal;

public interface onMealClickListener
{
    public interface onMealClickSearchListener {
        void onMealClick(Meal meal);
    }
    public interface onMealClickListenerCat
    {
        void onMealCatClick(String mealName);
    }
     interface onMealClickListenerCountry
    {
        void onMealCountryClick(String mealName);
    }
    interface onMealClickListenerIngreident
    {
        void onMealIngreidentClick(String mealName);
    }
    interface onMealClickListenerRandom
    {
        void onMealRandomClick(String mealName);
    }
}