package Feed.ui.home;

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
import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentHomeBinding;

import java.util.List;

import Feed.Controllers.RandomMealPresenter;
import Network.Model.Meal;
import Network.Model.MealsRemoteDataSource;

public class HomeFragment extends Fragment implements IRandomMealView {
    private ImageView randomMealView;
    private FragmentHomeBinding binding;
    private RandomMealPresenter randomPresenter;
    private MealsRemoteDataSource randomSrc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        randomMealView = binding.imageViewHome;
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
        Glide.with(this).load(meal.get(0).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(randomMealView);
    }

    @Override
    public void displayError(String errorMsg) {

    }
}