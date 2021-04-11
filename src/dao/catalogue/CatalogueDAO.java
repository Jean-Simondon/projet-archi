package dao.catalogue;

import dao.DAOManagerBD;
import metier.catalogue.I_Catalogue;
import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.catalogue.I_Catalogue;
import metier.catalogue.Catalogue;
import metier.produit.I_Produit;
import metier.produit.Produit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogueDAO extends DAOManagerBD implements I_CatalogueDAO {

    private static final String TAG = "[CatalogueDAO]";

    public CatalogueDAO() {
        ConnexionBD();
    }

    @Override
    public int create(String nom) throws ProductException {
        return 0;
    }

    @Override
    public int create(I_Catalogue c) throws ProductException {
        return 0;
    }

    @Override
    public I_Catalogue readById(int id) throws ReadException, HydrateException {
        return null;
    }

    @Override
    public I_Catalogue readByName(String name) throws ReadException, HydrateException, ProductException {
        return null;
    }

    @Override
    public List<I_Catalogue> readAll() {

        Logger.getLogger(TAG).log(Level.INFO,"Read all");
        try {
            pst = cn.prepareStatement("SELECT id, nom, count(*) FROM Catalogues GROUP BY id, nom", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
        } catch (Exception e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : ");
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<I_Catalogue> listeCatalogue = new ArrayList<>();

        try {
            while( rs.next() ) {
                listeCatalogue.add(hydrateCatalogue());
            }
            return listeCatalogue;
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant le read All");
            return new ArrayList<>();
        } catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    @Override
    public boolean update(I_Catalogue c) throws UpdateException {
        return false;
    }

    @Override
    public boolean delete(I_Catalogue c) throws DeleteException {
        return false;
    }

    @Override
    public I_Catalogue hydrateCatalogue() throws HydrateException
    {
        Logger.getLogger(TAG).log(Level.INFO,"Hydrate Catalogue");
        int id = -1;
        int nbProduits = 0;
        String nom;
        try{
            id = rs.getInt("id");
            nom = rs.getString("nom");
            nbProduits = rs.getInt("nbProduits");
        } catch (SQLException e) {
            throw new HydrateException();
        }
        return new Catalogue(id, nom, nbProduits);
    }

    @Override
    public void disconnect()
    {
        deconnexion();
    }

}

