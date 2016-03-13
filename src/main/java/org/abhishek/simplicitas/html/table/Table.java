/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML Table Element (&lt;table&gt;) represents data in two dimensions or more.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Table extends HTMLTag<Table> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Table initialize() {
        this.tagName = TagNameEnum.TABLE;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Table.class.getName());
        return this;
    }
}
