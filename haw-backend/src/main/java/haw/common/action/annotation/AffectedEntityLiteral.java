package haw.common.action.annotation;

import javax.enterprise.util.AnnotationLiteral;

/**
 * The Class AffectedEntityLiteral.
 */
public class AffectedEntityLiteral extends AnnotationLiteral<AffectedEntity>
        implements AffectedEntity {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The entity.
     */
    private final String entity;

    /**
     * Instantiates a new affected entity literal.
     *
     * @param entity the entity
     */
    public AffectedEntityLiteral(String entity) {
        this.entity = entity.toLowerCase();
    }

    /*
     * (non-Javadoc)
     * @see jak.common.action.AffectedEntity#value()
     */
    @Override
    public String value() {
        return entity;
    }

    /**
     * Creates the.
     *
     * @param entity the entity
     * @return the affected entity
     */
    public static AffectedEntity create(String entity) {
        return new AffectedEntityLiteral(entity);
    }

}
