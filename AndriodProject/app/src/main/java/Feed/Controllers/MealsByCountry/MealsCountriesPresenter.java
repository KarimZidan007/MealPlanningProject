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
    private DataSrcRepository searchRepo;
    private IsearchMealView.IgetMealCountriesView iViewOne;
    private IsearchMealView.IgetMealFilterCountriesView iViewTwo;
    public MealsCountriesPresenter(DataSrcRepository remoteSrc, IsearchMealView.IgetMealCountriesView iViewOne)
    {
        this.iViewOne=iViewOne;
        this.searchRepo=remoteSrc;
    }
    public MealsCountriesPresenter(DataSrcRepository remoteSrc , IsearchMealView.IgetMealFilterCountriesView iViewTwo)
    {
        this.iViewTwo=iViewTwo;
        this.searchRepo=remoteSrc;
    }


    //Ask for Meals Countries
    @Override
    public void reqMealsCountries() {
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
