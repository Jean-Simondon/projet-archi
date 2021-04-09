package controller;

import metier.Produit;

public class ControllerCreationSuppression extends ControllerManager
{

    public ControllerCreationSuppression() {}

    public static String[] getNomsProduits()
    {
        return cat.getNomProduits();
    }

    public static boolean creerProduit(Produit produit)
    {
        return cat.addProduit(produit);
    }

    public static boolean creerProduit(String nom, double prix, int qte)
    {
        return cat.addProduit(nom, prix, qte);
    }

    public static boolean supprimerProduit(String nomProduit)
    {
        return cat.removeProduit(nomProduit);
    }
}
