package Network.Model.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import Model.Category;

public class CategoriesResponse {

    @SerializedName("categories")
    public List<Category> categories;
}
