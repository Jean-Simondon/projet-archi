package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.I_Produit;
import metier.Produit;

import java.util.ArrayList;
import java.util.List;

public interface ProduitDAO_I {

    int create(String nom , double prixUnitaireHT, int qte) throws ProductException;

    I_Produit read(int id) throws ReadException, HydrateException;


    I_Produit readByName(String name) throws ReadException, HydrateException;

    List<I_Produit> readAll() throws ReadException;

    void update(I_Produit p) throws UpdateException;

    boolean delete(int id) throws DeleteException;

    boolean delete(I_Produit p) throws DeleteException;

    I_Produit hydrateProduit() throws HydrateException;


}
