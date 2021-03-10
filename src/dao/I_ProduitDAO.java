package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.I_Produit;
import metier.Produit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface I_ProduitDAO {

    int create(String nom, double prixUnitaireHT, int qte) throws ProductException;

    int create(I_Produit p) throws ProductException;

    I_Produit readById(int id) throws ReadException, HydrateException;

    I_Produit readByName(String name) throws ReadException, HydrateException, ProductException;

    List<I_Produit> readAll() throws ReadException;

    boolean update(I_Produit p) throws UpdateException, SQLException;

    boolean delete(I_Produit p) throws DeleteException, SQLException;

    I_Produit hydrateProduit() throws HydrateException, ProductException;

}
