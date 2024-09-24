//package Network.View;
//
//import static android.os.Build.VERSION_CODES.R;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.designpatternlabone.AllProducts.onClickAddListner;
//import com.example.designpatternlabone.Network.Model.Product;
//import com.example.designpatternlabone.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
//    {
//        private final Context context;
//        private static List<Product> values ;
//        private static final String TAG="RecyclerView";
//        private onClickAddListner listener;
//        public ProductAdapter(Context context, List<Product> values , onClickAddListner listner_) {
//            this.context = context;
//            this.values = new ArrayList<>(values.size());
//            this.values = values;
//            this.listener=listner_;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder{
//            private TextView title;
//            private TextView price;
//            private TextView brand;
//            private TextView description;
//            private ImageView imageV;
//            private RatingBar ratingbar;
//            private ConstraintLayout conLayout;
//            private View layoutView;
//            private Button addBtn;
//
//            //maybe generate a problem
//            public ViewHolder(View layoutView) {
//                super(layoutView);
//                this.layoutView = layoutView;
//                title=layoutView.findViewById(R.id.favtitleE);
//                price=layoutView.findViewById(R.id.favpriceE);
//                brand=layoutView.findViewById(R.id.favbrandE);
//                description=layoutView.findViewById(R.id.favdescribtionE);
//                imageV=layoutView.findViewById(R.id.favImageView2);
//                ratingbar=layoutView.findViewById(R.id.favratingBar);
//                ratingbar.setIsIndicator(true);
//                addBtn=layoutView.findViewById(R.id.addButton);
//            }
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
//            View tempV=cusInflater.inflate(R.layout.product_layout, parent,false);
//            ViewHolder tempHolder= new ViewHolder(tempV);
//            return tempHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//            holder.title.setText(values.get(position).getTitle());
//            holder.description.setText(values.get(position).getDescription());
//            holder.ratingbar.setRating(values.get(position).getRating());
//            holder.price.setText(String.valueOf(values.get(position).getPrice()));
//            holder.brand.setText(values.get(position).getBrand());
//            Glide.with(this.context).load(values.get(position).getThumbnail())
//                    .apply(new RequestOptions().override(350,313)
//                            .placeholder(R.drawable.ic_launcher_background)
//                            .error(R.drawable.ic_launcher_foreground))
//                    .into(holder.imageV);
//            holder.addBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onFavProductAdd(values.get(position));
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return values.size();
//        }
//        public static void setList(List<Product> product_)
//        {
//            values = product_;
//        }
//    }
//
//
//
