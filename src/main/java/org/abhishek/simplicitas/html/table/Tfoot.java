/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML Table Foot Element (&lt;tfoot&gt;) defines a set of rows summarizing
 * the columns of the table.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Tfoot extends HTMLTag<Tfoot> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Tfoot initialize() {
        this.tagName = TagNameEnum.TFOOT;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Tfoot.class.getName());
        return this;
    }
}
