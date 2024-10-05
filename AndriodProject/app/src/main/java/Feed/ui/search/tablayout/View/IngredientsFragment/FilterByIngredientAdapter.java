package Feed.ui.search.tablayout.View.IngredientsFragment;

import static Feed.ui.favourite.Controller.FavoriteManager.isFavorite;

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
import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.onClickRemoveFavourite;
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
    private onClickRemoveFavourite removeListner;
    public FilterByIngredientAdapter(Context context, List<Meal> meals, onMealClickListener.onMealClickListenerIngreident mealDetailsListner, onAddFavMealClickListner addFavMealListner, onMealPlanningClick addMealtoPlan , onClickRemoveFavourite removeListner) {
        this.context = context;
        this.addMealtoPlan=addMealtoPlan;
        this.removeListner=removeListner;
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
        private View layoutView;
        private TextView mealNameText;
        private ImageView imageV;
        private ImageView iconImage;
        private ImageView schedualeIcon;
        private boolean isFav=false;

        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            imageV=layoutView.findViewById(R.id.meal_picture);
            mealNameText=layoutView.findViewById(R.id.meal_name);
            iconImage = itemView.findViewById(R.id.favIcon);
            schedualeIcon=itemView.findViewById(R.id.schedualeIcon);
        }
    }

    @NonNull
    @Override
    public FilterByIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.container_list_card_withplan, parent,false);
        FilterByIngredientAdapter.ViewHolder tempHolder= new FilterByIngredientAdapter.ViewHolder(tempV);
        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FilterByIngredientAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(!isFavorite((values.get(position).getIdMeal())))
        {
            holder.isFav=false;
        }
        else
        {
            holder.iconImage.setImageResource(R.drawable.ic_favorite_filled);
            holder.isFav=true;
        }
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
                if(!holder.isFav)
                {
                    addFavMealListner.onFavMealAdd(values.get(position));
                    holder.iconImage.setImageResource(R.drawable.ic_favorite_filled);
                    holder.isFav=true;
                    FavoriteManager.toggleFavorite(values.get(position));
                }
                else
                {
                    removeListner.onFavMealRemove(values.get(position));
                    holder.iconImage.setImageResource(R.drawable.fav);
                    holder.isFav=false;
                    FavoriteManager.toggleFavorite(values.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



