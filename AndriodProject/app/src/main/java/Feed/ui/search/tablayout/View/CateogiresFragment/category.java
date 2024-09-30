package Feed.ui.search.tablayout.View.CateogiresFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;

import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.controller.MealDAO;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.MealsCategoriesPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.View.FavouriteFragment;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Category;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;


public class category extends Fragment  implements IsearchMealView.IgetMealCategoriesView ,IsearchMealView.IgetMealFilterCategoriesView ,onClickFilterByCateogryListner, onMealClickListener.onMealClickListenerCat,IsearchMealView.IsearchAllViewsMeals, onAddFavMealClickListner {
RecyclerView catRec;
CategoriesAdapter catAdapter;
FilterByCategoriesAdapter filterAdapter;
MealsCategoriesPresenter catPresenter;
MealsRemoteDataSource dataSource;
searchFragPresenter searchMealPresenter;
private addFavMealPresenter favMealPresenter;
private AppDataBase dataBaseObj;
private MealDAO dao;
private DataSrcRepository repo;
private boolean isDetailRequest=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        catRec=view.findViewById(R.id.categoryRec);
        catRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(catRec.VERTICAL);
        catRec.setLayoutManager(layoutManager);
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        catPresenter = new MealsCategoriesPresenter(dataSource,(IsearchMealView.IgetMealCategoriesView)category.this);
        catPresenter.reqMealsCategories();
    }

    @Override
    public void displayMealsCateogries(List<Category> categories) {
        catAdapter = new CategoriesAdapter(category.this.getContext(),categories,this);
        catRec.setAdapter(catAdapter);
        catAdapter.notifyDataSetChanged();
    }


    @Override
    public void displayFirstLMeals(List<Meal> meals) {

    }

    @Override
    public void displayError(String errorMsg) {

    }

    @Override
    public void displayMealsByName(List<Meal> meals) {
        Meal tempMeal = meals.get(0);

        if (isDetailRequest)
        {
            Intent mealDetailsIntent = new Intent(category.this.getContext(), MealDetailsActivity.class);
            mealDetailsIntent.putExtra("MEAL",tempMeal);
            startActivity(mealDetailsIntent);
        }
        else
        {
            dataBaseObj = AppDataBase.getDbInstance(category.this.getContext());
            dao = dataBaseObj.getMealsDao();
            repo = new DataSrcRepository(dao);
            favMealPresenter = new addFavMealPresenter(repo);
            favMealPresenter.insertFavMeal(tempMeal);
            isDetailRequest=true;
        }

    }

    @Override
    public void displayErrorByName(String errorMsg) {

    }

    @Override
    public void onFilterByCateogry(String cateogryName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        catPresenter = new MealsCategoriesPresenter(dataSource,(IsearchMealView.IgetMealFilterCategoriesView)category.this);
        catPresenter.reqFilteringByCateogry(cateogryName);
    }

    @Override
    public void displayFilterMealsCateogries(List<Meal> meals) {
        filterAdapter = new FilterByCategoriesAdapter(category.this.getContext(),meals,this,this);
        catRec.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();
    }




    @Override
    public void displayFilterErrorCateogries(String errorMsg) {

    }


    @Override
    public void onMealCatClick(String mealName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        searchMealPresenter = new searchFragPresenter(dataSource,  category.this);
        searchMealPresenter.getMealByNameRemotly(mealName);
    }

    @Override
    public void onFavMealAdd(Meal meal) {
        isDetailRequest=false;
        onMealCatClick(meal.getStrMeal());
    }
}