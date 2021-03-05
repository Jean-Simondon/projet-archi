package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import metier.Produit;

import java.util.ArrayList;

public interface ProduitDAO_I {

    int create(String nom , double prixUnitaireHT, int qte);

    Produit read(int id) throws ReadException, HydrateException;

    ArrayList<Produit> readAll() throws ReadException;

    void update(Produit p) throws UpdateException;

    boolean delete(int id) throws DeleteException;

    boolean delete(Produit p) throws DeleteException;

    Produit hydrateProduit() throws HydrateException;


}
