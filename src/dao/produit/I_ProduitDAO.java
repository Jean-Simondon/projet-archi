package dao.produit;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.produit.I_Produit;
import java.util.List;

public interface I_ProduitDAO
{

    int create(String nom, double prixUnitaireHT, int qte) throws ProductException;

    int create(I_Produit p) throws ProductException;

    I_Produit readById(int id) throws ReadException, HydrateException;

    I_Produit readByName(String name) throws ReadException, HydrateException, ProductException;

    List<I_Produit> readAll() throws ReadException;

    List<I_Produit> readByCatalogue(int idCatalogue) throws ReadException;

    boolean update(I_Produit p) throws UpdateException;

    boolean delete(I_Produit p) throws DeleteException;

    I_Produit hydrateProduit() throws HydrateException, ProductException;

    void disconnect();

}
