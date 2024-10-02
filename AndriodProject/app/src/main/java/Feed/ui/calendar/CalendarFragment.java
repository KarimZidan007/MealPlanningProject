package Feed.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDateDao;
import Feed.ui.calendar.View.CalenderAdapter;
import Feed.ui.calendar.View.MealIndicatorDecorator;

import Model.MealDate;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView calendarView;
    private MealDateDao mealDateDao;
    private calAppDataBase plannedDbObj;
    private RecyclerView calendarRec;
    private List<MealDate> values =null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();

            // Format the selected date
            String selectedDate = String.format("%04d-%02d-%02d", year, month , day).trim();


            // Fetch meals for the selected date and observe
            LiveData<List<MealDate>> liveDataForDate = mealDateDao.getMealsForDate(selectedDate);
            liveDataForDate.observe(getViewLifecycleOwner(), new Observer<List<MealDate>>() {
                @Override
                public void onChanged(List<MealDate> meals) {
                    if (meals != null && !meals.isEmpty()) {

                        // Update the RecyclerView and calendar decorators
                        values = meals;
                        CalenderAdapter adapter = new CalenderAdapter(getContext(), values);
                        calendarRec.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        Set<CalendarDay> mealDays = new HashSet<>();
                        for (MealDate meal : meals) {
                            String dateString = meal.getDate();
                            LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                            CalendarDay day = CalendarDay.from(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
                            mealDays.add(day);
                        }
                        MealIndicatorDecorator mealIndicatorDecorator = new MealIndicatorDecorator(mealDays);
                        calendarView.addDecorator(mealIndicatorDecorator);

                    } else {
                        Toast.makeText(getContext(), "No meals for this date", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
    }