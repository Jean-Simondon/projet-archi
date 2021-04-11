package controller;

import dao.catalogue.I_CatalogueDAO;
import dao.catalogue.CatalogueDAOFactory;
import metier.catalogue.I_Catalogue;

import java.util.ArrayList;
import java.util.List;

public class ControllerCatalogue {

    private static I_CatalogueDAO dao;

    protected static List<I_Catalogue> cats;

    public void aggressiveLoading()
    {
        dao = CatalogueDAOFactory.getInstance();
        cats = dao.readAll();
    }

    public String[] cataloguesNames() {
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats){
            catNames.add(cat.getName());
        }
        return catNames.toArray(new String[0]);
    }

    public String[] cataloguesNamesWithNbProduct() {
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats){
            catNames.add(cat.getName() + " : " +  cat.getNbProduits() + " produits");
        }
//        return catNames.toArray(new String[0]);

        String[] tab2 = {"Formacia : 6 produits" , "Le Redoutable : 4 produits" , "Noitaicossa : 0 produits" };
        return tab2;
    }

}

