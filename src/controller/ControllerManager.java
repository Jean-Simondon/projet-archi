package controller;

import metier.catalogue.CatalogueFactory;
import metier.catalogue.I_Catalogue;

public class ControllerManager
{

    protected static I_Catalogue cat;

    public static void aggresiveLoading()
    {
        cat = CatalogueFactory.getInstance();
    }

    public static void disconnect()
    {
        cat.clear();
    }

}
