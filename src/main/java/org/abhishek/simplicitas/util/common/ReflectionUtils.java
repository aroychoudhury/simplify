/* 
 * Heavily Drawn from the original Spring Code with minor tweaks.
 * Original Copyright text reproduced below.
 * 
 * Spring code source details:
 * org.springframework/spring-core/4.2.0.RELEASE/org/springframework/util/ReflectionUtils.java
 */

/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.abhishek.simplicitas.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.abhishek.simplicitas.display.domain.DisplayBase;

/**
 * Simple utility class for working with the reflection API.
 *
 * @author abhishek
 * @since 1.0
 */
public class ReflectionUtils extends ExceptionUtils {
    /**
     * @param target
     * @return
     * @author abhishek
     * @since  1.0
     */
    public static List<DisplayBase> extractClassFieldsForDisplay(Object target) {
        List<DisplayBase> displays = new ArrayList<DisplayBase>();

        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = target.getClass();
        do {
            Field[] fields = getDeclaredFields(targetClass);
            for (Field field : fields) {
                String[] parameterTypes = null;

                // calculate field value
                Object fieldValue = getField(field, target);

                // calculate field data-type
                String fieldType = field.getType().getName();
                if ("java.lang.Object".equals(fieldType)) {
                    fieldType = fieldValue.getClass().getName();
                
                // handles the array
                } else if (-1 != fieldType.indexOf("[")) {
                    String typeSimpleName = field.getType().getSimpleName();
                    int arrayStartIdx = typeSimpleName.indexOf("[]");

                    // handle primitive type
                    // if the substring is not found that means its a primitive
                    if (-1 == fieldType.indexOf(typeSimpleName.substring(0, arrayStartIdx))) {
                        parameterTypes = new String[] {
                            typeSimpleName.substring(0, arrayStartIdx)
                        };
                    } else {
                        int bracketIdx = fieldType.lastIndexOf("[") + 2;
                        parameterTypes = new String[] {
                            fieldType.substring(bracketIdx, (fieldType.length() - 1))
                        };
                    }
                    fieldType = typeSimpleName.substring(arrayStartIdx);
                }

                // calculate generic field types
                Type genericFieldType = field.getGenericType();
                if (genericFieldType instanceof ParameterizedType) {
                    ParameterizedType aType = (ParameterizedType) genericFieldType;
                    Type[] fieldArgTypes = aType.getActualTypeArguments();
                    parameterTypes = new String[fieldArgTypes.length];
                    for (int idx = 0; idx < fieldArgTypes.length; idx++) {
                        parameterTypes[idx] = ((Class<?>) fieldArgTypes[idx]).getName();
                    }
                }

                displays.add(new DisplayBase(field.getName(), fieldValue, fieldType, parameterTypes));
            }
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);

        return displays;
    }

    /**
     * @param target
     * @param displays
     * @return
     * @author abhishek
     * @since  1.0
     */
    public static <T> T extractDisplayFieldsAsObject(T target, List<DisplayBase> displays) {
        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = target.getClass();
        for (DisplayBase display : displays) {
            setField(findField(targetClass, display.getName()), target, display.getValue());
        }
        return target;
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with
     * the supplied {@code name}. Searches all superclasses up to {@link Object}
     * .
     * 
     * @param clazz
     *            the class to introspect
     * @param name
     *            the name of the field
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with
     * the supplied {@code name} and/or {@link Class type}. Searches all
     * superclasses up to {@link Object}.
     * 
     * @param clazz
     *            the class to introspect
     * @param name
     *            the name of the field (may be {@code null} if type is
     *            specified)
     * @param type
     *            the type of the field (may be {@code null} if name is
     *            specified)
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        if (name == null && type == null) {
            throw new IllegalArgumentException("Either name or type must be specified");
        }
        // "Either name or type of the field must be specified");
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = getDeclaredFields(searchType);
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Set the field represented by the supplied {@link Field field object} on
     * the specified {@link Object target object} to the specified {@code value}
     * . In accordance with {@link Field#set(Object, Object)} semantics, the new
     * value is automatically unwrapped if the underlying field has a primitive
     * type.
     * <p>
     * Thrown exceptions are handled via a call to
     * {@link #handleReflectionException(Exception)}.
     * 
     * @param field
     *            the field to set
     * @param target
     *            the target object on which to set the field
     * @param value
     *            the value to set; may be {@code null}
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    /**
     * Get the field represented by the supplied {@link Field field object} on
     * the specified {@link Object target object}. In accordance with
     * {@link Field#get(Object)} semantics, the returned value is automatically
     * wrapped if the underlying field has a primitive type.
     * <p>
     * Thrown exceptions are handled via a call to
     * {@link #handleReflectionException(Exception)}.
     * 
     * @param field
     *            the field to get
     * @param target
     *            the target object from which to get the field
     * @return the field's current value
     */
    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object
     * with no arguments. The target object can be {@code null} when invoking a
     * static {@link Method}.
     * <p>
     * Thrown exceptions are handled via a call to
     * {@link #handleReflectionException}.
     * 
     * @param method
     *            the method to invoke
     * @param target
     *            the target object to invoke the method on
     * @return the invocation result, if any
     * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object
     * with the supplied arguments. The target object can be {@code null} when
     * invoking a static {@link Method}.
     * <p>
     * Thrown exceptions are handled via a call to
     * {@link #handleReflectionException}.
     * 
     * @param method
     *            the method to invoke
     * @param target
     *            the target object to invoke the method on
     * @param args
     *            the invocation arguments (may be {@code null})
     * @return the invocation result, if any
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /**
     * Determine whether the given method explicitly declares the given
     * exception or one of its superclasses, which means that an exception of
     * that type can be propagated as-is within a reflective invocation.
     * 
     * @param method
     *            the declaring method
     * @param exceptionType
     *            the exception to throw
     * @return {@code true} if the exception can be thrown as-is; {@code false}
     *         if it needs to be wrapped
     */
    public static boolean declaresException(Method method, Class<?> exceptionType) {
        // Assert.notNull(method, "Method must not be null");
        Class<?>[] declaredExceptions = method.getExceptionTypes();
        for (Class<?> declaredException : declaredExceptions) {
            if (declaredException.isAssignableFrom(exceptionType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the given field is a "public static final" constant.
     * 
     * @param field
     *            the field to check
     */
    public static boolean isFieldPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
    }

    /**
     * Determine whether the given method is an "equals" method.
     * 
     * @see java.lang.Object#equals(Object)
     */
    public static boolean isEqualsMethod(Method method) {
        if (method == null || !method.getName().equals("equals")) {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return (paramTypes.length == 1 && paramTypes[0] == Object.class);
    }

    /**
     * Determine whether the given method is a "hashCode" method.
     * 
     * @see java.lang.Object#hashCode()
     */
    public static boolean isHashCodeMethod(Method method) {
        return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
    }

    /**
     * Determine whether the given method is a "toString" method.
     * 
     * @see java.lang.Object#toString()
     */
    public static boolean isToStringMethod(Method method) {
        return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
    }

    /**
     * Determine whether the given method is originally declared by
     * {@link java.lang.Object}.
     */
    public static boolean isObjectMethod(Method method) {
        if (method == null) {
            return false;
        }
        try {
            Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class, as locally declared or equivalent thereof (such as default methods
     * on Java 8 based interfaces that the given class implements).
     * 
     * @param clazz
     *            the class to introspect
     * @param mc
     *            the callback to invoke for each method
     * @since 4.2
     * @see #doWithMethods
     */
    public static void doWithLocalMethods(Class<?> clazz, MethodCallback mc) {
        Method[] methods = getDeclaredMethods(clazz);
        for (Method method : methods) {
            try {
                mc.doWith(method);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
            }
        }
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class and superclasses.
     * <p>
     * The same named method occurring on subclass and superclass will appear
     * twice, unless excluded by a {@link MethodFilter}.
     * 
     * @param clazz
     *            the class to introspect
     * @param mc
     *            the callback to invoke for each method
     * @see #doWithMethods(Class, MethodCallback, MethodFilter)
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
        doWithMethods(clazz, mc, null);
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class and superclasses (or given interface and super-interfaces).
     * <p>
     * The same named method occurring on subclass and superclass will appear
     * twice, unless excluded by the specified {@link MethodFilter}.
     * 
     * @param clazz
     *            the class to introspect
     * @param mc
     *            the callback to invoke for each method
     * @param mf
     *            the filter that determines the methods to apply the callback
     *            to
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) {
        // Keep backing up the inheritance hierarchy.
        Method[] methods = getDeclaredMethods(clazz);
        for (Method method : methods) {
            if (mf != null && !mf.matches(method)) {
                continue;
            }
            try {
                mc.doWith(method);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
            }
        }
        if (clazz.getSuperclass() != null) {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        } else if (clazz.isInterface()) {
            for (Class<?> superIfc : clazz.getInterfaces()) {
                doWithMethods(superIfc, mc, mf);
            }
        }
    }

    /**
     * Get all declared methods on the leaf class and all superclasses. Leaf
     * class methods are included first.
     * 
     * @param leafClass
     *            the class to introspect
     */
    public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
        final List<Method> methods = new ArrayList<Method>(32);
        doWithMethods(leafClass, new MethodCallback() {
            @Override
            public void doWith(Method method) {
                methods.add(method);
            }
        });
        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * Get the unique set of declared methods on the leaf class and all
     * superclasses. Leaf class methods are included first and while traversing
     * the superclass hierarchy any methods found with signatures matching a
     * method already included are filtered out.
     * 
     * @param leafClass
     *            the class to introspect
     */
    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) {
        final List<Method> methods = new ArrayList<Method>(32);
        doWithMethods(leafClass, new MethodCallback() {
            @Override
            public void doWith(Method method) {
                boolean knownSignature = false;
                Method methodBeingOverriddenWithCovariantReturnType = null;
                for (Method existingMethod : methods) {
                    if (method.getName().equals(existingMethod.getName()) && Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
                        // Is this a covariant return type situation?
                        if (existingMethod.getReturnType() != method.getReturnType() && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                            methodBeingOverriddenWithCovariantReturnType = existingMethod;
                        } else {
                            knownSignature = true;
                        }
                        break;
                    }
                }
                if (methodBeingOverriddenWithCovariantReturnType != null) {
                    methods.remove(methodBeingOverriddenWithCovariantReturnType);
                }
                if (!knownSignature) {
                    methods.add(method);
                }
            }
        });
        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * This variant retrieves {@link Class#getDeclaredMethods()} from a local
     * cache in order to avoid the JVM's SecurityManager check and defensive
     * array copying. In addition, it also includes Java 8 default methods from
     * locally implemented interfaces, since those are effectively to be treated
     * just like declared methods.
     * 
     * @param clazz
     *            the class to introspect
     * @return the cached array of methods
     * @see Class#getDeclaredMethods()
     */
    private static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = null;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
        if (defaultMethods != null) {
            result = new Method[declaredMethods.length + defaultMethods.size()];
            System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
            int index = declaredMethods.length;
            for (Method defaultMethod : defaultMethods) {
                result[index] = defaultMethod;
                index++;
            }
        } else {
            result = declaredMethods;
        }
        return result;
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new LinkedList<Method>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

    /**
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     * 
     * @param clazz
     *            the target class to analyze
     * @param fc
     *            the callback to invoke for each field
     * @since 4.2
     * @see #doWithFields
     */
    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        for (Field field : getDeclaredFields(clazz)) {
            try {
                fc.doWith(field);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
            }
        }
    }

    /**
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     * 
     * @param clazz
     *            the target class to analyze
     * @param fc
     *            the callback to invoke for each field
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        doWithFields(clazz, fc, null);
    }

    /**
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     * 
     * @param clazz
     *            the target class to analyze
     * @param fc
     *            the callback to invoke for each field
     * @param ff
     *            the filter that determines the fields to apply the callback to
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {
        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = clazz;
        do {
            Field[] fields = getDeclaredFields(targetClass);
            for (Field field : fields) {
                if (ff != null && !ff.matches(field)) {
                    continue;
                }
                try {
                    fc.doWith(field);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);
    }

    /**
     * This variant retrieves {@link Class#getDeclaredFields()} from a local
     * cache in order to avoid the JVM's SecurityManager check and defensive
     * array copying.
     * 
     * @param clazz
     *            the class to introspect
     * @return the cached array of fields
     * @see Class#getDeclaredFields()
     */
    private static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    /**
     * Given the source object and the destination, which must be the same class
     * or a subclass, copy all fields, including inherited fields. Designed to
     * work on objects with public no-arg constructors.
     */
    public static void shallowCopyFieldState(final Object src, final Object dest) {
        if (src == null) {
            throw new IllegalArgumentException("Source for field copy cannot be null");
        }
        if (dest == null) {
            throw new IllegalArgumentException("Destination for field copy cannot be null");
        }
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        }
        doWithFields(src.getClass(), new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (field.isAccessible()) {
                    Object srcValue = field.get(src);
                    field.set(dest, srcValue);
                }
            }
        }, FieldFilter.COPYABLE_FIELDS);
    }

    /**
     * Callback interface invoked on each field in the hierarchy.
     */
    public interface FieldCallback {

        /**
         * Perform an operation using the given field.
         * 
         * @param field
         *            the field to operate on
         */
        abstract void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;

    }

    /**
     * Action to take on each method.
     */
    public interface MethodCallback {

        /**
         * Perform an operation using the given method.
         * 
         * @param method
         *            the method to operate on
         */
        abstract void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;

    }

    /**
     * Callback optionally used to filter fields to be operated on by a field
     * callback.
     */
    public enum FieldFilter {

        /**
         * Pre-built FieldFilter that matches all non-static, non-final fields.
         */
        COPYABLE_FIELDS() {
            @Override
            public boolean matches(Field field) {
                return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
            }
        };

        /**
         * Determine whether the given field matches.
         * 
         * @param field
         *            the field to check
         */
        public abstract boolean matches(Field field);

    }

    /**
     * Callback optionally used to filter methods to be operated on by a method
     * callback.
     */
    public enum MethodFilter {

        /**
         * Pre-built MethodFilter that matches all non-bridge methods.
         */
        NON_BRIDGED_METHODS() {
            @Override
            public boolean matches(Method method) {
                return !method.isBridge();
            }
        },
        /**
         * Pre-built MethodFilter that matches all non-bridge methods which are
         * not declared on {@code java.lang.Object}.
         */
        USER_DECLARED_METHODS() {
            @Override
            public boolean matches(Method method) {
                return (!method.isBridge() && method.getDeclaringClass() != Object.class);
            }
        };

        /**
         * Determine whether the given method matches.
         * 
         * @param method
         *            the method to check
         */
        public abstract boolean matches(Method method);

    }
}
