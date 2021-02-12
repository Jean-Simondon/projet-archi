package dao;

import metier.Produit;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class ProduitDAO extends DAOManager {

    /**
     *
     * @param nom
     * @param prixUnitaireHT
     * @param qte
     * @return vrai si le produit a bien été crée
     */
    public static int create(String nom, double prixUnitaireHT, int qte) {
        int id = -1;
        try {
            CallableStatement cst = cn.prepareCall("{ ? = call insertProduit( ?, ?, ?)}"); // appel de fonction
            cst.setString(2,nom);
            cst.setDouble(3,prixUnitaireHT);
            cst.setInt(3,qte);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.execute();
            id = cst.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     *
     * @param p
     * @return vrai si le produit a bien été crée
     */
    public static int create(Produit p)
    {
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    /**
     * renvoie un produit en particulier
     * @param id
     * @return Produit
     */
    public static Produit read(int id)
    {
        try {
            pst = cn.prepareStatement("SELECT * FROM produit WHERE ID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, id);
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
    public static ArrayList<Produit> readAll()
    {
        try {
            pst = cn.prepareStatement("SELECT * FROM produit", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Produit> listeProduits = new ArrayList<>();

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


    public static boolean update(Produit p)
    {
        try {
            pst = cn.prepareStatement("UPDATE produit SET nom = ?, prixUnitaireHT = ?, qte = ? WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, p.getNom());
            pst.setDouble(2, p.getPrixUnitaireHT());
            pst.setInt(3, p.getQuantite());
            pst.setInt(4, p.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean delete(int id)
    {
        try {
            pst = cn.prepareStatement("DELETE FROM produit WHERE id = ?");
            pst.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(Produit p)
    {
        return delete(p.getId());
    }

    public static Produit hydrateProduit() {
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
            e.printStackTrace();
        }
        return new Produit(nom, prixUnitaireHT, qte);
    }

}
