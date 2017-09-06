package haw.common.action.annotation;

import java.lang.annotation.Annotation;

/**
 * The Enum Action.
 * This enum collects all possible Actions for entities.
 */
public enum Action {

	/** The create. */
	CREATE(new ActionCreateLiteral()),

	/** The update. */
	UPDATE(new ActionUpdateLiteral()),

	/** The delete. */
	DELETE(new ActionDeleteLiteral()),
	
	/** The external change. */
	EXTERNAL_CHANGE(new ActionExternalChangeLiteral());

	/** The annotation. */
	private final Annotation annotation;

	/**
	 * Instantiates a new action.
	 * 
	 * @param annotation
	 *            the annotation
	 */
	private Action(Annotation annotation) {
		this.annotation = annotation;
	}

	/**
	 * Gets the annotation.
	 * 
	 * @return the annotation
	 */
	public Annotation getAnnotation() {
		return annotation;
	}
}
