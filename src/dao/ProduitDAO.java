package dao;

import metier.I_Produit;
import metier.Produit;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class ProduitDAO extends DAOManager {

    private static final String TAG = "ProduitDAO";

    /**
     *
     * @param nom
     * @param prixUnitaireHT
     * @param qte
     * @return vrai si le produit a bien été crée
     */
    public static void create(String nom, double prixUnitaireHT, int qte)
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

    }

    /**
     *
     * @param p
     * @return vrai si le produit a bien été crée
     */
    public static void create(Produit p)
    {
        System.out.println(TAG + " : Create");
        create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    /**
     * renvoie un produit en particulier
     * @param name
     * @return Produit
     */
    public static I_Produit read(String name)
    {
        System.out.println(TAG + " : Read");
        try {
            pst = cn.prepareStatement("SELECT * FROM produits WHERE nom = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, name);
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
            for(int i = 0; i < nbRow; i++) {
                listeProduits.add(hydrateProduit());
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listeProduits;
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
            e.printStackTrace();
        }
        return new Produit(nom, prixUnitaireHT, qte);
    }


}
