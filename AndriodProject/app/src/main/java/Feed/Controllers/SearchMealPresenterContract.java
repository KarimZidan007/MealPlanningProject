package Feed.Controllers;

import Network.Model.NetworkCallback.NetworkCallback;

public interface SearchMealPresenterContract {
      void reqSearchByFirstCharacter(char firstChar);
      void reqSearchByName(String name);
}
