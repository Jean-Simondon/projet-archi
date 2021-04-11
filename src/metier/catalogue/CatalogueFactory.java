package metier.catalogue;

import dao.catalogue.CatalogueDAO;
import dao.catalogue.CatalogueDAOFactory;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.catalogue.CatalogueException;
import metier.catalogue.Catalogue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogueFactory
{

    private static I_Catalogue actualInstance;
    private static final String TAG = "CatalogueFactory";

    public static I_Catalogue getInstance(String nomCatalogue)
    {
        Logger.getLogger(TAG).log(Level.INFO,"getInstance of Catalogue");
        try {
            actualInstance = CatalogueDAOFactory.getInstance().readByName(nomCatalogue);
            return actualInstance;
        } catch (ReadException | HydrateException | CatalogueException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static I_Catalogue getInstance(){
        return actualInstance;
    }
}
