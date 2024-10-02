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

import Feed.ui.search.tablayout.View.CateogiresFragment.onMealPlanningClick;
import Feed.ui.search.tablayout.View.onAddFavMealClickListner;
import Feed.ui.search.tablayout.View.onMealClickListener;
import Model.Meal;
import Model.MealDate;


public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder>
{
    private final Context context;
    private List<MealDate> values ;
    private onAddFavMealClickListner addFavMealListner;
    private onMealPlanningClick addMealtoPlan;
    private onMealClickListener.onMealClickListenerCat mealDetailsListner;
    //, onMealClickListener.onMealClickListenerCat mealDetailsListner, onAddFavMealClickListner addFavMealListner, onMealPlanningClick addMealtoPlan
    public CalenderAdapter(Context context, List<MealDate> meals ) {
        this.context = context;
       // this.addFavMealListner=addFavMealListner;
       // this.addMealtoPlan=addMealtoPlan;
       // this.mealDetailsListner=mealDetailsListner;
        if(null != meals)
        {
            this.values = new ArrayList<MealDate>(meals.size());
            this.values = meals;
        }
        else if(meals == null)
        {
            this.values = new ArrayList<>();
            MealDate noMeal = new MealDate("NoMealFound","","");
            this.values.add(noMeal);
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
        }
    }

    @NonNull
    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.meal_card_layout_unfav, parent,false);
        CalenderAdapter.ViewHolder tempHolder= new CalenderAdapter.ViewHolder(tempV);
        return tempHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

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
//        holder.iconImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFavMealListner.onFavMealAdd(values.get(position));
//                holder.iconImage.setImageResource(R.drawable.ic_favorite_filled); // Change to filled heart
//
//            }
//        });
//        holder.schedualeIcon.setOnClickListener(v -> {
//            addMealtoPlan.onMealScheduleClicked(values.get(position));
//        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}



