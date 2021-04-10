package controller;

import metier.catalogue.Catalogue;
import metier.catalogue.CatalogueFactory;
import metier.catalogue.I_Catalogue;

import java.util.ArrayList;
import java.util.List;

public class ControllerManager
{

    protected static I_Catalogue cat;

    protected static List<I_Catalogue> cats;

    public static void aggresiveCatalogueLoading()
    {
        cats = CatalogueFactory.getAllCatalogue();
    }

    public static String[] getCatalogueNames(){
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats){
            catNames.add(cat.getName());
        }
        return catNames.toArray(new String[0]);
    }

    public static String[] getCatalogueNamesAndNumber()
    {

    }

    public static void chooseCatalogue(){
        cat = CatalogueFactory.getInstance("la-redoute");
    }

    public static void disconnect()
    {
        cat.clear();
    }

}
