package controller;

import metier.Catalogue;

public class ControllerAchatVente extends ControllerManager {

    public static String[] getNomsProduits()
    {
        return ControllerAchatVente.cat.getNomProduits();
    }

    public static boolean acheterProduit(String nomProduit, int qteAchetee)
    {
        return ControllerAchatVente.cat.acheterStock(nomProduit, qteAchetee);
    }

    public static boolean vendreProduit(String nomProduit, int qteAchetee)
    {
        return ControllerAchatVente.cat.vendreStock(nomProduit, qteAchetee);
    }

}
