package Repository;


import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback;

public class Repository implements MealsRepository {


    @Override
    public void getRandomMeal(MealsRemoteDataSource remoteSrc, NetworkCallback networkCallback) {
        remoteSrc.getDataOverNetwork(networkCallback);
    }
}
