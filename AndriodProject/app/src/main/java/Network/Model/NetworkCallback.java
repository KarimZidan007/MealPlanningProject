package Network.Model;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(List<Meal> product_);

    public void onFailureResult(String errorMsg);
}
