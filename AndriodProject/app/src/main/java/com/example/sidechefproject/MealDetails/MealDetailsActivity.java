package com.example.sidechefproject.MealDetails;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;

import java.util.ArrayList;
import java.util.List;

import Feed.ui.search.tablayout.View.IngredientsFragment.IngredientAdapter;
import Model.IngreidentDetails;
import Model.Meal;

public class MealDetailsActivity extends AppCompatActivity {
    private WebView webView;
    private TextView mealName;
    private TextView textViewDescription;
    private RecyclerView recyclerView;
    private Meal meal;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_details);
        Intent intent = getIntent();
        if (intent != null) {

            meal = (Meal) intent.getSerializableExtra("MEAL");


            webView = findViewById(R.id.webV);
            videoUrl = meal.getStrYoutube();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(videoUrl);

            mealName = findViewById(R.id.textViewName);
            mealName.setText(meal.getStrMeal());

            textViewDescription = findViewById(R.id.textViewD);
            textViewDescription.setText(meal.getStrInstructions());

            recyclerView = findViewById(R.id.recI);
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

        }
    }
}
