package Model;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("strArea")
    public String strArea;

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String StrArea) {
        this.strArea = StrArea;
    }


}
