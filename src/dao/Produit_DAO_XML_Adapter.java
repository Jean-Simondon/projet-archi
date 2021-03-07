package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.I_Produit;
import metier.Produit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Produit_DAO_XML_Adapter implements  ProduitDAO_I{

    private final ProduitDAO_XML produitDAO_xml;

    public Produit_DAO_XML_Adapter(){
        this.produitDAO_xml = new ProduitDAO_XML();
    }

    @Override
    public int create(String nom, double prixUnitaireHT, int qte) throws ProductException {
        if(this.produitDAO_xml.creer(new Produit(nom,prixUnitaireHT,qte))){
            return -1; //todo Changer ça pour retourner un id plus adéquat parce que le XML dao renvoie un boolean
        }
        else{
            throw new ProductException("Création du produit XML impossible");
        }
    }

    @Override
    public I_Produit read(int id) throws ReadException, HydrateException {
        return null; //On utilise pas cette methode vu qu'on a pas d'id en XML
    }

    public I_Produit readByName(String nom){
        return this.produitDAO_xml.lire(nom);
    }

    @Override
    public List<I_Produit> readAll() throws ReadException {
       try{
           return this.produitDAO_xml.lireTous();
       }catch (Exception e){
           throw new ReadException();
       }

    }

    @Override
    public void update(I_Produit p) throws UpdateException {
        if(!this.produitDAO_xml.maj(p)){
            throw new UpdateException();
        }
    }

    @Override
    public boolean delete(int id) throws DeleteException {//Pas utilisé non plus parce que pas d'id
        return false;
    }

    @Override
    public boolean delete(I_Produit p) throws DeleteException {
        if(!this.produitDAO_xml.supprimer(p)){
            throw new DeleteException();
        }
        return true;
    }

    @Override
    public Produit hydrateProduit() throws HydrateException { //Pas utilisé parce que pas besoin d'hydrater
        return null;
    }
}
