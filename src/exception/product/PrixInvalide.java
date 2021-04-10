package exception.product;

public class PrixInvalide extends ProductException
{
    public PrixInvalide()
    {
        super("la quantité entrée ne peut pas être inférieure à 0");
    }
}
