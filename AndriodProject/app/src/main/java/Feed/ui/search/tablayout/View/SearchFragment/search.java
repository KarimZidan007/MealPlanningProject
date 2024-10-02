package Feed.ui.search.tablayout.View.SearchFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;

import java.util.Calendar;
import java.util.List;

import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDateDao;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.CateogiresFragment.onMealPlanningClick;
import Feed.ui.search.tablayout.View.IngredientsFragment.ingreident;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;

public class search extends Fragment  implements IsearchMealView.IsearchAllViewsMeals, onMealClickListener.onMealClickSearchListener, onMealPlanningClick {
    SearchView search;
    RecyclerView recView;
    MealsRemoteDataSource  searchSrc ;
    searchFragPresenter searchMealPresenter;
    SearchAdapter searchAdapter;
    private MealDateDao mealDateDao;
    private calAppDataBase plannedDbObj;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search=view.findViewById(R.id.searchBar);
        recView=view.findViewById(R.id.searchRec);
        recView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(recView.HORIZONTAL);
        recView.setLayoutManager(layoutManager);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSrc= MealsRemoteDataSource.getRemoteSrcClient();
                searchMealPresenter = new searchFragPresenter(searchSrc,  search.this);
                searchMealPresenter.getMealByNameRemotly(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 1) {
                    searchSrc= MealsRemoteDataSource.getRemoteSrcClient();
                    searchMealPresenter = new searchFragPresenter(searchSrc,search.this);
                    char firstCharacter = newText.charAt(0);
                    searchMealPresenter.getMealByFirstCharRemotly(firstCharacter);
                }
                return false;
            }
        });
    }
    @Override
    public void displayFirstLMeals(List<Meal> meals) {
        searchAdapter = new SearchAdapter(search.this.getContext(),meals,this);
        recView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }

    @Override
    public void displayError(String errorMsg) {
        Toast.makeText(search.this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMealsByName(List<Meal> meals) {
        searchAdapter = new SearchAdapter(search.this.getContext(),meals,this);
        recView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        Log.i("NAMEEE", "displayMealsByName: ");
    }

    @Override
    public void displayErrorByName(String errorMsg) {
        Toast.makeText(search.this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();

    }
    //call back method when i press on a meal
    @Override
    public void onMealClick(Meal meal) {
        Intent mealDetailsIntent = new Intent(this.getContext(),MealDetailsActivity.class);
        mealDetailsIntent.putExtra("MEAL",meal);
        startActivity(mealDetailsIntent);
    }

    @Override
    public void onMealScheduleClicked(Meal meal) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Trigger a date picker dialog restricted to future dates
        DatePickerDialog datePickerDialog = new DatePickerDialog(search.this.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Format the selected date
                    //String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                    // After selecting the date, show the time picker dialog restricted to future times
                    TimePickerDialog timePickerDialog = new TimePickerDialog(search.this.getContext(),
                            (timeView, hourOfDay, minute) -> {
                                // Format the selected time
                                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);

                                // Combine date and time

                                // Insert the meal with the selected date and time into the database
                                saveMealToDate(meal, selectedDate , selectedTime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true); // Use 24-hour format

                    // Ensure the time picker shows future times if the selected date is today
                    if (isToday(year, monthOfYear, dayOfMonth)) {
                        timePickerDialog.updateTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                    }

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set the minimum date to today (restrict past dates)
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
        // Create a new MealDate object with separate date and time
        MealDate mealDate = new MealDate(meal.getStrMeal(), date, time);

        plannedDbObj = calAppDataBase.getDbInstance(search.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(search.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
}