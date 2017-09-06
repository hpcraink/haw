package haw.common.resource.wrapper;

import haw.common.resource.base.HawWrapper;
import haw.common.resource.entity.Person;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class represents the {@code Collection<Person>}.
 *
 * @author Katharina
 */
@XmlRootElement
@XmlSeeAlso(Person.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Persons extends HawWrapper<Person> {

    /**
     * The list of elements
     */
    private List<Person> elements = new LinkedList<>();

    /**
     * Default constructor.
     */
    public Persons() {
        super();
    }

    /**
     * Instantiates a new wrapper for {@link Person}.
     *
     * @param persons the Collection of {@link Person}
     * @param pageNumber the current page number
     * @param pages the number of all pages
     * @param rowcount the number of all rows in the database
     */
    public Persons(List<Person> persons, int pageNumber, int pages,
            int rowcount) {
        super(pageNumber, pages, rowcount);
        setElements(persons);
    }

    /**
     * Returns a list of persons.
     *
     * @return List of persons
     */
    @XmlElement(name = "person")
    public List<Person> getElements() {
        return this.elements;
    }

    /**
     * Sets a list of persons.
     *
     * @param elements List of person to set.
     */
    @Override
    public void setElements(List<Person> elements) {
        this.elements = new LinkedList<>(elements);
    }

}
