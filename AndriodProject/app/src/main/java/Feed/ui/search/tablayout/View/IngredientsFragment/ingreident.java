package Feed.ui.search.tablayout.View.IngredientsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;

import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.controller.MealDAO;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.MealsByIngredient.MealsIngredientPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.tablayout.View.CountriesFragment.country;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Ingredient;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;


public class ingreident extends Fragment implements IsearchMealView.IgetMealFilterIngredientsView,IsearchMealView.IgetMealIngredientsView, onClickListByIngredient, onMealClickListener.onMealClickListenerIngreident,IsearchMealView.IsearchAllViewsMeals, onAddFavMealClickListner {
RecyclerView ingreidentRec;
IngredientAdapter ingredientAdapter;
MealsIngredientPresenter ingredientPresenter;
MealsRemoteDataSource dataSource;
FilterByIngredientAdapter filterIngAdapter;
searchFragPresenter searchMealPresenter;
    private addFavMealPresenter favMealPresenter;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private boolean isDetailRequest=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingridents, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingreidentRec=view.findViewById(R.id.ingredientRecycler);
        ingreidentRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(ingreidentRec.VERTICAL);
        ingreidentRec.setLayoutManager(layoutManager);
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
        filterIngAdapter = new FilterByIngredientAdapter(ingreident.this.getContext(),meals,this,this);
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
}

