package Feed.Controllers;

import android.util.Log;

import java.util.List;

import Feed.ui.search.IsearchMealView;
import Model.Category;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Network.Model.NetworkCallback.NetworkCallback;
import Repository.DataSrcRepository;

public class MealsCategoriesPresenter implements MealsIPresenter.getCategoriesPresenterContract, NetworkCallback.NetworkCallbackGetCateogries ,NetworkCallback.NetworkCallbackFilterByCateogry,MealsIPresenter.getMealsFilterdByCateogry {
    public MealsRemoteDataSource remoteSrc;
    public IsearchMealView.IgetMealCategoriesView Search;
    private DataSrcRepository searchRepo;
    public IsearchMealView.IgetMealFilterCategoriesView filter;

   public MealsCategoriesPresenter(MealsRemoteDataSource remoteSrc , IsearchMealView.IgetMealCategoriesView Iview )
    {
        this.remoteSrc=remoteSrc;
        this.Search=Iview;
    }
    public MealsCategoriesPresenter(MealsRemoteDataSource remoteSrc , IsearchMealView.IgetMealFilterCategoriesView Iview )
    {
        this.remoteSrc=remoteSrc;
        this.filter=Iview;
    }
        //Ask for Meals Categories
    @Override
    public void reqMealsCategories() {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.getMealsCategories(this);
    }

    @Override
    public void onSuccessResultgetCategories(List<Category> categories) {
        Search.displayMealsCateogries(categories);

    }

    @Override
    public void onFailureResultgetCategories(String errorMsg) {
        Search.displayErrorByName(errorMsg);
    }




    //Ask for Meals Categories

    /***********************************************************************************/
    /***********************************************************************************/
    //Req Filter By Cateogry
    @Override
    public void reqFilteringByCateogry(String category) {
        searchRepo = new DataSrcRepository(remoteSrc);
        searchRepo.FilterMealsByCateogry(category,this);
    }
    @Override
    public void onSuccessResultFilterByCateogries(List<Meal> meals) {
        filter.displayFilterMealsCateogries(meals);

    }



    @Override
    public void onFailureResultFilterByCateogries(String errorMsg) {
        filter.displayFilterErrorCateogries(errorMsg);
    }
    //Req Filter By Cateogry

    /***********************************************************************************/

}
