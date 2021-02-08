package metier;

import java.util.ArrayList;
import java.util.List;

public class Catalogue implements I_Catalogue {

    private ArrayList<I_Produit> lesProduits;

    private Catalogue instance;

    public Catalogue(){
        lesProduits = new ArrayList<>();
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        if( produit != null ) {
            return lesProduits.add(produit);
        } else {
            return false;
        }
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        return lesProduits.add(new Produit(nom,prix,qte));
    }

    /**
     * Add a collection of products to the catalogue
     * @param l the collection
     * @return the number of items added
     */
    @Override
    public int addProduits(List<I_Produit> l) {
        lesProduits.addAll(l);
        return l.size();
    }

    @Override
    public boolean removeProduit(String nom) {
        int index = this.findIndexOfProduct(nom);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.remove(index) != null;
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.get(index).ajouter(qteAchetee);
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.get(index).ajouter(qteVendue);
    }

    @Override
    public String[] getNomProduits() {
        String[] tab = new String[this.lesProduits.size()];
        for(int i = 0 ; i < this.lesProduits.size() ; i++){
            tab[i] = this.lesProduits.get(i).getNom();
        }
        return tab;
    }

    @Override
    public double getMontantTotalTTC() {
        double montantTotal = 0;
        for(I_Produit produit : this.lesProduits){
            montantTotal += produit.getPrixStockTTC();
        }
        return montantTotal;
    }

    public int findIndexOfProduct(String nomProduit){
        int index = -1;
        for(I_Produit produit : this.lesProduits){
            if(produit.getNom().equals(nomProduit)){
                index = this.lesProduits.indexOf(produit);
            }
        }
        return index;
    }

    //Todo  : je sais pas a quoi elle sert cette méthode..
    @Override
    public void clear() {

    }

    @Override
    public String toString() {
        String message = "";
        for (I_Produit produit : lesProduits) {
            message += produit.toString();
            message += "\n";
        }
        message += "Montant total TTC du stock " + getMontantTotalTTC() + "€";
        return message;
    }






}

