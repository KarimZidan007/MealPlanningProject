package Feed.ui.favourite.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;

import java.util.ArrayList;
import java.util.List;
import com.example.sidechefproject.MealDetails.MealDetailsActivity;

import DataBase.Model.AppDataBase;
import DataBase.controller.MealDAO;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Repository.DataSrcRepository;

public class FavouriteFragment extends Fragment implements onMealClickListener.onMealClickListenerFavourite,FavouriteMealView,onClickRemoveFavourite {

    private RecyclerView recyclerView;
    private FavoriteMealAdapter adapter;
    private List<Meal> favoriteMeals = new ArrayList<>();
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private FavMealPresenter presenter;
    LiveData<List<Meal>> liveData;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite,container,false);
        recyclerView = view.findViewById(R.id.recycler_favorites);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        adapter = new FavoriteMealAdapter(new ArrayList<>(), getContext(), FavouriteFragment.this, FavouriteFragment.this);
        recyclerView.setAdapter(adapter);

        dataBaseObj = AppDataBase.getDbInstance(FavouriteFragment.this.getContext());
        dao = dataBaseObj.getMealsDao();
        repo = new DataSrcRepository(dao);
        presenter = new FavMealPresenter(repo,this);



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
}