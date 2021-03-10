package metier;

import exception.database.MAJImpossible;

public interface I_Produit extends PersistableEntity{

boolean ajouter(int qteAchetee);
boolean enlever(int qteVendue);
String getNom();
int getQuantite();
double getPrixUnitaireHT();
double getPrixUnitaireTTC();
double getPrixStockTTC();
int getId();
String toString();

}