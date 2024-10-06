package Feed.ui.calendar.View;

import static Feed.ui.favourite.Controller.FavoriteManager.isFavorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

import Feed.ui.favourite.Controller.FavoriteManager;
import Feed.ui.favourite.View.onClickRemoveFavourite;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Model.MealDate;


public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder>
{
    private final Context context;
    private List<MealDate> values ;
    private onAddFavMealClickListner addFavMealListner;
    private onDeletePlanMealClick delMealFromPLanListner;
    private onMealClickListener.onMealClickSearchListener listener;
    private onClickRemoveFavourite removeListner;

    public CalenderAdapter(Context context, List<MealDate> meals ,onDeletePlanMealClick delMealFromPLanListner ,onMealClickListener.onMealClickSearchListener listener,onAddFavMealClickListner addFavMealListner, onClickRemoveFavourite removeListner) {
        this.context = context;
        this.addFavMealListner=addFavMealListner;
        this.listener=listener;
        this.delMealFromPLanListner=delMealFromPLanListner;
        this.removeListner=removeListner;

        if(null != meals)
        {
            this.values = new ArrayList<MealDate>(meals.size());
            this.values = meals;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>();
            MealDate noMeal = new MealDate(new Meal(),"","","");
            this.values.add(noMeal);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View layoutView;
        private TextView mealNameText;
        private TextView mealDayText;
        private TextView mealTimeText;
        private ImageView imageV;
        private ImageView favIcon;
        private ImageView delIcon;
        private boolean isFav=false;

        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            imageV=layoutView.findViewById(R.id.meal_picture);
            mealNameText=layoutView.findViewById(R.id.meal_name);
            mealDayText=layoutView.findViewById(R.id.txt_day_of_week);
            mealTimeText=layoutView.findViewById(R.id.txt_time);
            favIcon = itemView.findViewById(R.id.favIcon);
            delIcon = itemView.findViewById(R.id.schedualeIcon);
        }
    }

    @NonNull
    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.container_list_card, parent,false);
        CalenderAdapter.ViewHolder tempHolder= new CalenderAdapter.ViewHolder(tempV);
        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.isFav = isFavorite(values.get(position).getIdMeal());
        if(holder.isFav)
        {
            holder.favIcon.setImageResource(R.drawable.ic_favorite_filled);
        }
        else
        {
            holder.favIcon.setImageResource(R.drawable.fav);
        }
        holder.mealNameText.setText(values.get(position).getStrMeal());
        holder.mealTimeText.setText(values.get(position).getTime());

        holder.mealDayText.setText(values.get(position).getDay());

        Glide.with(this.context).load(values.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageV);

        holder.imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal temp =  new Meal(values.get(position));

                listener.onMealClick(temp);
            }
        });

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal temp =  new Meal(values.get(position));
                if(!holder.isFav)
                {
                    addFavMealListner.onFavMealAdd(temp);
                    holder.favIcon.setImageResource(R.drawable.ic_favorite_filled); // Change to filled heart
                    holder.isFav=true;
                    FavoriteManager.toggleFavorite(values.get(position));
                }
                else
                {
                    holder.favIcon.setImageResource(R.drawable.fav);
                    removeListner.onFavMealRemove(temp);
                    holder.isFav=false;
                    FavoriteManager.toggleFavorite(values.get(position));
                }
            }
        });
        holder.delIcon.setOnClickListener(v -> {
            delMealFromPLanListner.onDeleteMealScheduleClicked(values.get(position));
        });

    }

    public void setList(List<MealDate> meals)
    {
        this.values=meals;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



