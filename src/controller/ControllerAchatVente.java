package controller;

import exception.database.MAJImpossible;
import metier.Catalogue;

public class ControllerAchatVente extends ControllerManager {

    public static String[] getNomsProduits()
    {
        return ControllerAchatVente.cat.getNomProduits();
    }

    public static boolean acheterProduit(String nomProduit, int qteAchetee) {
        try {
            return ControllerAchatVente.cat.acheterStock(nomProduit, qteAchetee);

        }catch (MAJImpossible e){
            //TODO trouver quoi faire quand une exception est levée du à une propagation des changements de valeur impossible
            return false;
        }
    }

    public static boolean vendreProduit(String nomProduit, int qteAchetee) {
        try {
            return ControllerAchatVente.cat.vendreStock(nomProduit, qteAchetee);
        }catch (MAJImpossible e ){
            //TODO trouver quoi faire quand une exception est levée du à une propagation des changements de valeur impossible
            return false;
        }
    }

}
