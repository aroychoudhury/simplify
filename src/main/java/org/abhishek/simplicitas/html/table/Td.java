/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The Table cell HTML element (&lt;td&gt;) defines a cell of a table that
 * contains data. It participates in the table model.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Td extends HTMLTag<Td> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Td initialize() {
        this.tagName = TagNameEnum.TD;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Td.class.getName());
        return this;
    }
}
