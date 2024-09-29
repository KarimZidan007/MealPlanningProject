package Model;

public class IngreidentDetails {
    private String name;
    private String measure;

    public IngreidentDetails(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }
}
