package exception.database;

public class ReadException extends Exception{

    public ReadException()
    {
        super("Erreur pendant la lecture de la base de donnée");
    }
}
