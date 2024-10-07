package Feed;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.sidechefproject.R;

import com.example.sidechefproject.databinding.ActivityNavBinding;

import DataBase.Model.AppDataBase;
import DataBase.Model.localSrcImplementation;
import DataBase.controller.MealDAO;
import Feed.ui.favourite.Controller.FavMealPresenter;
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Repository.DataSrcRepository;

public class NavActivity extends AppCompatActivity {
    private FavoriteManager favManager;
    private FavMealPresenter presenter;
    private AppDataBase dataBaseObj;
    private MealDAO dao;
    private DataSrcRepository repo;
    private ActivityNavBinding binding;
    private localSrcImplementation localSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications,R.id.navigation_calendar)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_nav);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        dataBaseObj = AppDataBase.getDbInstance(this);
        dao = dataBaseObj.getMealsDao();
        localSrc = new  localSrcImplementation(null,dao,null);
        repo = new DataSrcRepository (null,localSrc);
        presenter = new FavMealPresenter(repo);
        favManager=FavoriteManager.getInstance(presenter,this);
        FavoriteManager.loadFavoritesFromDatabase();


        checkInternetAndUpdateNavigation();

    }
    private void checkInternetAndUpdateNavigation() {
        if (!isNetworkAvailable(this)) {
            // Disable Home and Search items
            binding.navView.getMenu().findItem(R.id.navigation_home).setEnabled(false);
            binding.navView.getMenu().findItem(R.id.navigation_search).setEnabled(false);
            // Optionally show a message or toast here
        } else {
            // Enable Home and Search items
            binding.navView.getMenu().findItem(R.id.navigation_home).setEnabled(true);
            binding.navView.getMenu().findItem(R.id.navigation_search).setEnabled(true);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void showNoInternetUI() {

    }

}