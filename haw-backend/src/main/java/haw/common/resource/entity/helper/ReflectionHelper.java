package haw.common.resource.entity.helper;

import haw.common.helper.StringHelper;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.DomainClassResolver;

/**
 * Helper for working with reflection.
 *
 * @author Uwe Eisele
 */
public abstract class ReflectionHelper extends DomainClassResolver {

    /**
     * Returns the field name of a given getter or setter method.
     *
     * @param name Name of the getter or setter method
     * @return the field name by method
     */
    public static String getFieldNameByMethod(String name) {
        return StringHelper.firstCharToLowerCaser(name.substring(3,
                name.length()));
    }

    /**
     * Returns the name of the setter method by a given getter method.
     *
     * @param name Name of the setter Method
     * @return the setter name by method
     */
    public static String getSetterNameByMethod(String name) {
        return String.format("set%s", name.substring(3, name.length()));
    }

    /**
     * Gets the setter name by field.
     *
     * @param name the name of the field
     * @return the setter name by field
     */
    public static String getSetterNameByField(String name) {
        return String.format("set%s", StringHelper.firstCharToUpperCaser(name));
    }

    /**
     * Gets the getter name by field.
     *
     * @param name the name of the field
     * @return the getter name by field
     */
    public static String getGetterNameByField(String name) {
        return String.format("get%s", StringHelper.firstCharToUpperCaser(name));
    }

    /**
     * Returns the getter method of the given field.
     *
     * @param clazz The clazz
     * @param field The field of the clazz
     * @return the getter method
     */
    public static Method getGetterByField(Class<?> clazz, Field field) {
        String getterName = getGetterNameByField(field.getName());
        return getMethodByName(clazz, getterName);
    }

    /**
     * Returns the method with the given name or null if not found.
     *
     * @param clazz The class name
     * @param name The name of the method
     * @param parameterTypes The parameters of the method
     * @return The method with the given name or null if not found.
     */
    public static Method getMethodByName(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    /**
     * Executes the given method of the given object with given parameter.
     *
     * @param obj the Object which method should be executed
     * @param methodName the method name to execute
     * @param parameter the List of parameter to forward to the method
     * @return the return object of the method
     * @throws IllegalAccessException Thrown if this Method object is enforcing
     * Java language access control and the underlying method is inaccessible.
     * @throws IllegalArgumentException Thrown if the method is an instance
     * method and the specified object argument is not an instance of the class
     * or interface declaring the underlying method (or of a subclass or
     * implementor thereof); if the number of actual and formal parameters
     * differ; if an unwrapping conversion for primitive arguments fails; or if,
     * after possible unwrapping, a parameter value cannot be converted to the
     * corresponding formal parameter type by a method invocation conversion.
     * @throws InvocationTargetException Thrown if the underlying method throws
     * an exception.
     * @throws NoSuchMethodException Thrown when a particular method cannot be
     * found.
     * @throws SecurityException Thrown if a security manager, s, is present and
     * any of the following conditions is met:
     * <ul>
     * <li>invocation of s.checkMemberAccess(this, Member.DECLARED) denies
     * access to the declared method</li>
     * <li>the caller's class loader is not the same as or an ancestor of the
     * class loader for the current class and invocation of
     * s.checkPackageAccess() denies access to the package of this class</li>
     * </ul>
     */
    public static Object executeMethod(Object obj, String methodName,
            Object... parameter) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        Class<?> clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName,
                getClasses(parameter));
        boolean isAccessible = method.isAccessible();
        try {
            method.setAccessible(true);
            return method.invoke(obj, parameter);
        } finally {
            method.setAccessible(isAccessible);
        }
    }

    /**
     * Sets a value to the field with the given name.
     *
     * @param object The object which attribute should be changed
     * @param fieldName the name of the field
     * @param value the value
     *
     * @throws IllegalArgumentException Thrown if the method is an instance
     * method and the specified object argument is not an instance of the class
     * or interface declaring the underlying method (or of a subclass or
     * implementor thereof); if the number of actual and formal parameters
     * differ; if an unwrapping conversion for primitive arguments fails; or if,
     * after possible unwrapping, a parameter value cannot be converted to the
     * corresponding formal parameter type by a method invocation conversion.
     * @throws IllegalAccessException Thrown is thrown when an application tries
     * to reflectively create an instance (other than an array), set or get a
     * field, or invoke a method, but the currently executing method does not
     * have access to the definition of the specified class, field, method or
     * constructor.
     * @throws NoSuchFieldException Thrown if a field with the specified name is
     * not found.
     * @throws SecurityException Thrown if a security manager, s, is present and
     * any of the following conditions is met:
     * <ul>
     * <li>invocation of s.checkMemberAccess(this, Member.DECLARED) denies
     * access to the declared method</li>
     * <li>the caller's class loader is not the same as or an ancestor of the
     * class loader for the current class and invocation of
     * s.checkPackageAccess() denies access to the package of this class</li>
     * </ul>
     */
    public static void setValueForField(Object object, String fieldName, Object value)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field field = object.getClass().getDeclaredField(fieldName);
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(object, value);
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    /**
     * Gets the classes to the given Objects.
     *
     * @param objects the objects to search for classes
     * @return the associated classes
     */
    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        int i = 0;
        for (Object obj : objects) {
            classes[i++] = obj.getClass();
        }
        return classes;
    }

    /**
     * Creates an instance of the given {@link HawEntity} class with the given
     * id.
     *
     * @param <T> the generic type
     * @param entityId the unique id of the entity.
     * @param entityClass the entity class to create
     * @return a new instance of the given class.
     */
    public static <T extends HawEntity> HawEntity createHawEntity(int entityId,
            Class<T> entityClass) {
        HawEntity tmpEntity;
        try {
            tmpEntity = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can not create an instance of the entity!", e);
        }
        tmpEntity.setId(entityId);
        return tmpEntity;
    }

    /**
     * Creates the jak wrapper for an entity.
     *
     * @param <E> the generic entity
     * @param <W> the generic wrapper for the entity
     * @param wrapperClass the wrapper class to use
     * @param elements the list of entity elements to wrap
     * @param pageNumber the current pageNumber
     * @param pages the number of all pages
     * @param rowcount the number of all rows in the database
     * @return the wrapper for an entity.
     */
    public static <E, W extends HawWrapper<E>> W createHawWrapper(Class<W> wrapperClass,
            List<E> elements, int pageNumber, int pages, int rowcount) {
        W jakWrapper;
        try {
            jakWrapper = wrapperClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can not create an instance of the wapper!", e);
        }
        jakWrapper.setElements(elements);
        jakWrapper.setPageNumber(pageNumber);
        jakWrapper.setPages(pages);
        jakWrapper.setRowcount(rowcount);
        return jakWrapper;
    }

}
