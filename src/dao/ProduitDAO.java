package dao;

import metier.I_Produit;
import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import metier.I_Produit;
import metier.Produit;

import java.sql.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends DAOManager implements ProduitDAO_I {


    private static final String TAG = "ProduitDAO";

    /**
     *
     * @param nom Le nom du produit
     * @param prixUnitaireHT Le prix du produit
     * @param qte La quantité de produit
     * @return vrai si le produit a bien été crée
     */
    public static int create(String nom, double prixUnitaireHT, int qte)
    {
        System.out.println(TAG + " : Create");
        int id = -1;
        try {
            // Récupération du prochain ID de la table Produit
            CallableStatement cstId = cn.prepareCall("{ ? = call getNextValSequenceProduit() }"); // appel de fonction pour obtenir le prochain ID de la table Produits
            cstId.registerOutParameter(1, Types.NUMERIC);
            cstId.execute();
            id = cstId.getInt(1);

            CallableStatement cst = cn.prepareCall("INSERT INTO Produits (id, nom, prixUnitaireHT, qte) VALUES (?, ?, ?, ?)");
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
     *
     * @param p le produit
     * @return vrai si le produit a bien été crée
     */
    public static int create(Produit p)
    {
        System.out.println(TAG + " : Create");
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    /**
     * renvoie un produit en particulier
     * @param id l'id produit
     * @param name
     * @return Produit
     */
    public I_Produit read(int id) throws ReadException, HydrateException {
        try{
            pst = cn.prepareStatement("SELECT * FROM produit WHERE ID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            return hydrateProduit();
        }catch (SQLException e ){
            throw new ReadException();
        }

    }

    public I_Produit readByName(String name) throws ReadException,HydrateException {
        try{
            pst = cn.prepareStatement("SELECT * FROM produit WHERE NOM = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
        }catch (SQLException e ){
            throw new ReadException();
        }

        return hydrateProduit();
    }

    /**
     * Renvoie tous les produits de la base de données
     * @return ArrayListe<Produit>
     */
    public static ArrayList<I_Produit> readAll()
    {
        System.out.println(TAG + " : ReadAll");
        try {
            pst = cn.prepareStatement("SELECT * FROM produits", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<I_Produit> listeProduits = new ArrayList<>();

        try {
            int nbRow = rs.getRow();
            for (int i = 0; i < nbRow; i++) {
                listeProduits.add(hydrateProduit());
                rs.next();
            }
            return listeProduits;
        }catch (SQLException | HydrateException e){
            throw new ReadException();
        }

    public static Produit readWhereName(String nom)
    {
        System.out.println(TAG + " : ReadWhereName");
        try {
            pst = cn.prepareStatement("SELECT * FROM produits WHERE nom = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, nom);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hydrateProduit();
    }

        /**
         * Mets à jour un produit grâce à un Objet produit
         * @param p le produit
         * @throws UpdateException Si quelque chose s'est mal passé pendant la mise à jour
         */
    public static boolean update(Produit p)
    {
        System.out.println(TAG + " : Update");
        try {
            pst = cn.prepareStatement("UPDATE Produits SET nom = ?, prixUnitaireHT = ?, qte = ? WHERE nom LIKE ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, p.getNom());
            pst.setDouble(2, p.getPrixUnitaireHT());
            pst.setInt(3, p.getQuantite());
            pst.setString(4, p.getNom());
        } catch (SQLException e) {
            throw new UpdateException();
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean delete(String name)
    {
        System.out.println(TAG + " : Delete");
        try {
            pst = cn.prepareStatement("DELETE FROM Produits WHERE nom = ?");
            pst.setString(1, name);
        } catch (SQLException e) {
            throw new DeleteException();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(Produit p)
    {
        System.out.println(TAG + " : Delete");
        return delete(p.getNom());
    }

    public static Produit hydrateProduit()
    {
        System.out.println(TAG + " : HydrateProduit");
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
