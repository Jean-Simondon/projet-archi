package controller;

import dao.catalogue.I_CatalogueDAO;
import dao.catalogue.CatalogueDAOFactory;
import metier.catalogue.I_Catalogue;

import java.util.ArrayList;
import java.util.List;

public class ControllerCatalogue {

    private static I_CatalogueDAO dao;

    protected static List<I_Catalogue> cats;

    public static void aggressiveLoading()
    {
        dao = CatalogueDAOFactory.getInstance();
        cats = dao.readAll();
    }

    public static String[] cataloguesNames() {
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats) {
            catNames.add(cat.getName());
        }
        return catNames.toArray(new String[0]);
    }

    public static String[] cataloguesNamesWithNbProduct() {
        List<String> catNames = new ArrayList<String>();
        for (I_Catalogue cat : cats) {
            catNames.add(cat.getName() + " : " +  cat.getNbProduits() + " produits");
        }
        return catNames.toArray(new String[0]);
    }

    public static void ajouter(String nomCatalogue) {
        try{
            dao.create(nomCatalogue);
        }catch(CatalogueException e){
            Logger.getLogger(TAG).severe(() -> "Erreur pendant la suppresion");
        }
    }

    public static void enlever(String nomCatalogue) {
        try {
            dao.delete(nomCatalogue);
        }catch(DeleteException e){
            Logger.getLogger(TAG).severe(() -> "Erreur pendant la suppresion");
        }
    }


}

