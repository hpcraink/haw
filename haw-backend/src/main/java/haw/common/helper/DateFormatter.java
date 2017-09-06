package haw.common.helper;

import haw.common.exception.HawValidationException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Enum DateFormatter.
 * Summarizes all used date formats.
 */
public enum DateFormatter {

	/** Timestamp (ddMMyyyyHHmmss) */
	TIMESTAMP_SHORT("ddMMyyyyHHmmss"),
	
	/** Timestamp (dd.MM.yyyy HH:mm:ss) */
	TIMESTAMP("dd.MM.yyyy HH:mm:ss"),

	/** Date (dd.MM.yyyy) */
	DATE("dd.MM.yyyy"),

	/** Time (HH:mm) */
	TIME("HH:mm");

	/** The pattern. */
	private final String pattern;

	/**
	 * Instantiates a new date formatter.
	 * 
	 * @param pattern
	 *            the pattern
	 */
	private DateFormatter(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Gets the pattern.
	 * 
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Format the given date.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public String format(Date date) {
		if (date == null) {
			return null;
		}
		return getDateFormatInstance(pattern).format(date);
	}

	/**
	 * Parses the given string.
	 * 
	 * @param date
	 *            the String to parse
	 * @return the date parsed from given String.
	 * @throws JakValidationException
	 *             - Thrown if passed date is not valid.
	 */
	public Date parse(String date) throws HawValidationException {
		if (date == null || date.trim().isEmpty()) {
			return null;
		}
		try {
			return getDateFormatInstance(pattern).parse(date);
		} catch (ParseException e) {
			String message = String.format(
					"Invalid format. Got '%s' but expects format '%s': %s",
					date, pattern, e.getMessage());
			throw new HawValidationException(message, e);
		}
	}

	/**
	 * Gets the date format instance.
	 * 
	 * @param pattern1
	 *            the pattern
	 * @return the date format instance
	 */
	private DateFormat getDateFormatInstance(String pattern1) {
		return new SimpleDateFormat(pattern1);
	}

}
