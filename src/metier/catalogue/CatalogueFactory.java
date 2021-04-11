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

    private static final String TAG = "CatalogueFactory";

    public static I_Catalogue getInstance(String nomCatalogue)
    {
        Logger.getLogger(TAG).log(Level.INFO,"getInstance of Catalogue");
        try {
            return CatalogueDAOFactory.getInstance().readByName(nomCatalogue);
        } catch (ReadException | HydrateException | CatalogueException e) {
            e.printStackTrace();
        }
        return null;
    }
}
