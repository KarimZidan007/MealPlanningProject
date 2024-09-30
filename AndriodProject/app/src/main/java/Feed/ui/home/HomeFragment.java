package Feed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.MealDetails.MealDetailsActivity;
import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentHomeBinding;

import java.util.List;

import DataBase.Model.AppDataBase;
import DataBase.controller.MealDAO;
import Feed.Controllers.InsertingDBPresenter.addFavMealPresenter;
import Feed.Controllers.RandomMealPresenter;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.tablayout.View.CountriesFragment.country;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;
import Repository.DataSrcRepository;

public class HomeFragment extends Fragment implements IRandomMealView , onClickRemoveFavourite, onAddFavMealClickListner {
    private FragmentHomeBinding binding;
    private RandomMealPresenter randomPresenter;
    private MealsRemoteDataSource randomSrc;
    public TextView mealNameText;
    public ImageView imageV;
    public ImageView iconImage;
    boolean isFav=false;
    private addFavMealPresenter favMealPresenter;
    private FavMealPresenter presenter;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageV = binding.mealImage;
        iconImage = binding.mealFavoriteIcon;
        mealNameText = binding.mealName;
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
}