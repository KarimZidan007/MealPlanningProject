package Feed.ui.search.tablayout.View.IngredientsFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.R;

import java.util.ArrayList;
import java.util.List;

import Feed.ui.calendar.View.onMealPlanningClick;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;


public class FilterByIngredientAdapter extends RecyclerView.Adapter<FilterByIngredientAdapter.ViewHolder>
{
    private final Context context;
    private List<Meal> values ;
    private  final String TAG="FirstLRecyclerView";
    private  onMealClickListener.onMealClickListenerIngreident mealDetailsListner;
    private onAddFavMealClickListner addFavMealListner;
    private onMealPlanningClick addMealtoPlan;

    public FilterByIngredientAdapter(Context context, List<Meal> meals, onMealClickListener.onMealClickListenerIngreident mealDetailsListner, onAddFavMealClickListner addFavMealListner, onMealPlanningClick addMealtoPlan) {
        this.context = context;
        this.addMealtoPlan=addMealtoPlan;
        if(null != meals)
        {
            this.values = new ArrayList<Meal>(meals.size());
            this.values = meals;
            this.mealDetailsListner=mealDetailsListner;
            this.addFavMealListner=addFavMealListner;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>(1);
            Meal noMeal = new Meal();
            noMeal.setStrMeal("No Meal Found");
            this.values.add(noMeal);
            this.mealDetailsListner=mealDetailsListner;
            this.addFavMealListner=addFavMealListner;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View layoutView;
        public TextView mealNameText;
        public ImageView imageV;
        ImageView iconImage;
        ImageView schedualeIcon;

        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            imageV=layoutView.findViewById(R.id.meal_image);
            mealNameText=layoutView.findViewById(R.id.meal_name);
            iconImage = itemView.findViewById(R.id.meal_favorite_icon);
            schedualeIcon=itemView.findViewById(R.id.schedule_icon_del);
        }
    }

    @NonNull
    @Override
    public FilterByIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.meal_card_layout_unfav, parent,false);
        FilterByIngredientAdapter.ViewHolder tempHolder= new FilterByIngredientAdapter.ViewHolder(tempV);
        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FilterByIngredientAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mealNameText.setText(values.get(position).getStrMeal());
        Glide.with(this.context).load(values.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageV);
        holder.schedualeIcon.setOnClickListener(v -> {
            addMealtoPlan.onMealScheduleClicked(values.get(position));
        });
        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealDetailsListner.onMealIngreidentClick(values.get(position).getStrMeal());
            }
        });
        holder.iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavMealListner.onFavMealAdd(values.get(position));
                holder.iconImage.setImageResource(R.drawable.ic_favorite_filled);

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



