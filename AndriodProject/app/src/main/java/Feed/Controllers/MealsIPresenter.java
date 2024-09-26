package Feed.Controllers;


public interface MealsIPresenter {
    public interface RandomMealPresenterContract {
        public abstract void getRandomMealRemotly();
    }
    public interface SearchMealPresenterContract {
        public abstract void getMealByNameRemotly(String name );
        public abstract void getMealByFirstCharRemotly(char firtChar);
    }

}