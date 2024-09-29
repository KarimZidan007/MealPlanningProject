package com.example.sidechefproject.MealDetails;


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

import java.util.List;

import Model.IngreidentDetails;

public class MealDetailsIngredientsAdapter extends RecyclerView.Adapter<MealDetailsIngredientsAdapter.IngredientViewHolder> {
    private IngreidentDetails ingredient;
    private List<IngreidentDetails> ingredients;
    private final Context context;
    public MealDetailsIngredientsAdapter(Context context , List<IngreidentDetails> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_video_ingredient_ofrecycler, parent, false); // Create an XML layout for individual items
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngreidentDetails ingredient = ingredients.get(position);
        holder.nameTextView.setText(ingredient.getName());
        holder.measureTextView.setText(ingredient.getMeasure());
        Glide.with(this.context).load("https://www.themealdb.com/images/ingredients/"+holder.nameTextView.getText()+".png")
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.imageV);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

     class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView measureTextView;
        ImageView imageV;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewIngredientName);
            measureTextView = itemView.findViewById(R.id.textViewIngredientMeasure);
            imageV = itemView.findViewById(R.id.imageViewIngredient);
        }
    }
}


