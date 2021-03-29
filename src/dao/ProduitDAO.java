package dao;

import exception.database.DeleteException;
import exception.product.PrixInvalide;
import exception.product.ProduitExisteDeja;
import exception.product.QteInvalide;
import metier.I_Produit;
import exception.database.HydrateException;
import metier.Produit;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitDAO implements I_ProduitDAO {

    private static final String TAG = "[ProduitDAO]";

    /**
     * @param nom Le nom du produit
     * @param prixUnitaireHT Le prix du produit
     * @param qte La quantité de produit
     * @return l'ID du produit, donné par la BDD
     */
    @Override
    public int create(String nom, double prixUnitaireHT, int qte)
    {
        Logger.getLogger(TAG).log(Level.INFO,"Create with attributes");
        int id = -1;
        try {
            // Récupération du prochain ID de la table Produit
            CallableStatement cstId = DAOManager.cn.prepareCall("{ ? = call getNextValSequenceProduit() }"); // appel de fonction pour obtenir le prochain ID de la table Produits
            cstId.registerOutParameter(1, Types.NUMERIC);
            cstId.execute();
            id = cstId.getInt(1);

            // Insertion du produit dans la BDD
            CallableStatement cst = DAOManager.cn.prepareCall("INSERT INTO Produits (id, nom, prixUnitaireHT, qte) VALUES (?, ?, ?, ?)");
            cst.setInt(1, id);
            cst.setString(2,nom);
            cst.setDouble(3,prixUnitaireHT);
            cst.setInt(4,qte);
            cst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * @param p le produit
     * @return l'ID du produit, donné par la BDD
     */
    @Override
    public int create(I_Produit p)
    {
        Logger.getLogger(TAG).log(Level.INFO," Create With Instance");
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    /**
     * renvoie un produit en particulier
     * @param id l'id du produit
     * @return renvoie le Produit trouvé en BDD pour cet ID
     */
    @Override
    public I_Produit readById(int id)
    {
        try{
            DAOManager.pst = DAOManager.cn.prepareStatement("SELECT * FROM produits WHERE ID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DAOManager.pst.setInt(1, id);
            DAOManager.rs = DAOManager.pst.executeQuery();
            return hydrateProduit();
        } catch (SQLException e ){
           Logger.getLogger(TAG).log(Level.SEVERE,"Read Error");
           return null;
        } catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }

    }

    /**
     * renvoie un produit en particulier
     * @param name le nom du produit
     * @return renvoie le Produit trouvé en BDD pour ce nom là
     */
    @Override
    public I_Produit readByName(String name) {
        try{
            DAOManager.pst = DAOManager.cn.prepareStatement("SELECT * FROM produits WHERE NOM = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DAOManager.pst.setString(1, name);
            DAOManager.rs = DAOManager.pst.executeQuery();
            DAOManager.rs.next();
            if(!DAOManager.rs.isAfterLast()){
                return hydrateProduit();
            }
            else{return null;}
        } catch ( SQLException e ) {
            Logger.getLogger(TAG).log(Level.SEVERE, "Read Error");
            return null;
        }   catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    /**
     * Renvoie tous les produits de la base de données
     * @return List<I_Produit>
     */
    @Override
    public List<I_Produit> readAll() {
        Logger.getLogger(TAG).log(Level.INFO,"Read all");
        try {
            DAOManager.pst = DAOManager.cn.prepareStatement("SELECT * FROM produits", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DAOManager.rs = DAOManager.pst.executeQuery();
        } catch (Exception e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getLocalizedMessage());
            return new ArrayList<>();
        }

        List<I_Produit> listeProduits = new ArrayList<>();

        try {
            DAOManager.rs.next();
            while(!DAOManager.rs.isAfterLast()){
                listeProduits.add(hydrateProduit());
                DAOManager.rs.next();
            }
            return listeProduits;
        } catch (SQLException e) {
           Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant le read All");
           return new ArrayList<>();
        } catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur d'hydratation");
            return null;
        }
    }

    /**
     * Mets à jour le produit à l'aide de nouvelles données
     * @param id l'id
     * @param name le nom du produit
     * @param prixUnitaireHT le prix unitaire
     * @param qte quantite
     */
    public boolean update(int id, String name, double prixUnitaireHT, int qte) {
        try {
            CallableStatement cst = DAOManager.cn.prepareCall("UPDATE Produits SET nom = ?, prixUnitaireHT = ?, qte = ? WHERE id = ?");
            cst.setString(1, name);
            cst.setDouble(2, prixUnitaireHT);
            cst.setInt(3, qte);
            cst.setInt(4, id);
            cst.execute();
        }catch(SQLException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant l'update");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Mets à jour le produit passé en paramètre
     * @param p le produit
     */
    @Override
    public boolean update(I_Produit p){
        return update(p.getId(), p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    public boolean delete(int id) throws DeleteException {
        Logger.getLogger(TAG).log(Level.INFO,"Delete");
        try {
        CallableStatement cst = DAOManager.cn.prepareCall("DELETE FROM Produits WHERE id = ?");
        cst.setInt(1, id);
        cst.execute();
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getMessage());
            throw new DeleteException();
        }
        return true;
    }

    @Override
    public boolean delete(I_Produit p) throws DeleteException {
        I_Produit produit = this.readByName(p.getNom());
        return delete(produit.getId());
    }


    public I_Produit hydrateProduit() throws HydrateException {
        Logger.getLogger(TAG).log(Level.INFO,"Hydrate Produit");
        int id = -1;
        String nom;
        double prixUnitaireHT = -1;
        int qte = -1;
        try{
            id = DAOManager.rs.getInt("id");
            nom = DAOManager.rs.getString("nom");
            prixUnitaireHT = DAOManager.rs.getDouble("prixUnitaireHT");
            if( DAOManager.rs.wasNull() ) {
                prixUnitaireHT = -1;
            }
            qte = DAOManager.rs.getInt("qte");
            if( DAOManager.rs.wasNull() ) {
                qte = -1;
            }
        } catch (SQLException e) {
           throw new HydrateException();
        }
        return new Produit(id,nom, prixUnitaireHT, qte);

    }

    public void disconnect(){
        DAOManager.deconnexion();
    }


}
