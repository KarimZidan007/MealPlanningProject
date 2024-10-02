package Feed.ui.search.tablayout.View.CateogiresFragment;

import static android.text.format.DateUtils.isToday;

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

import java.util.Calendar;
import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.MealsCategoriesPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Category;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;
import DataBase.controller.MealDateDao;

public class category extends Fragment  implements IsearchMealView.IgetMealCategoriesView ,IsearchMealView.IgetMealFilterCategoriesView ,onClickFilterByCateogryListner, onMealClickListener.onMealClickListenerCat,IsearchMealView.IsearchAllViewsMeals, onAddFavMealClickListner,onMealPlanningClick {
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        catRec=view.findViewById(R.id.categoryRec);
        catRec.setHasFixedSize(true);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //layoutManager.setOrientation(catRec.VERTICAL);

        catRec.setLayoutManager(new GridLayoutManager(getContext(),2));

        //catRec.setLayoutManager(layoutManager);
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
        filterAdapter = new FilterByCategoriesAdapter(category.this.getContext(),meals,this,this,this);
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

    @Override
    public void onMealScheduleClicked(Meal meal) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Trigger a date picker dialog restricted to future dates
        DatePickerDialog datePickerDialog = new DatePickerDialog(category.this.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Format the selected date
                    //String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                    // After selecting the date, show the time picker dialog restricted to future times
                    TimePickerDialog timePickerDialog = new TimePickerDialog(category.this.getContext(),
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

        plannedDbObj = calAppDataBase.getDbInstance(category.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(category.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
}