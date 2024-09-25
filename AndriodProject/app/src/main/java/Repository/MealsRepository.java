package Repository;


import Network.Model.NetworkCallback.NetworkCallback;

public interface MealsRepository {

     void getRandomMeal(NetworkCallback.NetworkCallbackRandom networkCallBack);
     void getMealsByFirstChar(char firstChar,NetworkCallback.NetworkCallbackFirstChar networkCallBack);
     void getMealsByName(String Name,NetworkCallback.NetworkCallbackByName networkCallBack);
}
