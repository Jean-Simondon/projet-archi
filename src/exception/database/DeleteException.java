package exception.database;

public class DeleteException extends Exception
{
    public DeleteException()
    {
        super("Erreur pendant la suppression dans la base de donnée");
    }
}
