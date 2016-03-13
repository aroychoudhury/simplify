/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.builder;

import org.abhishek.simplicitas.html.base.HTMLTag;

/**
 * This class defines methods on how to generate the content for the
 * {@link HTMLTag}.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface TagBuilder {

    /**
     * Method to process the actual content for the {@link HTMLTag} in question.
     * 
     * @return constructed content for the {@link HTMLTag}
     * @throws IllegalConstructException
     * @author abhishek
     * @since 1.0
     */
    abstract String construct(HTMLTag<?> tag) throws IllegalStateException;

}
