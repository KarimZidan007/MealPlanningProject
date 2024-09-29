package Feed.Controllers.MealsByCountry;

import android.util.Log;

import Feed.Controllers.MealsIPresenter;
import Feed.ui.search.IsearchMealView;
import Model.Country;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;
import java.util.List;

public class MealsCountriesPresenter implements NetworkCallback.NetworkCallbackGetCountries,NetworkCallback.NetworkCallbackFilterByCountry, MealsIPresenter.getCountriesPresenterContract, MealsIPresenter.getMealsFilterdByCountry {
    private MealsRemoteDataSource remoteSrc;
    private DataSrcRepository searchRepo;
    private IsearchMealView.IgetMealCountriesView iViewOne;
    private IsearchMealView.IgetMealFilterCountriesView iViewTwo;
    public MealsCountriesPresenter(MealsRemoteDataSource remoteSrc, IsearchMealView.IgetMealCountriesView iViewOne)
    {
        this.iViewOne=iViewOne;
        this.remoteSrc=remoteSrc;
    }
    public MealsCountriesPresenter(MealsRemoteDataSource remoteSrc , IsearchMealView.IgetMealFilterCountriesView iViewTwo)
    {
        this.iViewTwo=iViewTwo;
        this.remoteSrc=remoteSrc;
    }


    //Ask for Meals Countries
    @Override
    public void reqMealsCountries() {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsCountries(this);
    }
    @Override
    public void onSuccessResultgetCountries(List<Country> countries) {
        iViewOne.displayMealsCountries(countries);
    }

    @Override
    public void onFailureResultgetCountries(String errorMsg) {
        iViewOne.displayErrorByCountries(errorMsg);
    }



    //Ask for Meals Countries
    /***********************************************************************************/

    @Override
    public void reqFilteringByCountry(String country) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.FilterMealsByCountry(country,this);
    }

    @Override
    public void onSuccessResultFilterByCountries(List<Meal> meals) {
        iViewTwo.displayFilterMealsCountries(meals);
    }

    @Override
    public void onFailureResultFilterByCountries(String errorMsg) {
        iViewTwo.displayFilterErrorByCountries(errorMsg);
    }


}
