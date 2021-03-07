package metier;

import exception.database.MAJImpossible;
import exception.product.ProductException;

import java.util.List;

public interface I_Catalogue {

boolean addProduit(I_Produit produit);
boolean addProduit(String nom, double prix, int qte) throws ProductException;
int addProduits(List<I_Produit> l);
boolean removeProduit(String nom);
boolean acheterStock(String nomProduit, int qteAchetee) throws MAJImpossible;
boolean vendreStock(String nomProduit, int qteVendue) throws MAJImpossible;
String[] getNomProduits();
double getMontantTotalTTC();
String toString();
void clear();

}