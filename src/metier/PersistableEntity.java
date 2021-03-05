package metier;

import exception.database.MAJImpossible;
import exception.database.UpdateException;

/**
 * A persistable entity have a method save().
 * Each time it is updated, it will be saved. (With a dao or whatever)
 */
public interface PersistableEntity {

    void save() throws UpdateException;
}
