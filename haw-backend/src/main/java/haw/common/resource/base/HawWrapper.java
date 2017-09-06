package haw.common.resource.base;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is a wrapper for all responses which containing a list of
 * elements.
 *
 * @param <E> the element type of the wrapper object.
 * @author Katharina Knaus
 * @author Uwe Eisele
 */
@XmlRootElement
public abstract class HawWrapper<E> {

    /**
     * The number of all pages available.
     */
    private int pages;

    /**
     * The current page number.
     */
    private int pageNumber;

    /**
     * The number of all rows.
     */
    private int rowcount;

    /**
     * Instantiates a new response wrapper.
     */
    public HawWrapper() {
        this(1, 1, 0);
    }

    /**
     * Instantiates a new response wrapper.
     *
     * @param pageNumber the current page number
     * @param pages the number of all pages
     * @param rowcount the number of all rows in the database
     */
    public HawWrapper(int pageNumber, int pages, int rowcount) {
        this.pages = pages;
        this.pageNumber = pageNumber;
        this.rowcount = rowcount;
    }

    /**
     * Gets the pages.
     *
     * @return the pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * Sets the pages.
     *
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Gets the page number.
     *
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the page number.
     *
     * @param pageNumber the new page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets the rowcount.
     *
     * @return the rowcount
     */
    public int getRowcount() {
        return rowcount;
    }

    /**
     * Sets the rowcount.
     *
     * @param rowcount the rowcount to set
     */
    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    /**
     * Sets the elements of this wrapper.
     *
     * @param elements the elements to set
     */
    public abstract void setElements(List<E> elements);
}
