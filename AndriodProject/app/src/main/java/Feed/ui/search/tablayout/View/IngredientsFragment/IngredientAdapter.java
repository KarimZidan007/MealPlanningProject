package Feed.ui.search.tablayout.View.IngredientsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sidechefproject.R;

import java.util.ArrayList;
import java.util.List;

import Model.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
private final Context context;
private List<Ingredient> values;
private  onClickListByIngredient  listner;
    private final String COUNTRY_TAG= "CountryRecyclerView";
    public IngredientAdapter(Context context, List<Ingredient> ingredients, onClickListByIngredient listner)
    {
        this.context=context;
        if(ingredients != null)
        {
            values = new ArrayList<Ingredient>(ingredients.size());
            this.values=ingredients;
            this.listner=listner;
        }
        else{
            values = new ArrayList<Ingredient>(1);
            Ingredient temp = new Ingredient();
            temp.setStrIngredient("ERROR");
            values.add(temp);
            this.listner=listner;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.ingredient_frag_content, parent,false);
        IngredientAdapter.ViewHolder tempHolder= new IngredientAdapter.ViewHolder(tempV);
        return tempHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onFilterByIngredient(values.get(position).getStrIngredient());
            }
        });
        holder.ingredientName.setText(values.get(position).getStrIngredient());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public Button listBtn;
        public View layoutView;
        public TextView ingredientName;
        public ImageView ingredientImage;

        public ViewHolder(@NonNull View LayoutView) {
            super(LayoutView);
            this.layoutView=LayoutView;
            ingredientName=layoutView.findViewById(R.id.IngredientName);
            listBtn=layoutView.findViewById(R.id.ListMealsI);
        }
    }
}
