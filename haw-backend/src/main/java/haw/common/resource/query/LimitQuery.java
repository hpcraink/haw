package haw.common.resource.query;

/**
 * A wrapper class for a page limit query. This class wraps the parameter 'page'
 * and 'limit'.
 * 
 * @author Uwe Eisele
 */
public class LimitQuery {

	/** The current page number. */
	private int page;

	/** The limit for results on one page. */
	private int limit;

	/**
	 * Instantiates a new query wrapper without a limit.
	 */
	public LimitQuery() {
		this.page = 1;
		this.limit = 0;
	}

	/**
	 * Instantiates a new query wrapper.
	 * 
	 * @param page
	 *            Page number (Starting from page number 1)
	 * @param limit
	 *            the amount of expected records per page
	 */
	public LimitQuery(int page, int limit) {
		this.page = (page >= 1 ? page : 1);
		this.limit = limit;
	}

	/**
	 * Returns the page number (Starting from page number 1).
	 * 
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the page number (Starting from page number 1).
	 * 
	 * @param page
	 *            the new page
	 */
	public void setPage(int page) {
		this.page = (page >= 1 ? page : 1);
	}

	/**
	 * Returns the amount of expected records per page.
	 * 
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Sets the amount of expected records per page.
	 * 
	 * @param limit
	 *            the new limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Calculates the number of the first expected record.
	 * 
	 * @return the from
	 */
	public int getFrom() {
		int from = (page - 1) * limit;
		return (from > 0 ? from : 0);
	}

	/**
	 * Calculates the number of the last expected record.
	 * 
	 * @return the to
	 */
	public int getTo() {
		return getFrom() + limit;
	}

	/**
	 * Indicates whether the specific instance has defined a page limit.
	 * 
	 * @return True if page limit is defined, false if not.
	 */
	public boolean hasLimit() {
		return limit > 0 && page > 0;
	}

	/**
	 * Calculates the number of pages with regard to the limit.
	 * 
	 * @param numberOfEntries
	 *            Number of database entries
	 * @return number of pages to display
	 */
	public int calculateNoOfPages(Long numberOfEntries) {
		float pages = 1f;
		if (limit > 0) {
			pages = (numberOfEntries.longValue() / (float) limit);
		}
		if (pages > Math.round(pages)) {
			pages += 1;
		}
		int pageResult = Math.round(pages);
		if (pageResult < getPage()) {
			setPage(pageResult);
		}
		return pageResult;
	}
}
