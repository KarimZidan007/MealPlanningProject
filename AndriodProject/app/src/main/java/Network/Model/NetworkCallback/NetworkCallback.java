package Network.Model.NetworkCallback;

import java.util.List;

import Network.Model.Meal;

public interface NetworkCallback {

    public interface NetworkCallbackByName {
        public void onSuccessResultByName(List<Meal> product_);

        public void onFailureResultByName(String errorMsg);
    }

    public interface NetworkCallbackFirstChar {
         void onSuccessResultFirstChar(List<Meal> product_);

         void onFailureResultFirstChar(String errorMsg);
    }

    public interface NetworkCallbackRandom {
         void onSuccessResultRandom(List<Meal> product_);

         void onFailureResultRandom(String errorMsg);
    }


}
