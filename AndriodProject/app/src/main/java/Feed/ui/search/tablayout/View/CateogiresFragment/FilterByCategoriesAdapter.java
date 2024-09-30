package Feed.ui.search.tablayout.View.CateogiresFragment;

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

import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;


public class FilterByCategoriesAdapter extends RecyclerView.Adapter<FilterByCategoriesAdapter.ViewHolder>
{
    private final Context context;
    private List<Meal> values ;
    private  final String TAG="FirstLRecyclerView";
    private onMealClickListener.onMealClickListenerCat mealDetailsListner;
    private onAddFavMealClickListner addFavMealListner;
    public FilterByCategoriesAdapter(Context context, List<Meal> meals , onMealClickListener.onMealClickListenerCat mealDetailsListner, onAddFavMealClickListner addFavMealListner) {
        this.context = context;
        if(null != meals)
        {
            this.values = new ArrayList<Meal>(meals.size());
            this.values = meals;
            this.mealDetailsListner = mealDetailsListner;
            this.addFavMealListner=addFavMealListner;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>();
            Meal noMeal = new Meal();
            noMeal.setStrMeal("No Meal Found");
            this.values.add(noMeal);
            this.mealDetailsListner = mealDetailsListner;
            this.addFavMealListner=addFavMealListner;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View layoutView;
        public TextView mealNameText;
        public ImageView imageV;
        ImageView iconImage;

        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            //catImage= layoutView.findViewById(R.id.imageCat);
            imageV=layoutView.findViewById(R.id.meal_image);
            mealNameText=layoutView.findViewById(R.id.meal_name);
            iconImage = itemView.findViewById(R.id.meal_favorite_icon);
        }
    }

    @NonNull
    @Override
    public FilterByCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.meal_card_layout_unfav, parent,false);
        FilterByCategoriesAdapter.ViewHolder tempHolder= new FilterByCategoriesAdapter.ViewHolder(tempV);
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
        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealDetailsListner.onMealCatClick(values.get(position).getStrMeal());
            }
        });
        holder.iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavMealListner.onFavMealAdd(values.get(position));
                holder.iconImage.setImageResource(R.drawable.ic_favorite_filled); // Change to filled heart

            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



