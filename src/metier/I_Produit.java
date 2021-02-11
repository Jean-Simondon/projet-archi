package metier;

import exception.database.MAJImpossible;

public interface I_Produit extends PersistableEntity{

boolean ajouter(int qteAchetee) throws MAJImpossible;
boolean enlever(int qteVendue) throws MAJImpossible;
String getNom();
int getQuantite();
double getPrixUnitaireHT();
double getPrixUnitaireTTC();
double getPrixStockTTC();
String toString();

}