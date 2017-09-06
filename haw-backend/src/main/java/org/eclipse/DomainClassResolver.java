/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Blaise Doughan - 2.4 - initial implementation
 ******************************************************************************/
package org.eclipse;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import javax.xml.bind.JAXBElement;

/**
 * This class contains a method which is part of the class org.eclipse.persistence.jaxb.rs.MOXyJsonProvider.
 * The method was extracted from this class because it was not public accessible. 
 */
public abstract class DomainClassResolver {
	
	/**
     * A convenience method to get the domain class (i.e. <i>Customer</i>) from 
     * the parameter/return type (i.e. <i>Customer</i>, <i>List&lt;Customer></i>,
     * <i>JAXBElement&lt;Customer></i>, <i>JAXBElement&lt;? extends Customer></i>, 
     * <i>List&lt;JAXBElement&lt;Customer>></i>, or 
     * <i>List&lt;JAXBElement&lt;? extends Customer>></i>).
     *
     * @param genericType - The parameter/return type of the JAX-RS operation.
     * @return The corresponding domain class.
     */
    public static Class<?> getDomainClass(Type genericType) {
        if(null == genericType) {
            return Object.class;
        }
        if(genericType instanceof Class && genericType != JAXBElement.class) {
            Class<?> clazz = (Class<?>) genericType;
            if(clazz.isArray()) {
                return getDomainClass(clazz.getComponentType());
            }
            return clazz;
        } else if(genericType instanceof ParameterizedType) {
            Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if(type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if(rawType == JAXBElement.class) {
                    return getDomainClass(type);
                }
            } else if(type instanceof WildcardType) {
                Type[] upperTypes = ((WildcardType)type).getUpperBounds();
                if(upperTypes.length > 0){
                    Type upperType = upperTypes[0];
                    if(upperType instanceof Class){
                        return (Class<?>) upperType;
                    }
                }
            } else if(JAXBElement.class == type) {
                return Object.class;
            }
            return (Class<?>) type;
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;
            return getDomainClass(genericArrayType.getGenericComponentType());
        } else {
            return Object.class;
        }
    }
    
}