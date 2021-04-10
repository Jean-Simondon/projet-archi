DROP TABLE Produits;

CREATE TABLE Produits(
                         id NUMBER,
                         nom VARCHAR(50),
                         prixUnitaireHT DOUBLE PRECISION,
                         qte NUMBER,
                         idCatalogue NUMBER,
                         CONSTRAINT pk_produit PRIMARY KEY (id),
                         CONSTRAINT fk_catalogue_produit FOREIGN KEY (idCatalogue) REFERENCES Catalogues (id),
                         CONSTRAINT nn_catalogue_id CHECK (idCatalogue IS NOT NULL),
                         CONSTRAINT nn_nom_produit CHECK (nom IS NOT NULL),
                         CONSTRAINT nn_prixUnitaireHT CHECK (prixUnitaireHT IS NOT NULL),
                         CONSTRAINT nn_qte CHECK (qte IS NOT NULL)
);

-- Séquence pour incrémenter l'ID des nouveaux produits
CREATE SEQUENCE seqProduit START WITH 1 INCREMENT BY 1;

-- Commande pour ajouter un nouveau produit
INSERT INTO Produits (id, nom, prixUnitaireHT, qte) VALUES (1, 'Mars', 10, 1);



-- Insert de produit si le catalogue existe et qu'il n'y a pas de produit du même nom dans ce catalogue
CREATE OR REPLACE PROCEDURE insertNewProduit(p_nom IN Produits.nom%TYPE, p_prixUnitaireHT IN Produits.prixUnitaireHT%TYPE, p_qte IN Produits.qte%TYPE, p_idCatalogue IN Produits.idCatalogue%TYPE ) IS
v_catalogue NUMBER;
v_produit NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_catalogue FROM Catalogues WHERE id = p_idCatalogue;
    IF (v_catalogue < 1) THEN
        RAISE_APPLICATION_ERROR (-2001, 'Insertion impossible : '  || v_catalogue || 'Le catalogue n existe pas');
    ELSE
        SELECT COUNT(*) INTO v_produit FROM Produits WHERE nom = p_nom AND idCatalogue = p_idCatalogue;

            IF (v_produit > 0) THEN
                    RAISE_APPLICATION_ERROR (-2001, 'Insertion impossible : '  || v_produit || 'Un produit avec un nom similaire existe deja dans ce catalogue');
        ELSE
                INSERT INTO Produits(id, nom, prixUnitaireHT, qte, idCatalogue) VALUES (seqProduit.NEXTVAL , p_nom, p_prixUnitaireHT, p_qte, p_idCatalogue);
        END IF;

    END IF;

END;
/
show errors;






-- Function pour obtenir la prochaine valeur de la séquence afin de la renseigner plus tard dans un INSERT depuis le DAO
--CREATE OR REPLACE FUNCTION getNextValSequenceProduit RETURN NUMBER IS
--    v_id NUMBER;
--BEGIN
--    v_id := seqProduit.NEXTVAL;
--RETURN v_id;
--END;



