/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML element table header cell &lt;th&gt; defines a cell as a header for
 * a group of cells of a table. The group of cells that the header refers to is
 * defined by the scope and headers attribute.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Th extends HTMLTag<Th> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Th initialize() {
        this.tagName = TagNameEnum.TH;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Th.class.getName());
        return this;
    }
}
