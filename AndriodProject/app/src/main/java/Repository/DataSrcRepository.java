package Repository;


import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;

public class DataSrcRepository implements MealsRepository {
    MealsRemoteDataSource remoteSrc;
    public DataSrcRepository(MealsRemoteDataSource remoteSrc)
    {
        this.remoteSrc=remoteSrc;
    }
    @Override
    public void getRandomMeal(NetworkCallback.NetworkCallbackRandom networkCallback) {
        remoteSrc.getRandomMeal( networkCallback);
    }

    @Override
    public void getMealsByFirstChar(char firstChar ,NetworkCallback.NetworkCallbackFirstChar networkCallback) {
        remoteSrc.searchMealsByFirstLetter(firstChar,networkCallback);

    }

    @Override
    public void getMealsByName(String Name,NetworkCallback.NetworkCallbackByName networkCallback ) {
        remoteSrc.searchMealsByName(Name, networkCallback);
    }


}
