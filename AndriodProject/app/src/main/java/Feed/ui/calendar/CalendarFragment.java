package Feed.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.ui.calendar.View.CalenderAdapter;
import Feed.ui.calendar.View.CustomSelectorDecorator;
import Feed.ui.calendar.View.MealIndicatorDecorator;

import Feed.ui.calendar.View.onDeletePlanMealClick;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.FavouriteFragment;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Feed.ui.search.tablayout.View.SearchFragment.search;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Model.MealDate;
import Repository.DataSrcRepository;

public class CalendarFragment extends Fragment implements onAddFavMealClickListner,onDeletePlanMealClick, onMealClickListener.onMealClickSearchListener, onClickRemoveFavourite {
    private MaterialCalendarView calendarView;
    private MealDateDao mealDateDao;
    private calAppDataBase plannedDbObj;
    private RecyclerView calendarRec;
    private List<MealDate> values =null;
    private CalenderAdapter adapter;
    private MealIndicatorDecorator mealIndicatorDecorator;
    private Set<CalendarDay> mealDays;
    private  Map<CalendarDay, MealIndicatorDecorator> mealIndicatorDecorators = new HashMap<>();
    private CalendarDay selectedCalendarDay;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private  CustomSelectorDecorator customSelectorDecorator;
    private DataSrcRepository repo;
    private FavMealPresenter presenter;
    private FavoriteManager favManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarRec = view.findViewById(R.id.recyclerViewMeals);
        calendarView = view.findViewById(R.id.calendarView);
        plannedDbObj = calAppDataBase.getDbInstance(CalendarFragment.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        // Set up RecyclerView with a GridLayoutManager
        calendarRec.setLayoutManager(new GridLayoutManager(getContext(), 1));

        LiveData<List<MealDate>> liveDataAllMeals = mealDateDao.getAllMealsForDate();  // Assuming you have a method to get all meals
        liveDataAllMeals.observe(getViewLifecycleOwner(), new Observer<List<MealDate>>() {
            @Override
            public void onChanged(List<MealDate> meals) {
                if (meals != null && !meals.isEmpty()) {
                    Set<CalendarDay> mealDays = new HashSet<>();

                    // Loop through all the meal dates and convert them to CalendarDay
                    for (MealDate meal : meals) {
                        String dateString = meal.getDate();
                        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                        CalendarDay day = CalendarDay.from(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
                        mealDays.add(day);
                    }

                    // Add decorator for all meal days
                    MealIndicatorDecorator mealIndicatorDecorator = new MealIndicatorDecorator(mealDays);
                    calendarView.addDecorator(mealIndicatorDecorator);

                    // Optionally, you can update your RecyclerView adapter here if needed
                } else {
                    Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            // Get selected date values
            int year = date.getYear();
            int month = date.getMonth();  // Note: CalendarDay's month is 1-indexed (Jan = 1, Dec = 12)
            int day = date.getDay();

            // Create a CalendarDay object for the selected date
            selectedCalendarDay = CalendarDay.from(year, month, day);

            // Remove old decorators and apply new selection decorator
            if (customSelectorDecorator != null) {
                calendarView.removeDecorator(customSelectorDecorator);
            }
            // Apply a custom circle for the selected day (orange color)
             customSelectorDecorator = new CustomSelectorDecorator(getContext(), selectedCalendarDay);
            calendarView.addDecorator(customSelectorDecorator);

            // Format the selected date as string (assuming you need this for database lookup)
            String selectedDate = String.format("%04d-%02d-%02d", year, month, day).trim();

            // Set up the adapter for the RecyclerView with an empty list initially
            adapter = new CalenderAdapter(getContext(), new ArrayList<>(), CalendarFragment.this, CalendarFragment.this, CalendarFragment.this,CalendarFragment.this);
            calendarRec.setAdapter(adapter);

            // Fetch meals for the selected date and observe changes
            LiveData<List<MealDate>> liveDataForDate = mealDateDao.getMealsForDate(selectedDate);
            liveDataForDate.observe(getViewLifecycleOwner(), meals -> {
                if (meals != null && !meals.isEmpty()) {
                    // Meals found for the selected date

                    // Update RecyclerView with the list of meals
                    values = meals;
                    adapter.setList(values);
                    adapter.notifyDataSetChanged();

                    // Prepare a set of CalendarDays for the meal dates
                    mealDays = new HashSet<>();
                    for (MealDate meal : meals) {
                        String dateString = meal.getDate();
                        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                        CalendarDay mealDay = CalendarDay.from(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
                        mealDays.add(mealDay);
                    }

                    // Apply a decorator for the meal days (meal indicator)
                    MealIndicatorDecorator mealIndicatorDecorator = new MealIndicatorDecorator(mealDays);
                    mealIndicatorDecorators.put(selectedCalendarDay, mealIndicatorDecorator);
                    calendarView.addDecorator(mealIndicatorDecorator);

                } else {
                    // No meals for the selected date
                    MealIndicatorDecorator existingDecorator = mealIndicatorDecorators.remove(selectedCalendarDay);
                    if (existingDecorator != null) {
                        calendarView.removeDecorator(existingDecorator);  // Remove decorator for that day if no meals found
                    }
                    adapter.setList(new ArrayList<>());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "No meals for this date", Toast.LENGTH_SHORT).show();
                }
            });
        });

}

    @Override
    public void onDeleteMealScheduleClicked(MealDate meal) {
        new AlertDialog.Builder(CalendarFragment.this.getContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to delete this scheduled meal?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    plannedDbObj = calAppDataBase.getDbInstance(CalendarFragment.this.getContext());
                    mealDateDao = plannedDbObj.getDateMealsDao();
                    new Thread(() -> {
                        mealDateDao.deletePlannedMeal(meal);
                    }).start();
                    Toast.makeText(getContext(), "Meal has been deleted.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void onFavMealAdd(Meal meal) {
        dataBaseObj = AppDataBase.getDbInstance(CalendarFragment.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo);
        presenter.insertFavMeal(meal);
        FavoriteManager.loadFavoritesFromDatabase();
    }

    @Override
    public void onMealClick(Meal meal) {
        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
        intent.putExtra("MEAL", meal);
        startActivity(intent);
    }

    @Override
    public void onFavMealRemove(Meal meal) {
        new AlertDialog.Builder(CalendarFragment.this.getContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to remove this meal from favorites?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    dataBaseObj = AppDataBase.getDbInstance(CalendarFragment.this.getContext());
                    dao = dataBaseObj.getMealsDao();
                    repo = new DataSrcRepository(dao);
                    presenter = new FavMealPresenter(repo);
                    presenter.deleteMeal(meal);
                    FavoriteManager.loadFavoritesFromDatabase();
                    Toast.makeText(getContext(), "Meal removed from favorites.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}