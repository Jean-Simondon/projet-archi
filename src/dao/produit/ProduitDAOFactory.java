package dao.produit;

import dao.produit.bd.ProduitDAO_BD;

/**
 * renvoi une instance du DAO souhaité
 */
public class ProduitDAOFactory
{

    private static final String TAG = "ProduitDAOFactory";

    private static I_ProduitDAO dao;

    public static I_ProduitDAO getInstance()
    {
        System.out.println(TAG +  " : getInstance");
        if( dao == null) {
            dao = new ProduitDAO_BD();
        }
        return dao;
    }

}
