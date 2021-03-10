package dao;

import exception.product.ProduitExisteDeja;
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

public class ProduitDAO extends DAOManager implements I_ProduitDAO {

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
        System.out.println(TAG + " : Create With Attribute");

        int id = -1;
        try {
            // Récupération du prochain ID de la table Produit
            CallableStatement cstId = cn.prepareCall("{ ? = call getNextValSequenceProduit() }"); // appel de fonction pour obtenir le prochain ID de la table Produits
            cstId.registerOutParameter(1, Types.NUMERIC);
            cstId.execute();
            id = cstId.getInt(1);

            // Insertion du produit dans la BDD
            CallableStatement cst = cn.prepareCall("INSERT INTO Produits (id, nom, prixUnitaireHT, qte) VALUES (?, ?, ?, ?)");
            cst.setInt(1, id);
            cst.setString(2,nom);
            cst.setDouble(3,prixUnitaireHT);
            cst.setInt(4,qte);
            cst.execute();

            if( false ) {
                throw new ProduitExisteDeja();
            }

        } catch (SQLException | ProduitExisteDeja e) {
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
        System.out.println(TAG + " : Create With Instance");
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
            pst = cn.prepareStatement("SELECT * FROM produit WHERE ID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, id);
            rs = pst.executeQuery();
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
            pst = cn.prepareStatement("SELECT * FROM produit WHERE NOM = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, name);
            rs = pst.executeQuery();
            return hydrateProduit();
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
            pst = cn.prepareStatement("SELECT * FROM produits", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
        } catch (Exception e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getLocalizedMessage());;
            return new ArrayList<>();
        }

        List<I_Produit> listeProduits = new ArrayList<>();

        try {
            int nbRow = rs.getRow();
            for (int i = 0; i < nbRow; i++) {
                listeProduits.add(hydrateProduit());
                rs.next();
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
            CallableStatement cst = cn.prepareCall("UPDATE Produit SET nom = ?, prixUnitaireHT = ?, qte = ? WHERE id = ?");
            cst.setString(1, name);
            cst.setDouble(2, prixUnitaireHT);
            cst.setInt(3, qte);
            cst.setInt(4, id);
            cst.execute();
        }catch(SQLException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant l'update");
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


    public I_Produit readWhereName(String nom){
        System.out.println(TAG + " : ReadWhereName");
        try {
            pst = cn.prepareStatement("SELECT * FROM produits WHERE nom = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, nom);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getMessage());
            return null;
        }

        try{
            return hydrateProduit();
        }catch(HydrateException e){
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur pendant l'hydratation d'un produit");
            return null;
        }

    }

    public boolean delete(int id) {
        System.out.println(TAG + " : Delete");
        try {
        CallableStatement cst = cn.prepareCall("DELETE FROM Produit WHERE id = ?");
        cst.setInt(1, id);
        cst.execute();
        } catch (SQLException e) {
            Logger.getLogger(TAG).log(Level.SEVERE,"Erreur SQL : " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(I_Produit p) {
       Logger.getLogger(TAG).log(Level.INFO,"Delete");
        return delete(p.getId());
    }


    public I_Produit hydrateProduit() throws HydrateException {
        Logger.getLogger(TAG).log(Level.INFO,"Hydrate Produit");
        int id = -1;
        String nom = null;
        double prixUnitaireHT = -1;
        int qte = -1;
        try{
            id = rs.getInt("id");
            nom = rs.getString("nom");
            prixUnitaireHT = rs.getDouble("prixUnitaireHT");
            if( rs.wasNull() ) {
                prixUnitaireHT = -1;
            }
            qte = rs.getInt("quantite");
            if( rs.wasNull() ) {
                qte = -1;
            }
        } catch (SQLException e) {
           throw new HydrateException();
        }
        return new Produit(nom, prixUnitaireHT, qte);
    }


}
