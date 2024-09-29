package Feed.ui.search.tablayout.View.CountriesFragment;

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

import Feed.Controllers.MealsByCountry.MealsCountriesPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Country;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;

public class country extends Fragment implements onClickListByCountry,IsearchMealView.IgetMealCountriesView,IsearchMealView.IgetMealFilterCountriesView,IsearchMealView.IsearchAllViewsMeals, onMealClickListener.onMealClickListenerCountry {
    RecyclerView countryRec;
    CountryAdapter countryAdapter;
    MealsCountriesPresenter countryPresenter;
    MealsRemoteDataSource dataSource;
    FilterByCountryAdapter filterAdapter;
    searchFragPresenter searchMealPresenter;
    //create adapter
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryRec=view.findViewById(R.id.countryRec);
        countryRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(countryRec.VERTICAL);
        countryRec.setLayoutManager(layoutManager);
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        countryPresenter = new MealsCountriesPresenter(dataSource,(IsearchMealView.IgetMealCountriesView)country.this);
        countryPresenter.reqMealsCountries();
    }



    @Override
    public void displayMealsCountries(List<Country> countries) {
        countryAdapter = new CountryAdapter(country.this.getContext(),countries,this);
        countryRec.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayErrorByCountries(String errorMsg) {
    }

    @Override
    public void displayFilterMealsCountries(List<Meal> meals) {
        filterAdapter = new FilterByCountryAdapter(country.this.getContext(),meals,this);
        countryRec.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFilterErrorByCountries(String errorMsg) {

    }

    @Override
    public void onFilterByCountry(String countryName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        countryPresenter = new MealsCountriesPresenter(dataSource,(IsearchMealView.IgetMealFilterCountriesView)country.this);
        countryPresenter.reqFilteringByCountry(countryName);
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
        Intent mealDetailsIntent = new Intent(country.this.getContext(), MealDetailsActivity.class);
        mealDetailsIntent.putExtra("MEAL",tempMeal);
        startActivity(mealDetailsIntent);
    }

    @Override
    public void displayErrorByName(String errorMsg) {

    }



    @Override
    public void onMealCountryClick(String mealName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        searchMealPresenter = new searchFragPresenter(dataSource,  country.this);
        searchMealPresenter.getMealByNameRemotly(mealName);
    }
}