package Feed.ui.search.tablayout.View.CountriesFragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Model.Country;
import android.content.Context;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.caverock.androidsvg.SVG;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sidechefproject.R;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
private final Context context;
private List<Country> values;
private onClickListByCountry listner;
private final String BASELINK = "https://raw.githubusercontent.com/hjnilsson/country-flags/master/svg/";
private final String[] countryCodes = {
            "us", // American
            "gb", // British
            "ca", // Canadian
            "cn", // Chinese
            "hr", // Croatian
            "nl", // Dutch
            "eg", // Egyptian
            "ph", // Filipino
            "fr", // French
            "gr", // Greek
            "in", // Indian
            "ie", // Irish
            "it", // Italian
            "jm", // Jamaican
            "jp", // Japanese
            "ke", // Kenyan
            "my", // Malaysian
            "mx", // Mexican
            "ma", // Moroccan
            "pl", // Polish
            "pt", // Portuguese
            "ru", // Russian
            "es", // Spanish
            "th", // Thai
            "tn", // Tunisian
            "tr", // Turkish
            "ua", // Ukrainian
            "vn", // Vietnamese
            "vn",
            "vn",
            "vn",
    };
    public CountryAdapter(Context context, List<Country> countries, onClickListByCountry listner_)
    {
        this.context=context;
        if(countries != null)
        {
            values = new ArrayList<Country>(countries.size());
            this.values=countries;
            this.listner=listner_;
        }
        else{
            values = new ArrayList<Country>(1);
            Country x = new Country();
            x.setStrArea("ERROR WHILE FETCHING");
            values.add(x);
            this.listner=listner_;
        }
    }

    @NonNull
    @java.lang.Override
    public ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        LayoutInflater cusInflater = LayoutInflater.from(parent.getContext());
        View tempV=cusInflater.inflate(R.layout.country_frag_content , parent,false);
        CountryAdapter.ViewHolder tempHolder= new CountryAdapter.ViewHolder(tempV);
        return tempHolder;    }

    @java.lang.Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onFilterByCountry(values.get(position).getStrArea());
            }
        });
        holder.countryName.setText(values.get(position).getStrArea());
        Glide.with(this.context).load("https://flagsapi.com/"+countryCodes[position].toUpperCase()+"/shiny/64.png")
                .apply(new RequestOptions().override(350,313)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.countryImage);
    }

    @java.lang.Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public Button listBtn;
        public View layoutView;
        public TextView countryName;
        public ImageView countryImage;

        public ViewHolder(@NonNull View LayoutView) {
            super(LayoutView);
            this.layoutView=LayoutView;
            countryName=layoutView.findViewById(R.id.IngredientName);
            listBtn=layoutView.findViewById(R.id.ListMealsI);
            countryImage= layoutView.findViewById(R.id.imageVI);

        }
    }
}
