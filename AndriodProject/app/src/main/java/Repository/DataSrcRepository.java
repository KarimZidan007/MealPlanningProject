package Repository;


import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback;

public class DataSrcRepository implements MealsRepository {
    MealsRemoteDataSource remoteSrc;
    NetworkCallback networkCallback;
    public DataSrcRepository(MealsRemoteDataSource remoteSrc, NetworkCallback networkCallback)
    {
        this.remoteSrc=remoteSrc;
        this.networkCallback=networkCallback;
    }
    @Override
    public void getRandomMeal() {
        remoteSrc.getRandomMeal(networkCallback);
    }

    @Override
    public void getMealsByFirstChar(char firstChar) {
        remoteSrc.searchMealsByFirstLetter(firstChar,networkCallback);

    }


}
