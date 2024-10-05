package Feed.ui.search.tablayout.View.IngredientsFragment;

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
import Feed.Controllers.MealsByIngredient.MealsIngredientPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.IsearchMealView;
import Feed.ui.calendar.View.onMealPlanningClick;
import Feed.ui.search.SearchFragment;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Ingredient;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;


public class ingreident extends Fragment implements IsearchMealView.IgetMealFilterIngredientsView,IsearchMealView.IgetMealIngredientsView, onClickListByIngredient, onMealClickListener.onMealClickListenerIngreident,IsearchMealView.IsearchAllViewsMeals, onAddFavMealClickListner, onMealPlanningClick, onClickRemoveFavourite {
private RecyclerView ingreidentRec;
private IngredientAdapter ingredientAdapter;
private MealsIngredientPresenter ingredientPresenter;
private MealsRemoteDataSource dataSource;
private static FilterByIngredientAdapter filterIngAdapter;
private searchFragPresenter searchMealPresenter;
private addFavMealPresenter favMealPresenter;
private AppDataBase dataBaseObj;
private MealDAO dao;
private DataSrcRepository repo;
private boolean isDetailRequest=true;
private MealDateDao mealDateDao;
private calAppDataBase plannedDbObj;
private FavMealPresenter presenter;
private FavoriteManager favManager;

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
        ingreidentRec=view.findViewById(R.id.categoryRec);
        ingreidentRec.setHasFixedSize(true);
        ingreidentRec.setLayoutManager(new GridLayoutManager(getContext(),2));
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        ingredientPresenter = new MealsIngredientPresenter(dataSource,(IsearchMealView.IgetMealIngredientsView)ingreident.this);
        ingredientPresenter.reqMealsIngredients();
    }

    @Override
    public void displayMealsIngredients(List<Ingredient> ingredients) {
        ingredientAdapter = new IngredientAdapter(ingreident.this.getContext(),ingredients,this);
        ingreidentRec.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();
    }
    @Override
    public void displayErrorByIngredients(String errorMsg) {

    }


    @Override
    public void displayFilterMealsIngredients(List<Meal> meals) {
        ingreidentRec.setLayoutManager(new GridLayoutManager(getContext(),1));
        filterIngAdapter = new FilterByIngredientAdapter(ingreident.this.getContext(),meals,this,this,this,this);
        ingreidentRec.setAdapter(filterIngAdapter);
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFilterErrorByIngredients(String errorMsg) {

    }


    //Button Callback
    @Override
    public void onFilterByIngredient(String ingreident) {
          dataSource = MealsRemoteDataSource.getRemoteSrcClient();
          ingredientPresenter = new MealsIngredientPresenter(dataSource,(IsearchMealView.IgetMealFilterIngredientsView)ingreident.this);
          ingredientPresenter.reqFilteringByIngredient(ingreident);
    }

    @Override
    public void onMealIngreidentClick(String mealName) {
        dataSource= MealsRemoteDataSource.getRemoteSrcClient();
        searchMealPresenter = new searchFragPresenter(dataSource,  ingreident.this);
        searchMealPresenter.getMealByNameRemotly(mealName);
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
            Intent mealDetailsIntent = new Intent(ingreident.this.getContext(), MealDetailsActivity.class);
            mealDetailsIntent.putExtra("MEAL", tempMeal);
            startActivity(mealDetailsIntent);
        }
        else
        {
            dataBaseObj = AppDataBase.getDbInstance(ingreident.this.getContext());
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
    public void onFavMealAdd(Meal meal) {
        isDetailRequest=false;
        onMealIngreidentClick(meal.getStrMeal());
    }

    @Override
    public void onMealScheduleClicked(Meal meal) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Trigger a date picker dialog restricted to future dates
        DatePickerDialog datePickerDialog = new DatePickerDialog(ingreident.this.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(ingreident.this.getContext(),
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

        plannedDbObj = calAppDataBase.getDbInstance(ingreident.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(ingreident.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavMealRemove(Meal meal) {
        dataBaseObj = AppDataBase.getDbInstance(ingreident.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo);
        presenter.deleteMeal(meal);
    }

    public static void notifyDataChanged() {
        if (filterIngAdapter != null) {
            filterIngAdapter.notifyDataSetChanged();
        }
    }
}

