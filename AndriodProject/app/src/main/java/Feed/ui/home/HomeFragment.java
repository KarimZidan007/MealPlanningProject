package Feed.ui.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentHomeBinding;

import java.util.Calendar;
import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.RandomMealPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;

public class HomeFragment extends Fragment implements IRandomMealView , onClickRemoveFavourite, onAddFavMealClickListner {
    private FragmentHomeBinding binding;
    private RandomMealPresenter randomPresenter;
    private MealsRemoteDataSource randomSrc;
    public TextView mealNameText;
    public ImageView imageV;
    public ImageView iconImage;
    public ImageView schedualeIcon;
    boolean isFav=false;
    private addFavMealPresenter favMealPresenter;
    private FavMealPresenter presenter;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private MealDateDao mealDateDao;
    private calAppDataBase plannedDbObj;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageV = binding.mealImage;
        iconImage = binding.mealFavoriteIcon;
        mealNameText = binding.mealName;
        schedualeIcon=root.findViewById(R.id.schedule_icon_del);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomSrc= MealsRemoteDataSource.getRemoteSrcClient();
        randomPresenter = new RandomMealPresenter(randomSrc,this);
        randomPresenter.getRandomMealRemotly();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void displayRandomMeal(List<Meal> meal) {
        mealNameText.setText(meal.get(0).getStrMeal());
        Glide.with(this).load(meal.get(0).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(imageV);
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealDetailsIntent = new Intent(HomeFragment.this.getContext(), MealDetailsActivity.class);
                mealDetailsIntent.putExtra("MEAL",meal.get(0));
                startActivity(mealDetailsIntent);
            }
        });
        iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFav)
                {
                    onFavMealAdd(meal.get(0));
                    iconImage.setImageResource(R.drawable.ic_favorite_filled);
                    isFav=true;
                }
                else
                {
                    onFavMealRemove(meal.get(0));
                    iconImage.setImageResource(R.drawable.fav);
                    isFav=false;
                }
            }
        });
        schedualeIcon.setOnClickListener(v -> {
                // Get the current date and time
                Calendar calendar = Calendar.getInstance();

                // Trigger a date picker dialog restricted to future dates
                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeFragment.this.getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            // Format the selected date
                            //String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                            // After selecting the date, show the time picker dialog restricted to future times
                            TimePickerDialog timePickerDialog = new TimePickerDialog(HomeFragment.this.getContext(),
                                    (timeView, hourOfDay, minute) -> {
                                        // Format the selected time
                                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);

                                        // Combine date and time

                                        // Insert the meal with the selected date and time into the database
                                        saveMealToDate( meal.get(0), selectedDate , selectedTime);
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


        });
    }

    @Override
    public void displayError(String errorMsg) {

    }

    @Override
    public void onFavMealRemove(Meal meal) {
        dataBaseObj = AppDataBase.getDbInstance(HomeFragment.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo);
        presenter.deleteMeal(meal);
    }

    @Override
    public void onFavMealAdd(Meal meal) {
        dataBaseObj = AppDataBase.getDbInstance(HomeFragment.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        favMealPresenter = new addFavMealPresenter(repo);
        favMealPresenter.insertFavMeal(meal);
    }
    private boolean isToday(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        return year == today.get(Calendar.YEAR) &&
                month == today.get(Calendar.MONTH) &&
                day == today.get(Calendar.DAY_OF_MONTH);
    }
    private void saveMealToDate(Meal meal, String date, String time) {
        // Create a new MealDate object with separate date and time
        MealDate mealDate = new MealDate(meal, date, time);

        plannedDbObj = calAppDataBase.getDbInstance(HomeFragment.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(HomeFragment.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
}