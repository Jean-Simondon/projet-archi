package controller;

import metier.Catalogue;
import metier.CatalogueFactory;
import metier.I_Catalogue;

public class ControllerManager {

    protected static I_Catalogue cat = CatalogueFactory.getInstance();

}
