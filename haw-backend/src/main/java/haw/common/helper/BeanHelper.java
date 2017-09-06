package haw.common.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.NamingException;

/**
 * Helper class for working with the CDI BeanManager.
 * 
 * @author Uwe Eisele
 */
public abstract class BeanHelper {

	/**
	 * Returns the BeanManager instance.
	 * 
	 * @return the bean manager
	 */
	public static BeanManager getBeanManager() {
		return CDI.current().getBeanManager();
	}

	/**
	 * Lookups for a instance of the given class. If there are more then one
	 * instances, the first is returned.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param beanClass
	 *            The class to look for a instance
	 * @param annotations
	 *            Qualifier annotations to match
	 * @return the t
	 * @throws NamingException
	 *             Thrown if no instance of the given class can be
	 *             found
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(Class<T> beanClass, Annotation... annotations)
			throws NamingException {
		T obj;
		try {
			BeanManager bm = getBeanManager();
			Set<Bean<?>> beans = bm.getBeans(beanClass, annotations);
			Bean<?> bean = bm.resolve(beans);
			CreationalContext<T> ctx = bm
					.createCreationalContext((Bean<T>) bean);
			obj = (T) bm.getReference(bean, beanClass, ctx);
		} catch (Exception e) {
			String message = String.format(
					"Could not get bean of class %s through bean manager!",
					beanClass.getName());
			throw new NamingException(message);
		}
		return obj;
	}
	
	/**
	 * Converts an InjectionPoint to readable string.
	 * 
	 * @param ip The injection point
	 * @return The readable string
	 */
	public static String injectionPointToString(InjectionPoint ip) {
    	StringBuilder buffer = new StringBuilder("InjectionPoint");
    	buffer.append("\n\tgetAnnotated:"+ip.getAnnotated());
    	buffer.append("\n\tgetAnnotations:"+ip.getAnnotated().getAnnotations());
    	buffer.append(";\n\t getType:"+ip.getType());
    	ParameterizedType type = (ParameterizedType) ip.getType();
    	Type[] typeArgs = type.getActualTypeArguments();
    	Type rawType = type.getRawType();
    	buffer.append("\n\t\ttypeArgs: "+ Arrays.asList(typeArgs));
    	buffer.append("\n\t\trawType: "+ rawType);
    	buffer.append(";\n\t getQualifiers:"+ip.getQualifiers());
    	buffer.append(";\n\t getBean:"+ip.getBean());
    	buffer.append(";\n\t getMember:"+ip.getMember());
    	buffer.append(";\n\t isDelegate:"+ip.isDelegate());
    	buffer.append(";\n\t isTransient:"+ip.isTransient());
    	return buffer.toString();
    }

}