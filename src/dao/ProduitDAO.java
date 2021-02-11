package dao;

import metier.Produit;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProduitDAO extends DAOManager {

    public static boolean create(String nom, double prixUnitaireHT, int qte)
    {
        try {
            CallableStatement cst = cn.prepareCall("{CALL insertProduit(?, ?, ?)}");
            cst.setString(1, nom);
            cst.setDouble(2, prixUnitaireHT);
            cst.setInt(3, qte);
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean create(Produit p)
    {
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

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

        ArrayList<Produit> produits = new ArrayList<>();

        try {
            int nbRow = rs.getRow();
            for(int i = 0; i < nbRow; i++) {
                produits.add(hydrateProduit());
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }

    public static boolean update(Produit p)
    {




        return true;
    }

    public static boolean delete()
    {
        return true;
    }

    public static Produit hydrateProduit() {
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
