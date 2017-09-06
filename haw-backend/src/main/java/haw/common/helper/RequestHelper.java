package haw.common.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class RequestHelper.
 */
public abstract class RequestHelper {

	/**
	 * Gets the remote IP address of the client sending request as String.
	 * 
	 * @param req
	 *            the original client's HTTP request
	 * @return the remote client's IP address (including forward info if behind Proxies)
	 */
	public static String getRemoteIP(HttpServletRequest req) {
                StringBuilder builder = new StringBuilder();
                builder.append (req.getRemoteAddr());
                String forwarded;
                // RFC 7239 in 2014 replaced X-Forwarded for; first look for this
                // See http://en.wikipedia.org/wiki/X-Forwarded-For
                if ((forwarded = req.getHeader("Forwarded")) != null) {
                        builder.append(" (");
                        // XXX We might want to limit the information, to not get swamped by erroneous headers
                        builder.append(forwarded);
                        builder.append(")");                        
                } else if ((forwarded  = req.getHeader("X-Forwarded-For")) != null) {
                        builder.append(" (");
                        // XXX We might want to limit the information, to not get swamped by erroneous headers
                        builder.append(forwarded);
                        builder.append(")");
                }
                
		return builder.toString();
	}

}
