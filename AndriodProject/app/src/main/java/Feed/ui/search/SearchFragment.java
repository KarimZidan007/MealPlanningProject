package Feed.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentSearchBinding;
import com.google.android.material.search.SearchBar;

import java.util.List;

import Feed.Controllers.RandomMealPresenter;
import Feed.Controllers.SearchMealPresenter;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;

public class SearchFragment extends Fragment implements IsearchByFirstLMealView {
    SearchView search;
    private FragmentSearchBinding binding;
    MealsRemoteDataSource  searchByFirstLetterSrc ;
    SearchMealPresenter searchMealPresenter;
    RecyclerView recView;
    SearchAdapter searchAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        search = binding.searchBar;
        search.clearFocus();
        //return inflater.inflate(R.layout.firstlettersearchmeals, container, false);


     return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recView = (RecyclerView) view.findViewById(R.id.searchRec);
        recView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(recView.HORIZONTAL);
        recView.setLayoutManager(layoutManager);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() == 1) {
                    searchByFirstLetterSrc= MealsRemoteDataSource.getRemoteSrcClient();
                    searchMealPresenter = new SearchMealPresenter(searchByFirstLetterSrc,SearchFragment.this);
                    char firstCharacter = newText.charAt(0);
                    searchMealPresenter.searchByFirstCharacter(firstCharacter);

                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void displayFirstLMeals(List<Meal> meals) {
        searchAdapter = new SearchAdapter(SearchFragment.this.getContext(),meals);
        recView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }

    @Override
    public void displayError(String errorMsg) {

    }
}