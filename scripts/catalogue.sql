DROP TABLE Catalogues;

CREATE TABLE Catalogues(
                         id NUMBER,
                         nom VARCHAR(50) UNIQUE,
                         CONSTRAINT pk_catalogue PRIMARY KEY (id),
                         CONSTRAINT nn_nom_catalogue CHECK (nom IS NOT NULL)
);

-- Séquence pour incrémenter l'ID des nouveaux catalogue
CREATE SEQUENCE seqCatalogue START WITH 1 INCREMENT BY 1;

-- Commande pour ajouter un nouveau catalogue
--INSERT INTO Catalogues (id, nom) VALUES (1, 'La redoute');

-- Function pour obtenir la prochaine valeur de la séquence afin de la renseigner plus tard dans un INSERT depuis le DAO
--CREATE OR REPLACE FUNCTION getNextValSequenceCatalogue RETURN NUMBER IS
--    v_id NUMBER;
--BEGIN
    --    v_id := seqCatalogue.NEXTVAL;
    --    RETURN v_id;
--END;

CREATE OR REPLACE PROCEDURE insertNewCatalogue(p_nom IN Catalogues.nom%TYPE) IS
BEGIN
    INSERT INTO Catalogues(id, nom) VALUES (seqCatalogue.NEXTVAL, p_nom);
END;