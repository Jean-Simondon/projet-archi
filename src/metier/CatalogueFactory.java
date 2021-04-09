package metier;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogueFactory
{

    private static final String TAG = "CatalogueFactory";

    public static Catalogue getInstance()
    {
        Logger.getLogger(TAG).log(Level.INFO,"getInstance of Catalogue");
        return new Catalogue();
    }
}
