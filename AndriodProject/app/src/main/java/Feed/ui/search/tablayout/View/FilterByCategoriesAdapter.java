package Feed.ui.search.tablayout.View;

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

import Model.Category;
import Model.Meal;


public class FilterByCategoriesAdapter extends RecyclerView.Adapter<FilterByCategoriesAdapter.ViewHolder>
{
    private final Context context;
    private List<Meal> values ;
    private  final String TAG="FirstLRecyclerView";

    public FilterByCategoriesAdapter(Context context, List<Meal> meals ) {
        this.context = context;
        if(null != meals)
        {
            this.values = new ArrayList<Meal>(meals.size());
            this.values = meals;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>();
            Meal noMeal = new Meal();
            noMeal.setStrMeal("No Meal Found");
            this.values.add(noMeal);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public Button filterBtn;
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
    public FilterByCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.firstlettersearchmeals, parent,false);
        FilterByCategoriesAdapter.ViewHolder tempHolder= new FilterByCategoriesAdapter.ViewHolder(tempV);
        Log.i("NAMEEE", "YES: ");

        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FilterByCategoriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mealNameText.setText(values.get(position).getStrMeal());
        Glide.with(this.context).load(values.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageV);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



