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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Simple utility class for handling exceptions.
 * <p>
 * Base class exposing utility methods for handling exceptions and converting
 * them to Runtime based expcetions.
 *
 * @author abhishek
 * @since 1.0
 */
public class ExceptionUtils {
    /**
     * Handle the given reflection exception. Should only be called if no
     * checked exception is expected to be thrown by the target method.
     * <p>
     * Throws the underlying RuntimeException or Error in case of an
     * InvocationTargetException with such a root cause. Throws an
     * IllegalStateException with an appropriate message else.
     * 
     * @param ex
     *            the reflection exception to handle
     */
    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage(), ex);
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage(), ex);
        }
        if (ex instanceof InvocationTargetException) {
            throw new IllegalStateException("Could not invoke: " + ex.getMessage(), ex);
        }
        rethrowRuntimeException(ex);
    }

    /**
     * Handle the given IO exception. Should only be called if no checked
     * exception is expected to be thrown by the target method.
     * <p>
     * Throws the underlying {@link RuntimeException}. Throws an
     * {@link IllegalStateException} with an appropriate message else.
     * 
     * @param ex
     *            the reflection exception to handle
     */
    public static void handleIOException(Exception ex) {
        if (ex instanceof IOException) {
            throw new IllegalStateException("IO Exception: " + ex.getMessage(), ex);
        }
        if (ex instanceof FileNotFoundException) {
            throw new IllegalStateException("File Not Found: " + ex.getMessage(), ex);
        }
        rethrowRuntimeException(ex);
    }

    /**
     * Handle the given checked exception. Should only be called if no checked
     * exception is expected to be thrown by the target method.
     * <p>
     * Throws the underlying {@link RuntimeException}. Throws an
     * {@link IllegalStateException} with an appropriate message else.
     * 
     * @param ex
     *            the reflection exception to handle
     */
    public static void handleCheckedException(Exception ex) {
        rethrowRuntimeException(ex);
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}. Should
     * only be called if no checked exception is expected to be thrown by the
     * target method.
     * <p>
     * Rethrows the underlying exception cast to an {@link RuntimeException} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link IllegalStateException}.
     * 
     * @param ex
     *            the exception to rethrow
     * @throws RuntimeException
     *             the rethrown exception
     */
    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new IllegalStateException(ex.getMessage(), ex);
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}. Should
     * only be called if no checked exception is expected to be thrown by the
     * target method.
     * <p>
     * Rethrows the underlying exception cast to an {@link Exception} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link IllegalStateException}.
     * 
     * @param ex
     *            the exception to rethrow
     * @throws Exception
     *             the rethrown exception (in case of a checked exception)
     */
    public static void rethrowException(Throwable ex) throws Exception {
        if (ex instanceof Exception) {
            throw (Exception) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new IllegalStateException(ex.getMessage(), ex);
    }

    /**
     * Gets the stack trace from a Throwable as a String.
     *
     * The result of this method vary by JDK version as this method uses
     * {@link java.lang.Throwable#printStackTrace(java.io.PrintWriter)}. On
     * JDK1.3 and earlier, the cause exception will not be shown unless the
     * specified throwable alters printStackTrace.
     *
     * @param throwable
     *            the {@link java.lang.Throwable} to be examined
     * @return the stack trace as generated by the exception's
     *         <code>printStackTrace(PrintWriter)</code> method
     * @see java.lang.Throwable
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString().replaceAll("\\t", "");
    }
}
