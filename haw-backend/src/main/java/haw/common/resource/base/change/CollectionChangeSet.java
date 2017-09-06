package haw.common.resource.base.change;

import haw.common.action.annotation.Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Class CollectionChangeSet.
 *
 * @param <E> the element type
 */
public class CollectionChangeSet<E> {

    /**
     * The collection.
     */
    private final List<E> collection;

    /**
     * The changes.
     */
    private final List<ChangeSet> changes;

    /**
     * Instantiates a new collection change set.
     *
     * @param oldCollection the old collection
     */
    public CollectionChangeSet(Collection<E> oldCollection) {
        this.collection = new ArrayList<>(oldCollection);
        this.changes = new ArrayList<>();
    }

    /**
     * Gets the collection.
     *
     * @return the collection
     */
    public List<E> getCollection() {
        return collection;
    }

    /**
     * Adds to the given newCollection this collection and returns the
     * newCollection.
     *
     * @param <T> the generic type of element type {@code <E>}
     * @param newCollection the new collection to modify
     * @return the modified collection.
     */
    public <T extends Collection<E>> T getCollectionAs(T newCollection) {
        newCollection.addAll(collection);
        return newCollection;
    }

    /**
     * Gets the changes.
     *
     * @return the changes
     */
    public List<ChangeSet> getChanges() {
        return changes;
    }

    /**
     * Adds the change with the given action and the given entity.
     *
     * @param action the action
     * @param entity the entity
     */
    public void addChange(Action action, E entity) {
        ChangeSet change = new ChangeSet(action, entity);
        addChange(change);
    }

    /**
     * Adds the given {@link ChangeSet} to this List of {@link ChangeSet}s.
     *
     * @param change the change
     */
    public void addChange(ChangeSet change) {
        this.changes.add(change);
    }

    /**
     * Checks for changes.
     *
     * @return true, if successful
     */
    public boolean hasChanges() {
        return !changes.isEmpty();
    }

}
