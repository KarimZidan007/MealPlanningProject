package Feed.ui.search.tablayout.View.CateogiresFragment;
import android.annotation.SuppressLint;
import android.content.Context;
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



    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>
    {
        private final Context context;
        private List<Category> values ;
        private onClickFilterByCateogryListner listner;
        private  final String TAG="FirstLRecyclerView";
        public CategoriesAdapter(Context context, List<Category> categories, onClickFilterByCateogryListner listner_ ) {
            this.context = context;
            if(null != categories)
            {
                this.values = new ArrayList<Category>(categories.size());
                this.values = categories;
                listner=listner_;
            }
            else if(categories == null)
            {
                this.values = new ArrayList<>(1);
                Category noCategory = new Category();
                noCategory.setStrCategory("No Meal Found");
                this.values.add(noCategory);
                listner=listner_;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public Button filterBtn;
            public View layoutView;
            public TextView categoryNameText;
            public ImageView catImage;

            public ViewHolder(View layoutView) {
                super(layoutView);
                this.layoutView = layoutView;
                categoryNameText=layoutView.findViewById(R.id.categoryName);
                catImage= layoutView.findViewById(R.id.imageCat);
                //filterBtn=layoutView.findViewById(R.id.filterbTn);

            }
        }

        @NonNull
        @Override
        public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
            View tempV=cusInflater.inflate(R.layout.cateogry_frag_content, parent,false);
            CategoriesAdapter.ViewHolder tempHolder= new CategoriesAdapter.ViewHolder(tempV);
            return tempHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.catImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onFilterByCateogry(values.get(position).getStrCategory());
                }
            });
            holder.categoryNameText.setText(values.get(position).getStrCategory());
            Glide.with(this.context).load(values.get(position).getStrCategoryThumb())
                    .apply(new RequestOptions().override(313,200)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.catImage);

        }

        @Override
        public int getItemCount() {
            return values.size();
        }
    }



