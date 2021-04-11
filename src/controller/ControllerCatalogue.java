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
        //TODO renvoyer liste de nom de catalogues
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats){
            catNames.add(cat.getName());
        }
        return catNames.toArray(new String[0]);
    }

    public String[] cataloguesNamesWithNbProduct() {
        //TODO renvoyer liste de nom de catalogues avec le nombre de produit associé
    }

}

