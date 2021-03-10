package controller;

import exception.database.MAJImpossible;
import metier.Catalogue;

public class ControllerAchatVente extends ControllerManager {

    public static String[] getNomsProduits()
    {
        return cat.getNomProduits();
    }

    public static boolean acheterProduit(String nomProduit, int qteAchetee) {

            return cat.acheterStock(nomProduit, qteAchetee);


    }

    public static boolean vendreProduit(String nomProduit, int qteAchetee) {

            return cat.vendreStock(nomProduit, qteAchetee);

    }

}
