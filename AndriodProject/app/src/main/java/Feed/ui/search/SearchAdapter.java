package Feed.ui.search;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

import Network.Model.Meal;
import com.example.sidechefproject.R;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
{
    private final Context context;
    private static List<Meal> values ;
    private static final String TAG="FirstLRecyclerView";

    public SearchAdapter(Context context, List<Meal> values ) {
        this.context = context;
        this.values = new ArrayList<>(values.size());
        this.values = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageV;
        public ConstraintLayout conLayout;
        public View layoutView;


        //maybe generate a problem
        public ViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
            imageV=layoutView.findViewById(R.id.favImageView2);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.firstlettersearchmeals, parent,false);
        ViewHolder tempHolder= new ViewHolder(tempV);
        return tempHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

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
    public static void setList(List<Meal> meals)
    {
        values = meals;
    }
}



