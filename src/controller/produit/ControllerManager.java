package controller.produit;

import metier.catalogue.CatalogueFactory;
import metier.catalogue.I_Catalogue;

public class ControllerManager
{

    protected static I_Catalogue cat;

    public static void aggresiveLoading(String nomCatalogue)
    {
        cat = CatalogueFactory.getInstance(nomCatalogue);
        cat.load();
    }

    public static void disconnect()
    {
        cat.clear();
    }

}
