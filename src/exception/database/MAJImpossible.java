package exception.database;

import exception.product.ProductException;

public class MAJImpossible extends DatabaseException
{
    public MAJImpossible()
    {
        super("La mise a jour de ce produit à retourné une erreur");
    }
}
