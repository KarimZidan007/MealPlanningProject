package Feed.ui.favourite.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.sidechefproject.MealDetails.MealDetailsActivity;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.ui.calendar.View.onMealPlanningClick;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.search.tablayout.View.CountriesFragment.country;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Model.MealDate;
import Repository.DataSrcRepository;

public class FavouriteFragment extends Fragment implements onMealClickListener.onMealClickListenerFavourite,FavouriteMealView,onClickRemoveFavourite, onMealPlanningClick  {

    private RecyclerView recyclerView;
    private FavoriteMealAdapter adapter;
    private List<Meal> favoriteMeals = new ArrayList<>();
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private FavMealPresenter presenter;
    private LiveData<List<Meal>> liveData;
    private calAppDataBase plannedDbObj;
    private MealDateDao mealDateDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite,container,false);
        recyclerView = view.findViewById(R.id.recycler_favorites);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        adapter = new FavoriteMealAdapter(new ArrayList<>(), getContext(), FavouriteFragment.this, FavouriteFragment.this,FavouriteFragment.this);
        recyclerView.setAdapter(adapter);

        dataBaseObj = AppDataBase.getDbInstance(FavouriteFragment.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liveData = presenter.getFavouriteMeals();
        Observer observer=  new Observer<List<Meal>>()
        {
            @Override
            public void onChanged(List<Meal> meals) {
                if(meals!=null)
                {
                    adapter.updateMeals(meals);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        liveData.observe(getViewLifecycleOwner(),observer);
    }

    @Override
    public void onMealFavoutriteClick(Meal meal) {
            Intent intent = new Intent(getContext(), MealDetailsActivity.class);
            intent.putExtra("MEAL", meal);
            startActivity(intent);
    }

    @Override
    public void displayFavMeals(LiveData<List<Meal>> favMeals) {

    }

    @Override
    public void onFavMealRemove(Meal meal) {
        presenter.deleteMeal(meal);
    }

    @Override
    public void onMealScheduleClicked(Meal meal) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(FavouriteFragment.this.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(FavouriteFragment.this.getContext(),
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

        plannedDbObj = calAppDataBase.getDbInstance(FavouriteFragment.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(FavouriteFragment.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
}