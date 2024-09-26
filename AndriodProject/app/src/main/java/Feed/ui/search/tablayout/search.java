package Feed.ui.search.tablayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sidechefproject.R;

import java.util.List;

import Feed.Controllers.SearchMealPresenter;
import Feed.Controllers.searchFragPresenter;
import Feed.ui.search.IsearchMealView;
import Feed.ui.search.SearchAdapter;
import Feed.ui.search.SearchFragment;
import Model.Meal;
import Network.Model.MealsRemoteDataSource;

public class search extends Fragment  implements IsearchMealView.IsearchAllViewsMeals {
    SearchView search;
    RecyclerView recView;
    MealsRemoteDataSource  searchSrc ;
    searchFragPresenter searchMealPresenter;
    SearchAdapter searchAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search=view.findViewById(R.id.searchBar);
        recView=view.findViewById(R.id.searchRec);
        recView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(recView.HORIZONTAL);
        recView.setLayoutManager(layoutManager);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSrc= MealsRemoteDataSource.getRemoteSrcClient();
                //searchMealPresenter = new SearchMealPresenter.searchFragPresenter(searchSrc, search.this);
                searchMealPresenter = new searchFragPresenter(searchSrc,search.this);
                searchMealPresenter.getMealByNameRemotly(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() == 1) {
                    searchSrc= MealsRemoteDataSource.getRemoteSrcClient();
                    searchMealPresenter = new searchFragPresenter(searchSrc,search.this);
                    char firstCharacter = newText.charAt(0);
                    searchMealPresenter.getMealByFirstCharRemotly(firstCharacter);
                }
                return false;
            }
        });
    }
    @Override
    public void displayFirstLMeals(List<Meal> meals) {
        searchAdapter = new SearchAdapter(search.this.getContext(),meals);
        recView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }

    @Override
    public void displayError(String errorMsg) {
        Toast.makeText(search.this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMealsByName(List<Meal> meals) {
        searchAdapter = new SearchAdapter(search.this.getContext(),meals);
        recView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        Log.i("NAMEEE", "displayMealsByName: ");
    }

    @Override
    public void displayErrorByName(String errorMsg) {
        Toast.makeText(search.this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();

    }

}