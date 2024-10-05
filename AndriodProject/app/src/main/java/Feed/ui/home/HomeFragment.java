package Feed.ui.home;

import static Feed.ui.favourite.Controller.FavoriteManager.isFavorite;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentHomeBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.MealsByCountry.MealsCountriesPresenter;
import Feed.Controllers.MealsByIngredient.MealsIngredientPresenter;
import Feed.Controllers.MealsCategoriesPresenter;
import Feed.Controllers.RandomMealPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Feed.ui.search.tablayout.View.CountriesFragment.country;
import Feed.ui.search.tablayout.View.IngredientsFragment.ingreident;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Model.Category;
import Model.Country;
import Model.Ingredient;
import Model.Meal;
import Model.MealDate;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;

public class HomeFragment extends Fragment implements IRandomMealView , onClickRemoveFavourite, onAddFavMealClickListner,IsearchMealView.IgetMealCategoriesView,IsearchMealView.IgetMealCountriesView,IsearchMealView.IgetMealIngredientsView{
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
    private MealsCategoriesPresenter catPresenter;
    private MealsRemoteDataSource dataSource;
    private FavoriteManager favManager;
    private ImageView categoryImage;
    private TextView categoryText;
    private TextView countryText;
    private TextView ingText;
    private ImageView countryImage;
    private ImageView ingImage;
    private MealsIngredientPresenter ingredientPresenter;
    private  MealsCountriesPresenter countryPresenter;
    private final String BASELINK = "https://raw.githubusercontent.com/hjnilsson/country-flags/master/svg/";
    private final String[] countryCodes = {
            "us", // American
            "gb", // British
            "ca", // Canadian
            "cn", // Chinese
            "hr", // Croatian
            "nl", // Dutch
            "eg", // Egyptian
            "ph", // Filipino
            "fr", // French
            "gr", // Greek
            "in", // Indian
            "ie", // Irish
            "it", // Italian
            "jm", // Jamaican
            "jp", // Japanese
            "ke", // Kenyan
            "my", // Malaysian
            "mx", // Mexican
            "ma", // Moroccan
            "pl", // Polish
            "pt", // Portuguese
            "ru", // Russian
            "es", // Spanish
            "th", // Thai
            "tn", // Tunisian
            "tr", // Turkish
            "ua", // Ukrainian
            "vn", // Vietnamese
            "vn",
            "vn",
            "vn",
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageV = binding.randomImage;
        mealNameText = binding.randomText;
        iconImage=binding.favoriteIcon;
        schedualeIcon=binding.scheduleIcon;
        categoryImage = root.findViewById(R.id.category_image);
        categoryText = root.findViewById(R.id.category_text);
        countryImage=root.findViewById(R.id.country_image);
        countryText=root.findViewById(R.id.country_text);
        ingImage=root.findViewById(R.id.ingredient_image);
        ingText=root.findViewById(R.id.ingredient_text);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNetworkAvailable(getContext())) {
            randomSrc= MealsRemoteDataSource.getRemoteSrcClient();
            randomPresenter = new RandomMealPresenter(randomSrc,this);
            randomPresenter.getRandomMealRemotly();


            dataSource= MealsRemoteDataSource.getRemoteSrcClient();
            catPresenter = new MealsCategoriesPresenter(dataSource,(IsearchMealView.IgetMealCategoriesView)HomeFragment.this);
            catPresenter.reqMealsCategories();


            dataSource= MealsRemoteDataSource.getRemoteSrcClient();
            countryPresenter = new MealsCountriesPresenter(dataSource,(IsearchMealView.IgetMealCountriesView)HomeFragment.this);
            countryPresenter.reqMealsCountries();


            dataSource= MealsRemoteDataSource.getRemoteSrcClient();
            ingredientPresenter = new MealsIngredientPresenter(dataSource,(IsearchMealView.IgetMealIngredientsView) HomeFragment.this);
            ingredientPresenter.reqMealsIngredients();

        } else {
            // Show a message to the user
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void displayRandomMeal(List<Meal> meal) {
        if(!isFavorite(meal.get(0).getIdMeal()))
        {
            isFav=false;
        }
        else
        {
           iconImage.setImageResource(R.drawable.ic_favorite_filled);
         isFav=true;
        }
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
                    FavoriteManager.toggleFavorite(meal.get(0));

                }
                else
                {
                    onFavMealRemove(meal.get(0));
                    iconImage.setImageResource(R.drawable.fav);
                    isFav=false;
                    FavoriteManager.toggleFavorite(meal.get(0));

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

        // Parse the date string to LocalDate using the correct format (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        // Get the day of the week from LocalDate
        String dayOfWeek = localDate.getDayOfWeek().toString(); // e.g., "SUNDAY", "MONDAY"
        String formattedDayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase(); // e.g., "Sunday"

        MealDate mealDate = new MealDate(meal, date, time,formattedDayOfWeek);

        plannedDbObj = calAppDataBase.getDbInstance(HomeFragment.this.getContext());
        mealDateDao = plannedDbObj.getDateMealsDao();

        new Thread(() -> mealDateDao.insertPlannedMeal(mealDate)).start();

        Toast.makeText(HomeFragment.this.getContext(), "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    public void displayMealsCateogries(List<Category> categories) {
        Random random = new Random();
        int randomIndex = random.nextInt(categories.size());
        Category randomCategory = categories.get(randomIndex);

        categoryText.setText(randomCategory.getStrCategory());
        Glide.with(this).load(randomCategory.getStrCategoryThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(categoryImage);
        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_search);
            }
        });
    }

    @Override
    public void displayErrorByName(String errorMsg) {

    }

    @Override
    public void displayMealsCountries(List<Country> countries) {
        Random random = new Random();
        int randomIndex = random.nextInt(countries.size());
        Country randomCountry = countries.get(randomIndex);

        countryText.setText(randomCountry.getStrArea());

        Glide.with(this.getContext()).load("https://flagsapi.com/"+countryCodes[randomIndex].toUpperCase()+"/shiny/64.png")
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(countryImage);

        countryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_search);
            }
        });
    }

    @Override
    public void displayErrorByCountries(String errorMsg) {

    }

    @Override
    public void displayMealsIngredients(List<Ingredient> ingredients) {
        Random random = new Random();
        int randomIndex = random.nextInt(ingredients.size());
        Ingredient randomIngredient = ingredients.get(randomIndex);

       ingText.setText(randomIngredient.getStrIngredient());
        Glide.with(this.getContext()).load("https://www.themealdb.com/images/ingredients/"+randomIngredient.getStrIngredient()+".png")
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(ingImage);
        ingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_search);
            }
        });
    }

    @Override
    public void displayErrorByIngredients(String errorMsg) {

    }
}