package metier;

import exception.database.ReadException;

public class CatalogueFactory {

    private static final String TAG = "CatalogueFactory";

    public static Catalogue getInstance(){
        return new Catalogue();
    }
}
