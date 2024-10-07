package com.example.sidechefproject.MealDetails;

import static Feed.ui.favourite.Controller.FavoriteManager.isFavorite;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.Model.calAppDataBase;
import DataBase.Model.localSrcImplementation;
import DataBase.controller.MealDAO;
import DataBase.controller.MealDateDao;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.ui.calendar.Controller.CalendarPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.FavouriteFragment;
import Model.CountryMapping;
import Model.IngreidentDetails;
import Model.Meal;
import Model.MealDate;
import Repository.DataSrcRepository;

public class MealDetailsActivity extends AppCompatActivity {
    private WebView webView;
    private TextView mealName;
    private TextView counName;
    private TextView textViewDescription;
    private RecyclerView recyclerView;
    private Meal meal;
    private ImageView countFlag;
    private ImageView mealImage;
    private ImageView favIcon;
    private ImageView schedualeIcon;
    private final String BASELINK = "https://raw.githubusercontent.com/hjnilsson/country-flags/master/svg/";
    private CountryMapping mapping;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private boolean isFav=false;
    private addFavMealPresenter favMealPresenter;
    private FavMealPresenter presenter;
    private calAppDataBase plannedDbObj;
    private MealDateDao mealDateDao;
    private localSrcImplementation localSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_details);
        Intent intent = getIntent();
        if (intent != null) {
            mealName = findViewById(R.id.textViewName);
            webView =findViewById(R.id.webV);
            textViewDescription = findViewById(R.id.textViewD);
            recyclerView = findViewById(R.id.recI);
            counName=findViewById(R.id.countryName);
            countFlag=findViewById(R.id.countryFlag);
            mealImage=findViewById(R.id.mealPicture);
            favIcon=findViewById(R.id.addToFavIcon);
            schedualeIcon=findViewById(R.id.addToPlanIcon);
            meal = (Meal) intent.getSerializableExtra("MEAL");

            String youtubeUrl = meal.getStrYoutube();
            String videoId = "";
            if (youtubeUrl != null) {
                if (youtubeUrl.contains("v=")) {
                    videoId = youtubeUrl.split("v=")[1].split("&")[0];
                } else if (youtubeUrl.contains("youtu.be/")) {
                    videoId = youtubeUrl.split("youtu.be/")[1];
                }
            }
            if (!videoId.isEmpty()) {
                String embedUrl = "https://www.youtube.com/embed/" + videoId;
                String videoUrl = "<html><body style=\"margin:0;padding:0;\"><iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe></body></html>";
                webView.loadData(videoUrl, "text/html", "UTF-8");
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
            }
            else {
                    Toast.makeText(this, "No video available for this meal.", Toast.LENGTH_SHORT).show();
                }

            mealName.setText(meal.getStrMeal());
            String instructions = meal.getStrInstructions();
            String[] steps = instructions.split("\\.\\s*");
            StringBuilder formattedSteps = new StringBuilder();
            for (int i = 0; i < steps.length; i++) {
                formattedSteps.append("<b>STEP ").append(i + 1).append(": </b>");
                formattedSteps.append(steps[i].trim());
                formattedSteps.append("<br/><br/>");
            }
            textViewDescription.setText(Html.fromHtml(formattedSteps.toString(), Html.FROM_HTML_MODE_COMPACT));
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            List<IngreidentDetails> ingredientList = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ingredientName = null;
                try {
                    ingredientName = (String) meal.getClass().getDeclaredField("strIngredient" + i).get(meal);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                String ingredientMeasure = null;
                try {
                    ingredientMeasure = (String) meal.getClass().getDeclaredField("strMeasure" + i).get(meal);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                if (ingredientName != null && !ingredientName.isEmpty()) {
                    ingredientList.add(new IngreidentDetails(ingredientName, ingredientMeasure));
                }
                MealDetailsIngredientsAdapter ingredientAdapter = new MealDetailsIngredientsAdapter(this.getApplicationContext(), ingredientList);
                recyclerView.setAdapter(ingredientAdapter);
            }

            if(meal.getStrArea()!=null)
             counName.setText("This meal is :" + meal.getStrArea());
            else
                counName.setText("Unknown Originates");

            mapping = new CountryMapping();
            Glide.with(this).load("https://flagsapi.com/"+mapping.getCountryCode(meal.strArea).toUpperCase()+"/shiny/64.png")
                    .apply(new RequestOptions().override(350,313)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(countFlag);

            Glide.with(this).load(meal.getStrMealThumb())
                    .apply(new RequestOptions().override(350,313)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(mealImage);
            if(!isFavorite(meal.getIdMeal()))
            {
                favIcon.setImageResource(R.drawable.fav);
               isFav=false;
            }
            else
            {
                favIcon.setImageResource(R.drawable.ic_favorite_filled);
               isFav=true;
            }
            favIcon.setOnClickListener(v -> {
                if(!isFav)
                {
                    dataBaseObj = AppDataBase.getDbInstance(this);
                    dao = dataBaseObj.getMealsDao();
                    localSrc= new localSrcImplementation(null,dao,null);
                    repo = new DataSrcRepository(null,localSrc);
                    favMealPresenter = new addFavMealPresenter(repo);
                    favMealPresenter.insertFavMeal(meal);
                    favIcon.setImageResource(R.drawable.ic_favorite_filled);
                    isFav=true;
                    FavoriteManager.toggleFavorite(meal);
                }
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Remove from Favorites")
                            .setMessage("Are you sure you want to remove this meal from your favorites?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                // User confirmed deletion
                                dataBaseObj = AppDataBase.getDbInstance(this);
                                dao = dataBaseObj.getMealsDao();
                                localSrc = new localSrcImplementation(null,dao,null);
                                repo = new DataSrcRepository (null,localSrc);
                                presenter = new FavMealPresenter(repo);
                                presenter.deleteMeal(meal);
                                favIcon.setImageResource(R.drawable.fav);
                                isFav = false;
                                FavoriteManager.toggleFavorite(meal);
                                Toast.makeText(this, "Meal removed from favorites.", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton(android.R.string.no, (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                }});

            schedualeIcon.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            String selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear+1 , dayOfMonth);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
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
            });
        }
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

        plannedDbObj = calAppDataBase.getDbInstance(MealDetailsActivity.this);
        mealDateDao = plannedDbObj.getDateMealsDao();
        localSrcImplementation plannedLocalSrc=new localSrcImplementation (mealDateDao,null,null);
        repo =new DataSrcRepository(null,plannedLocalSrc) ;
        CalendarPresenter calendarPresenter = new CalendarPresenter(repo);
        repo.insertPlannedMeal(mealDate);
        Toast.makeText(MealDetailsActivity.this, "Meal scheduled for " + date + " at " + time, Toast.LENGTH_SHORT).show();
    }
    }

