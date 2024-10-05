package Feed.ui.search.tablayout.View.CountriesFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.MealsByCountry.MealsCountriesPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.IsearchMealView;
import Feed.ui.calendar.View.onMealPlanningClick;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Country;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;

public class country extends Fragment implements onClickListByCountry,IsearchMealView.IgetMealCountriesView,IsearchMealView.IgetMealFilterCountriesView,IsearchMealView.IsearchAllViewsMeals, onMealClickListener.onMealClickListenerCountry, onAddFavMealClickListner, onClickRemoveFavourite , onMealPlanningClick {
    private  RecyclerView countryRec;
    private  CountryAdapter countryAdapter;
    private  MealsCountriesPresenter countryPresenter;
    private  MealsRemoteDataSource dataSource;
    private static FilterByCountryAdapter filterAdapter;
    private searchFragPresenter searchMealPresenter;
    private addFavMealPresenter favMealPresenter;
    private FavMealPresenter presenter;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private boolean isDetailRequest=true;
    private MealDateDao mealDateDao;
    private calAppDataBase plannedDbObj;
    private FavoriteManager favManager;

    //create adapter
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryRec=view.findViewById(R.id.categoryRec);
        countryRec.setHasFixedSize(true);
        countryRec.setLayoutManager(new GridLayoutManager(getContext(),2));
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
        filterAdapter = new FilterByCountryAdapter(country.this.getContext(),meals,this,this,this,this);
        countryRec.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFilterErrorByCountries(String errorMsg) {

    }

    @Override
    public void onFilterByCountry(String countryName) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(countryRec.VERTICAL);
        countryRec.setLayoutManager(layoutManager);
        //req meal details
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

        if (isDetailRequest) {
            Intent mealDetailsIntent = new Intent(country.this.getContext(), MealDetailsActivity.class);
            mealDetailsIntent.putExtra("MEAL", tempMeal);
            startActivity(mealDetailsIntent);
        }
        else
        {
            dataBaseObj = AppDataBase.getDbInstance(country.this.getContext());
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
    public void onMealCountryClick(String mealName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        searchMealPresenter = new searchFragPresenter(dataSource,  country.this);
        searchMealPresenter.getMealByNameRemotly(mealName);
    }

    @Override
    public void onFavMealAdd(Meal meal) {
        isDetailRequest=false;
        onMealCountryClick(meal.getStrMeal());
    }

    @Override
    public void onFavMealRemove(Meal meal) {
        dataBaseObj = AppDataBase.getDbInstance(country.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo);
        presenter.deleteMeal(meal);

    }

    @Override
    public void onMealScheduleClicked(Meal meal) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(country.this.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(country.this.getContext(),
                            (timeView, hourOfDay, minute) -> {
                                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);

                                saveMealToDate(meal, selectedDate , selectedTime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true);

                    if (isToday(year, monthOfYear, dayOfMonth)) {
                        timePickerDialog.updateTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                    }

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }
    private boolean isToday(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        return year == today.get(Calendar.YEAR) &&
                month == today.get(Calendar.MONTH) &&
                day == today.get(Calendar.DAY_OF_MONTH);
    }
    private void saveMealToDate(Meal meal, String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        // Get the day of the week from LocalDate
        String dayOfWeek = localDate.getDayOfWeek().toString(); // e.g., "SUNDAY", "MONDAY"
        String formattedDayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase(); // e.g., "Sunday"

        MealDate mealDate = new MealDate(meal, date, time,formattedDayOfWeek);

        plannedDbObj = calAppDataBase.getDbInstance(country.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(country.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }


    public static void notifyDataChanged() {
        if (filterAdapter != null) {
            filterAdapter.notifyDataSetChanged();
        }
    }
}