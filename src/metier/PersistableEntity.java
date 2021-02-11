package metier;

import exception.database.MAJImpossible;

/**
 * A persistable entity have a method save().
 * Each time it is updated, it will be saved. (With a dao or whatever)
 */
public interface PersistableEntity {

    void save() throws MAJImpossible;
}
