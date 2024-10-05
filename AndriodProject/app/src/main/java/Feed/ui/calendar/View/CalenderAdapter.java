package Feed.ui.calendar.View;

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
    private onMealClickListener.onMealClickListenerCat mealDetailsListner;
    public CalenderAdapter(Context context, List<MealDate> meals ,onDeletePlanMealClick delMealFromPLanListner ,onMealClickListener.onMealClickListenerCat mealDetailsListner,onAddFavMealClickListner addFavMealListner) {
        this.context = context;
        this.addFavMealListner=addFavMealListner;
        this.mealDetailsListner=mealDetailsListner;
        this.delMealFromPLanListner=delMealFromPLanListner;
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

        holder.mealNameText.setText(values.get(position).getStrMeal());
        holder.mealTimeText.setText(values.get(position).getTime());

        holder.mealDayText.setText(values.get(position).getDay());

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
        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal temp =  new Meal(values.get(position));
                addFavMealListner.onFavMealAdd(temp);
                holder.favIcon.setImageResource(R.drawable.ic_favorite_filled); // Change to filled heart
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



