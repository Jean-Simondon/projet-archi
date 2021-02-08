package exception;
//Todo : Ca pourrait être utile quand on aura a coder les controller et gerer les exceptions, peut etre pas aussi
public class QteInvalide extends ProductException{

    public QteInvalide(){
        super("La quantité entrée n'est pas valide ou une erreur est survenue");
    }
}
