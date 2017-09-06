package haw.common.action.annotation;

import javax.enterprise.util.AnnotationLiteral;

/**
 * The Class AffectedAttributeLiteral.
 */
public class AffectedAttributeLiteral extends
        AnnotationLiteral<AffectedAttribute>
        implements AffectedAttribute {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The attribute.
     */
    private final String attribute;

    /**
     * Instantiates a new affected attribute literal.
     *
     * @param attribute the attribute
     */
    public AffectedAttributeLiteral(String attribute) {
        this.attribute = attribute.toLowerCase();
    }

    /*
	 * (non-Javadoc)
	 * @see jak.common.action.AffectedAttribute#value()
     */
    @Override
    public String value() {
        return attribute;
    }

    /**
     * Creates the AffectedAttribute
     *
     * @param attribute the attribute
     * @return the affected attribute
     */
    public static AffectedAttribute create(String attribute) {
        return new AffectedAttributeLiteral(attribute);
    }

}
