package haw.common.resource.entity.helper;

import static haw.common.helper.StringHelper.isEmpty;
import haw.common.resource.base.HawEntity;
import haw.common.resource.entity.Person;
import haw.common.resource.type.Gender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Class MergeHelper. This class provides helper methods for manual merging.
 */
public abstract class MergeHelper {

    /**
     * The Constant EMPTY_VALUE_STRING.
     */
    public static final String EMPTY_VALUE_STRING = "EMPTY";

    /**
     * The Constant EMPTY_VALUE_FLOAT.
     */
    public static final Float EMPTY_VALUE_FLOAT = 0.0f;

    /**
     * The Constant EMPTY_VALUE_DOUBLE.
     */
    public static final Double EMPTY_VALUE_DOUBLE = 0.0;

    /**
     * The Constant EMPTY_VALUE_INT.
     */
    public static final Integer EMPTY_VALUE_INT = 0;

    /**
     * The Constant EMPTY_VALUE_BOOL.
     */
    public static final Boolean EMPTY_VALUE_BOOL = Boolean.FALSE;

    /**
     * The Constant EMPTY_VALUE_DATE.
     */
    public static final Date EMPTY_VALUE_DATE = new Date(0);

    /**
     * The Constant EMPTY_VALUE_LIST.
     */
    public static final List<?> EMPTY_VALUE_LIST = Collections.EMPTY_LIST;

    /**
     * The Constant EMPTY_VALUE_SET.
     */
    public static final Set<?> EMPTY_VALUE_SET = Collections.EMPTY_SET;

    /**
     * The Constant EMPTY_VALUE_MAP.
     */
    public static final Map<?, ?> EMPTY_VALUE_MAP = Collections.EMPTY_MAP;

    /**
     * The Constant EMPTY_VALUE_GENDER.
     */
    public static final Gender EMPTY_VALUE_GENDER = Gender.UNSPECIFIED;

    public static final double EPS = 1.0 / 10000;

    /**
     * Compares two {@link Double}s. Two {@link Double}s are equal, if
     * <ul>
     * <li>they are NOT NaN or infinite,</li>
     * <li>if they lie within an EPS boundary...</li>
     * </ul>
     *
     * @param dbl1 first Double to compare
     * @param dbl2 second Double to compare
     * @return true, if the doubles are within a boundary EPS.
     */
    public static boolean areDoublesEqual(Double dbl1, Double dbl2) {
        boolean equals = false;
        // If there are any inifite values (infinity, or NaN), return false
        if (!Double.isFinite(dbl1) || !Double.isFinite(dbl2)) {
            equals = false;
        } else if (dbl1 >= Math.abs(dbl2 - EPS) && dbl1 <= Math.abs(dbl2 + EPS)) {
            equals = true;
        }
        return equals;
    }

    /**
     * Compares two {@link String}s. Two {@link String}s are equal, if
     * <ul>
     * <li>they have identical content</li>
     * <li>trimmed {@link String}s have identical content</li>
     * <li>one String is null and the other empty or consists only of
     * blanks</li>
     * </ul>
     *
     * @see String#trim()
     * @param str1 first String to compare
     * @param str2 second String to compare
     * @return true, if successful
     */
    public static boolean areStringsEqual(String str1, String str2) {
        boolean equals = false;
        if (isEmpty(str1) && isEmpty(str2)) {
            equals = true;
        } else if (str1 != null && str2 != null) {
            equals = str1.trim().equals(str2.trim());
        }
        return equals;
    }

    /**
     * Compares two {@link Object}s. Two {@link Object}s are equal, if
     * <ul>
     * <li>both are null</li>
     * <li>comparison by {@link Object#equals(Object) }method says that they are
     * equal</li>
     * </ul>
     *
     * @param obj1 first Object to compare
     * @param obj2 first Object to compare
     * @return true, if successful
     */
    public static boolean areObjectsEqual(Object obj1, Object obj2) {
        boolean equals = false;
        if (obj1 == null && obj2 == null) {
            equals = true;
        } else if (obj1 != null && obj2 != null) {
            equals = obj1.equals(obj2);
        }
        return equals;
    }

    /**
     * Compares two {@link Collection}s. Two {@link Collection}s are equal, if
     * <ul>
     * <li>both are null</li>
     * <li>Collection sizes are equal and all contents of second Collection are
     * in first Collection</li>
     * </ul>
     *
     * @see Collection#containsAll(Collection)
     * @param list1 first Collection to compare
     * @param list2 second Collection to compare
     * @return true, if successful
     */
    public static boolean areCollectionsEqual(Collection<?> list1,
            Collection<?> list2) {
        boolean equals = false;
        if (list1 == null && list2 == null) {
            equals = true;
        } else if (list1 != null && list2 != null) {
            equals = (list1.size() == list2.size())
                    && (list1.containsAll(list2));
        }
        return equals;
    }

    /**
     * Compares two {@link Map}s. Two {@link Map}s are equal, if
     * <ul>
     * <li>both are null</li>
     * <li>Map sizes are equal and all contents of second Map are in the first
     * Map</li>
     * </ul>
     *
     * @see Set#containsAll(Collection)
     * @param map1 first Map to compare
     * @param map2 second Map to compare
     * @return true, if successful
     */
    public static boolean areMapsEqual(Map<?, ?> map1, Map<?, ?> map2) {
        boolean equals = false;
        if (map1 == null && map2 == null) {
            equals = true;
        } else if (map1 != null && map2 != null) {
            equals = (map1.size() == map2.size())
                    && (map1.entrySet().containsAll(map2.entrySet()));
        }
        return equals;
    }

    /**
     * Checks if an entity is empty.
     *
     * @param e the entity
     * @return true if entity is null or no attribute is set.
     * 
     * XXX/TODO: Rename
     */
    public static boolean isJakEntityEmpty(HawEntity e) {
        return (e == null) || (e.isEntityEmpty());
    }

    /**
     * Checks if a collection is empty.
     *
     * @param c the collection
     * @return true if collection is null or empty
     */
    public static boolean isCollectionEntityEmpty(Collection<?> c) {
        return (c == null) || (c.isEmpty());
    }

    /**
     * Compares two {@link Collection}s and checks if theList contains at least
     * one element of the {@link Collection} elementsToCheck. If one element is
     * found true will be returned. The elements will be checked by using the
     * equals method of the element.
     *
     * @param theList the {@link Collection} which may contain one element of
     * the other list.
     * @param elementsToCheck the {@link Collection} which element will be
     * searched in theList
     * @return true, if successful
     */
    public static boolean containsAtLeastOneOf(Collection<?> theList,
            Collection<?> elementsToCheck) {
        boolean contains = false;
        if (theList != null && !theList.isEmpty() && elementsToCheck != null
                && !elementsToCheck.isEmpty()) {
            for (Object obj : theList) {
                for (Object etc : elementsToCheck) {
                    if (obj.equals(etc)) {
                        contains = true;
                        break;
                    }
                }
            }
        }
        return contains;
    }

    /**
     * Returns a normalized {@link String}. If the given value is not empty, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_STRING}
     *
     * @see StringHelper#isEmpty(String)
     * @param value the value to be checked
     * @return the normalized {@link String}
     */
    public static String norm(String value) {
        if (isEmpty(value)) {
            return EMPTY_VALUE_STRING;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Float}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_FLOAT}
     *
     * @param value the value to be checked
     * @return the normalized {@link Float}
     */
    public static Float norm(Float value) {
        if (value == null) {
            return EMPTY_VALUE_FLOAT;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Double}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_DOUBLE}
     *
     * @param value the value to be checked
     * @return the normalized {@link Double}
     */
    public static Double norm(Double value) {
        if (value == null) {
            return EMPTY_VALUE_DOUBLE;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Integer}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_INT}
     *
     * @param value the value to be checked
     * @return the normalized {@link Integer}
     */
    public static Integer norm(Integer value) {
        if (value == null) {
            return EMPTY_VALUE_INT;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Boolean}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_BOOL}
     *
     * @param value the value to be checked
     * @return the normalized {@link Boolean}
     */
    public static Boolean norm(Boolean value) {
        if (value == null) {
            return EMPTY_VALUE_BOOL;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Date}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_DATE}
     *
     * @param value the value to be checked
     * @return the normalized {@link Date}
     */
    public static Date norm(Date value) {
        if (value == null) {
            return EMPTY_VALUE_DATE;
        }
        return value;
    }

    /**
     * Returns a normalized generic {@link List}. If the given {@link List} is
     * not null, the {@link List} will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_LIST}
     *
     * @param <T> the generic type
     * @param value the {@link List} to be checked
     * @return the normalized {@link List}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> norm(List<T> value) {
        if (value == null) {
            return (List<T>) EMPTY_VALUE_LIST;
        }
        return value;
    }

    /**
     * Returns a normalized generic {@link Set}. If the given {@link Set} is not
     * null, the {@link Set} will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_SET}
     *
     * @param <T> the generic type
     * @param value the {@link Set} to be checked
     * @return the normalized {@link Set}
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> norm(Set<T> value) {
        if (value == null) {
            return (Set<T>) EMPTY_VALUE_SET;
        }
        return value;
    }

    /**
     * Returns a normalized generic {@link Map}. If the given {@link Map} is not
     * null, the {@link Map} will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_MAP}
     *
     * @param <TKey> the generic type of the Key
     * @param <TValue> the generic type of the Value
     * @param value the {@link Map} to be checked
     * @return the normalized {@link Map}
     */
    @SuppressWarnings("unchecked")
    public static <TKey, TValue> Map<TKey, TValue> norm(Map<TKey, TValue> value) {
        if (value == null) {
            return (Map<TKey, TValue>) EMPTY_VALUE_MAP;
        }
        return value;
    }

    /**
     * Returns a normalized {@link Gender}. If the given value is not null, the
     * value will be returned. Otherwise the method returns
     * {@link MergeHelper#EMPTY_VALUE_GENDER}
     *
     * @param value the value to be checked
     * @return the normalized value
     */
    public static Gender norm(Gender value) {
        if (value == null) {
            return EMPTY_VALUE_GENDER;
        }
        return value;
    }


    /**
     * Returns a normalized {@link JakEntity}. If the given value is not null,
     * the value will be returned. Otherwise the method returns the given
     * defaultValue.
     *
     * @param <T> the generic type
     * @param value the value to be checked
     * @param defaultValue default value to return if the given parameter value
     * is null
     * @return the normalized value
     */
    public static <T extends HawEntity> T norm(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Returns entries which are in the new list, but not in the old list. The
     * entries will be compared with method equals.
     *
     * @param <T> the generic type
     * @param oldList List with old entries.
     * @param newList List with new entries
     * @return Collection with only new entries.
     */
    public <T> Collection<T> getCreated(List<T> oldList, List<T> newList) {
        Collection<T> changes = new ArrayList<>();
        for (T e : newList) {
            int index = oldList.indexOf(e);
            if (index < 0) {
                changes.add(e);
            }
        }
        return changes;
    }

    /**
     * Returns entries which are in the old list, but not in the new list. The
     * entries will be compared with method equals.
     *
     * @param <T> the generic type
     * @param oldList the old list
     * @param newList List with new entries
     * @return Collection with only deleted entries.
     */
    public <T> Collection<T> getDeleted(List<T> oldList, List<T> newList) {
        Collection<T> changes = new ArrayList<>();
        for (T e : oldList) {
            int index = newList.indexOf(e);
            if (index < 0) {
                changes.add(e);
            }
        }
        return changes;
    }

}
