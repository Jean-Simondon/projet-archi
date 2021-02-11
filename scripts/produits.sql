CREATE TABLE Produits(id NUMBER, nom VARCHAR(50), prixUnitaireHT DOUBLE PRECISION, tauxTVA DOUBLE PRECISION,
                      CONSTRAINT pk_produit PRIMARY KEY (id),
                      CONSTRAINT nn_nom CHECK (nom IS NOT NULL),
                      CONSTRAINT prixUnitaireHT CHECK (prixUnitaireHT IS NOT NULL),
                      CONSTRAINT tauxTVA CHECK (tauxTVA IS NOT NULL)
) ;


CREATE SEQUENCE seqProduit
    START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE PROCEDURE insertProduit(p_nom IN Produits.nom%TYPE, p_prixUnitaireHT IN Produits.prixUnitaireHT%TYPE, p_tauxTVA IN Produits.tauxTVA%TYPE) IS
BEGIN
INSERT INTO Produits VALUES (seqProduit.NEXTVAL, p_nom, p_prixUnitaireHT, p_tauxTVA) ;
END;
