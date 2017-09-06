package haw.common.resource.base.list;

import haw.common.action.annotation.Action;
import haw.common.resource.base.change.CollectionChangeSet;
import haw.common.security.UserPrincipal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The Class MergableList.
 *
 * @param <E> the element type
 * @author Uwe
 */
public class MergableList<E> implements Collection<E>, Cloneable {

    /**
     * The list.
     */
    private final Collection<E> list;

    /**
     * Instantiates a new mergable list.
     */
    public MergableList() {
        this(new ArrayList<E>());
    }

    /**
     * Instantiates a new mergable list.
     *
     * @param list the list
     */
    public MergableList(Collection<E> list) {
        this.list = (list != null ? list : new ArrayList<E>());
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#size()
     */
    @Override
    public int size() {
        return list.size();
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return list.removeAll(c);
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Merges the newEntityList with the current version of the entityList by
     * taking care of role restrictions of the given {@link UserPrincipal}.
     *
     * @param newEntityList List with changes to merge.
     * @return the Collection of all changes.
     */
    public CollectionChangeSet<E> merge(Collection<E> newEntityList) {
        CollectionChangeSet<E> collectionChangeSet = new CollectionChangeSet<>(this);
        List<E> tmpNewEntityList;
        if (newEntityList != null) {
            tmpNewEntityList = new ArrayList<>(newEntityList);
        } else {
            tmpNewEntityList = new ArrayList<>();
        }
        // Check if old entities in new list (keep, remove or merge old
        // entities)
        for (E oldEntity : collectionChangeSet.getCollection()) {
            if (tmpNewEntityList.contains(oldEntity)) {
                tmpNewEntityList.remove(oldEntity);
            } else {
                //removed
                collectionChangeSet.addChange(Action.DELETE, oldEntity);
                this.remove(oldEntity);
            }
        }
        // Add new entities to the old list
        for (E newEntity : tmpNewEntityList) {
            //added
            collectionChangeSet.addChange(Action.CREATE, newEntity);
            this.add(newEntity);
        }
        return collectionChangeSet;
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Collection<?>)) {
            return false;
        }
        Collection<?> other = (Collection<?>) obj;
        boolean result = list.size() == other.size();
        result = result && list.containsAll(other);
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (E e : list) {
            result = prime * result + e.hashCode();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (E e : list) {
            buffer.append(e.toString());
            buffer.append(", ");
        }
        String txt = buffer.toString();
        return String.format("[%s]", txt.substring(0, txt.length() - 2));
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
     */
    @Override
    public MergableList<E> clone() {
        MergableList<E> clonedList = new MergableList<>();
        clonedList.addAll(this);
        return clonedList;
    }

}
