package haw.common.persistence.handler;

/**
 * Convertor for an entity.
 *
 * @author Uwe Eisele
 *
 * @param <E> The entity
 */
public interface ResultConvertor<E> {

    /**
     * Converts a object to an entity.
     *
     * @param entity The object to convert
     * @return The converted entity
     */
    public E convertEntity(Object entity);
}
