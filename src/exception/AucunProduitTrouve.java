package exception;
//Todo : Ca pourrait être utile quand on aura a coder les controller et gerer les exceptions, peut etre pas aussi
public class AucunProduitTrouve extends ProductException{
    public AucunProduitTrouve(){
        super("Le produit recherché n'est pas stocké");
    }
}
