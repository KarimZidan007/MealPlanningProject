package Feed.ui.search.tablayout.View.CountriesFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.R;

import java.util.ArrayList;
import java.util.List;

import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;


public class FilterByCountryAdapter extends RecyclerView.Adapter<FilterByCountryAdapter.ViewHolder>
{
    private final Context context;
    private List<Meal> values ;
    private  final String TAG="FirstLRecyclerView";
    private onMealClickListener.onMealClickListenerCountry mealDetailsListner;

    public FilterByCountryAdapter(Context context, List<Meal> meals, onMealClickListener.onMealClickListenerCountry mealDetailsListner ) {
        this.context = context;
        if(null != meals)
        {
            this.values = new ArrayList<Meal>(meals.size());
            this.values = meals;
            this.mealDetailsListner = mealDetailsListner;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>(1);
            Meal noMeal = new Meal();
            noMeal.setStrMeal("No Meal Found");
            this.values.add(noMeal);
            this.mealDetailsListner = mealDetailsListner;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View layoutView;
        public TextView mealNameText;
        public ImageView imageV;

        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            //catImage= layoutView.findViewById(R.id.imageCat);
            imageV=layoutView.findViewById(R.id.favImageView2);
            mealNameText=layoutView.findViewById(R.id.mealName);
        }
    }

    @NonNull
    @Override
    public FilterByCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.firstlettersearchmeals, parent,false);
        FilterByCountryAdapter.ViewHolder tempHolder= new FilterByCountryAdapter.ViewHolder(tempV);
        Log.i("NAMEEE", "YES FROM COUNTRY: ");

        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FilterByCountryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mealNameText.setText(values.get(position).getStrMeal());
        Glide.with(this.context).load(values.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageV);
        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealDetailsListner.onMealCountryClick(values.get(position).getStrMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



