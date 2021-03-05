package dao;

public class DAOFactory {

    public static ProduitDAO_I getInstance(){
        return new ProduitDAO();
    }
}
