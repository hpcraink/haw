package haw.module.security.model;

import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawValidationException;
import haw.common.helper.DateFormatter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class Token.
 */
public class Token implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The decrypted token. */
	private final String decryptedToken;

	/** The email. */
	private final String email;

	/** The timestamp. */
	private final Date timestamp;

	/**
	 * Instantiates a new token.
	 * 
	 * @param email
	 *            the email
	 * @param timestamp
	 *            the timestamp
	 */
	public Token(String email, Date timestamp) {
		this.email = email;
		this.timestamp = new Date(timestamp.getTime());
		this.decryptedToken = toToken(email, timestamp);
	}

	/**
	 * Instantiates a new token.
	 * 
	 * @param decryptedToken
	 *            the decrypted token
	 * @throws HawAuthenticationException - Thrown if the Email or password
	 * 	is invalid
	 */
	public Token(String decryptedToken) throws HawAuthenticationException {
		this.decryptedToken = decryptedToken;
		this.email = extractEmail(decryptedToken);
		this.timestamp = extractTimestamp(decryptedToken);
	}

	/**
	 * To token.
	 * 
	 * @param email
	 *            the email
	 * @param timestamp
	 *            the timestamp
	 * @return the string
	 */
	public static String toToken(String email, Date timestamp) {
		String timestampString = DateFormatter.TIMESTAMP_SHORT.format(timestamp);
		return String.format("%s:%s", email, timestampString);
	}

	/**
	 * Extract email.
	 * 
	 * @param decryptedToken
	 *            the decrypted token
	 * @return the string
	 * @throws HawAuthenticationException - Thrown if the Email or password
	 * 	is invalid
	 */
	public static String extractEmail(String decryptedToken) throws HawAuthenticationException {
		String[] in = decryptedToken.split(":");
		if (in.length != 2){
			throw new HawAuthenticationException("Email or Password Wrong");
		}
		return in[0];
	}

	/**
	 * Extract timestamp.
	 * 
	 * @param decryptedToken
	 *            the decrypted token
	 * @return the date
	 * @throws HawAuthenticationException - Thrown if the Email or password
	 * 	is invalid
	 */
	public static Date extractTimestamp(String decryptedToken) throws HawAuthenticationException {
		String[] in = decryptedToken.split(":");
		if (in.length != 2){
			throw new HawAuthenticationException("Email or Password Wrong");
		}
		String inDate = in[1];
		Date date;
		try {
			date = DateFormatter.TIMESTAMP_SHORT.parse(inDate);
		} catch (HawValidationException e) {
			throw new HawAuthenticationException("Invalid Token");
		}
		return date;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the timestamp as date.
	 * 
	 * @return the timestamp as date
	 */
	public Date getTimestampAsDate() {
		return new Date(timestamp.getTime());
	}

	/**
	 * Gets the timestamp as calendar.
	 * 
	 * @return the timestamp as calendar
	 */
	public Calendar getTimestampAsCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		return calendar;
	}

	/**
	 * Gets the decrypted token.
	 * 
	 * @return the decrypted token
	 */
	public String getDecryptedToken() {
		return decryptedToken;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((decryptedToken == null) ? 0 : decryptedToken.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result
				+ ((email == null) ? 0 : email.hashCode());
		return result;
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
		if (!(obj instanceof Token)) {
			return false;
		}
		Token other = (Token) obj;
		if (decryptedToken == null) {
			if (other.decryptedToken != null) {
				return false;
			}
		} else if (!decryptedToken.equals(other.decryptedToken)) {
			return false;
		}
		if (timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!timestamp.equals(other.timestamp)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		return true;
	}

}
