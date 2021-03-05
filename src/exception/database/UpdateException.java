package exception.database;

public class UpdateException extends Exception {

    public UpdateException()
    {
        super("Erreur pendant la mise à jour d'un élément de la base de donnée");
    }
}
