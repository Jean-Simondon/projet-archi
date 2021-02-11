package exception.product;

public class QteInvalide extends ProductException
{

    public QteInvalide()
    {
        super("La quantité entrée n'est pas valide ou une erreur est survenue");
    }

}
