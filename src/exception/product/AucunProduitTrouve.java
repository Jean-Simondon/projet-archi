package exception.product;


public class AucunProduitTrouve extends ProductException
{
    public AucunProduitTrouve()
    {
        super("Le produit recherché n'est pas stocké");
    }

}
