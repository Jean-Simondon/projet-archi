package dao.catalogue;

import dao.DAOManagerBD;
import exception.database.catalogue.CatalogueException;
import metier.catalogue.I_Catalogue;
import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import metier.catalogue.Catalogue;

import java.sql.CallableStatement;
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
    public int create(String nom) throws Exception {
        Logger.getLogger(TAG).log(Level.INFO,"Create with attributes");
            CallableStatement cst = cn.prepareCall("{ call insertNewCatalogue(?) }"); // appel de fonction pour obtenir le prochain ID de la table Produits
            cst.setString(1, nom);
            cst.execute();
            I_Catalogue cat = readByName(nom);
            return cat.getId();
    }

    @Override
    public int create(I_Catalogue c) throws Exception {
        return create(c.getName());
    }

    @Override
    public I_Catalogue readById(int id) throws ReadException, HydrateException {
        try{
            pst = cn.prepareStatement("SELECT id, nom, count(p.id) as nbProduits FROM Catalogues c LEFT OUTER JOIN Produits p on c.id = p.idCatalogue WHERE c.id = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            rs.next();
            return hydrateCatalogue();
        } catch (SQLException e ) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Read Error");
            return null;
        } catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    @Override
    public I_Catalogue readByName(String name) throws ReadException, HydrateException, CatalogueException {
        try{
            pst = cn.prepareStatement("SELECT c.id, c.nom, count(p.id) as nbProduits FROM Catalogues c LEFT OUTER JOIN Produits p on c.id = p.idCatalogue WHERE c.nom = ? GROUP BY c.id,c.nom ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, name);
            rs = pst.executeQuery();
            rs.next();
            return hydrateCatalogue();
        } catch (SQLException e ) {
            e.printStackTrace();
            Logger.getLogger(TAG).log(Level.SEVERE,"Read Error");
            return null;
        } catch(HydrateException e){
            e.printStackTrace();
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    @Override
    public List<I_Catalogue> readAll(){

        Logger.getLogger(TAG).log(Level.INFO,"Read all");
        try {
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT c.id, c.nom, count(p.id) as nbProduits FROM Catalogues c LEFT OUTER JOIN Produits p on c.id = p.idCatalogue GROUP BY c.id, c.nom ORDER BY (c.nom) DESC");
        } catch (Exception e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : ");
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<I_Catalogue> listeCatalogue = new ArrayList<>();

        try {
            while ( rs.next() ) {
                listeCatalogue.add(hydrateCatalogue());
            }
/*
            rs.next();
            do {
                listeCatalogue.add(hydrateCatalogue());
                System.out.println("TAILLE : " + listeCatalogue.size());
                rs.next();
            } while (!rs.isAfterLast());

 */

            return listeCatalogue;
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant le read All");
            return new ArrayList<>();
        } catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    public boolean update(int id, String name)
    {
        try {
            CallableStatement cst = cn.prepareCall("UPDATE Catalogues SET nom = ? WHERE id = ?");
            cst.setString(1, name);
            cst.setInt(2, id);
            cst.execute();
        }catch(SQLException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant l'update");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean update(I_Catalogue c) throws UpdateException {
        return update(c.getId(),c.getName());
    }

    public boolean delete(int id) throws DeleteException
    {
        Logger.getLogger(TAG).log(Level.INFO,"Delete");
        try {
            CallableStatement cst = cn.prepareCall("DELETE FROM Catalogues WHERE id = ?");
            cst.setInt(1, id);
            cst.execute();
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getMessage());
            throw new DeleteException();
        }
        return true;
    }

    public boolean delete(String name) throws DeleteException
    {
        Logger.getLogger(TAG).log(Level.INFO,"Delete");
        try {
            CallableStatement cst = cn.prepareCall("DELETE FROM Catalogues WHERE nom = ?");
            cst.setString(1, name);
            cst.execute();
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getMessage());
            throw new DeleteException();
        }
        return true;
    }

    @Override
    public boolean delete(I_Catalogue c) throws DeleteException {
        return delete(c.getId());
    }

    @Override
    public I_Catalogue hydrateCatalogue() throws HydrateException
    {
        Logger.getLogger(TAG).log(Level.INFO,"Hydrate Catalogue");
        int id = -1;
        String nom = "";
        int nbProduits = 0;
        try{
            id = rs.getInt("id");
            nom = rs.getString("nom");
            nbProduits = rs.getInt("nbProduits");
            return new Catalogue(id, nom, nbProduits);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HydrateException();
        }
    }

    @Override
    public void disconnect()
    {
        deconnexion();
    }

}

