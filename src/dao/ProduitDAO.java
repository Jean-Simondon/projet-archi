package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import metier.I_Produit;
import metier.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends DAOManager implements ProduitDAO_I {


    /**
     *
     * @param nom Le nom du produit
     * @param prixUnitaireHT Le prix du produit
     * @param qte La quantité de produit
     * @return vrai si le produit a bien été crée
     */
    public int create(String nom, double prixUnitaireHT, int qte) {
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
     * @param p le produit
     * @return vrai si le produit a bien été crée
     */
    public int create(I_Produit p)
    {
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    /**
     * renvoie un produit en particulier
     * @param id l'id produit
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
            return hydrateProduit();
        }catch (SQLException e ){
            throw new ReadException();
        }
    }

    /**
     * Renvoie tous les produits de la base de données
     * @return ArrayListe<Produit>
     */
    public List<I_Produit> readAll() throws ReadException {
        try {
            pst = cn.prepareStatement("SELECT * FROM produit", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();

            List<I_Produit> listeProduits = new ArrayList<>();

            int nbRow = rs.getRow();
            for (int i = 0; i < nbRow; i++) {
                listeProduits.add(hydrateProduit());
                rs.next();
            }
            return listeProduits;
        }catch (SQLException | HydrateException e){
            throw new ReadException();
        }


    }

    /**
     * Mets à jour un produit grâce à un Objet produit
     * @param p le produit
     * @throws UpdateException Si quelque chose s'est mal passé pendant la mise à jour
     */
    public void update(I_Produit p) throws UpdateException {
        try {
            pst = DAOManager.cn.prepareStatement("UPDATE produit SET nom = ?, prixUnitaireHT = ?, qte = ? WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, p.getNom());
            pst.setDouble(2, p.getPrixUnitaireHT());
            pst.setInt(3, p.getQuantite());
            pst.setInt(4, p.getId());
        } catch (SQLException e) {
            throw new UpdateException();
        }

    }

    public boolean delete(int id) throws DeleteException {
        try {
            pst = DAOManager.cn.prepareStatement("DELETE FROM produit WHERE id = ?");
            pst.setInt(1, id);
        } catch (SQLException e) {
            throw new DeleteException();
        }
        return true;
    }

    public boolean delete(I_Produit p) throws DeleteException {
        return delete(p.getId());
    }

    /**
     * Utilise le ResultSet pour hydrater les données renvoyées par la base de donnée et renvoyer un objet Produit
     * @return le Produit
     * @throws HydrateException Si l'hydratation est impossible
     */
    public I_Produit hydrateProduit() throws HydrateException {
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
