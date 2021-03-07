package metier;

public class CatalogueFactory {

    private static final String TAG = "CatalogueFactory";

    public static Catalogue getInstance(){
        return new Catalogue();
    }
}
