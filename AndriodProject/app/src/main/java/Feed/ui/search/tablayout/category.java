package Feed.ui.search.tablayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sidechefproject.R;

import java.util.List;

import Feed.Controllers.MealsCategoriesPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.SearchAdapter;
import Feed.ui.search.tablayout.View.CategoriesAdapter;
import Feed.ui.search.tablayout.View.FilterByCategoriesAdapter;
import Feed.ui.search.tablayout.View.onClickFilterByCateogryListner;
import Model.Category;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;


public class category extends Fragment  implements IsearchMealView.IgetMealCategoriesView ,IsearchMealView.IgetMealFilterCategoriesView ,onClickFilterByCateogryListner {
RecyclerView catRec;
CategoriesAdapter catAdapter;
FilterByCategoriesAdapter filterAdapter;
MealsCategoriesPresenter catPresenter;
MealsRemoteDataSource dataSource;
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
        Log.i("NAMEEE", "onBindViewHolder: ");

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
        filterAdapter = new FilterByCategoriesAdapter(category.this.getContext(),meals);
        catRec.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();
    }



    @Override
    public void displayFilterErrorByName(String errorMsg) {

    }
}