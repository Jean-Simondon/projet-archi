DROP TABLE Produits;

CREATE TABLE Produits(
            id NUMBER,
            nom VARCHAR(50),
            prixUnitaireHT DOUBLE PRECISION,
            qte NUMBER,
            CONSTRAINT pk_produit PRIMARY KEY (id),
            CONSTRAINT nn_nom CHECK (nom IS NOT NULL),
            CONSTRAINT nn_prixUnitaireHT CHECK (prixUnitaireHT IS NOT NULL),
            CONSTRAINT nn_qte CHECK (qte IS NOT NULL)
);

-- Séquence pour incrémenter l'ID des nouveaux produits
CREATE SEQUENCE seqProduit START WITH 1 INCREMENT BY 1;

-- Commande pour ajouter un nouveau produit
INSERT INTO Produits (id, nom, prixUnitaireHT, qte) VALUES (1, 'Mars', 10, 1);

-- Function pour obtenir la prochaine valeur de la séquence afin de la renseigner plus tard dans un INSERT depuis le DAO
CREATE OR REPLACE FUNCTION getNextValSequenceProduit RETURN NUMBER IS
    v_id NUMBER;
BEGIN
    v_id := seqProduit.NEXTVAL;
RETURN v_id;
END;
