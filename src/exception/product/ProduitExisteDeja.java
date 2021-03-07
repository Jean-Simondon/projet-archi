package exception.product;

public class ProduitExisteDeja extends ProductException {
    public ProduitExisteDeja()
    {
        super("Le produit existe déjà sous ce nom");
    }
}
