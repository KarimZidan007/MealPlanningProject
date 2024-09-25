package Repository;



import java.util.List;

import Network.Model.NetworkCallback;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;

public interface MealsRepository {


     void getRandomMeal();
     void getMealsByFirstChar(char firstChar);
}
