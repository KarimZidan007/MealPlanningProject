package Feed.Controllers;


public interface MealsIPresenter {
    public interface RandomMealPresenterContract {
        public abstract void getRandomMealRemotly();
    }

    interface SearchMealPresenterContract {
        void getMealByNameRemotly(String name);

        void getMealByFirstCharRemotly(char firtChar);
    }

    public interface getCategoriesPresenterContract {
        void reqMealsCategories();
    }

    interface getMealsFilterdByCateogry {
        void reqFilteringByCateogry(String category);
    }
}