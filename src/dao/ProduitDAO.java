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

/*
        try {
            pst = cn.prepareStatement("SELECT * FROM personnes order BY agePersonne ASC", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

 */
        return new Produit("null", 0, 0);
    }

    public static ArrayList<Produit> readAll()
    {
        return new ArrayList<Produit>();
    }

    public static boolean update(Produit p)
    {
        return true;
    }

    public static boolean delete()
    {
        return true;
    }

}
