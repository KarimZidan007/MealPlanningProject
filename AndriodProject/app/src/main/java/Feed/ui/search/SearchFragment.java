package Feed.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.example.sidechefproject.R;
import com.example.sidechefproject.databinding.FragmentSearchBinding;

import Feed.ui.search.tablayout.VPAdapter;
import Feed.ui.search.tablayout.View.CateogiresFragment.category;
import Feed.ui.search.tablayout.View.CountriesFragment.country;
import Feed.ui.search.tablayout.View.IngredientsFragment.ingreident;
import Feed.ui.search.tablayout.View.SearchFragment.search;

import com.google.android.material.tabs.TabLayout;

public class SearchFragment extends Fragment  {
    private FragmentSearchBinding binding;



    TabLayout tabLayout;
    ViewPager viewPager;
    VPAdapter adapter;

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

     return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);
         adapter = new VPAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        adapter.addFragment(new search(), "Search");
        adapter.addFragment(new category(), "Categories");
        adapter.addFragment(new country(), "Countries");
        adapter.addFragment(new ingreident(), "Ingredients");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // No action needed here
            }

            @Override
            public void onPageSelected(int position) {

                        }

            @Override
            public void onPageScrollStateChanged(int state) {
                // No action needed here
            }
        });
    }
    public void updateTabs() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}