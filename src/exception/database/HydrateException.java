package exception.database;

public class HydrateException extends Exception{
    public HydrateException(){
        super("Erreur pendant l'hydratation des éléments");
    }
}
