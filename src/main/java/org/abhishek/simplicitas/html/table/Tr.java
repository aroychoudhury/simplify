/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML element table row &lt;tr&gt; defines a row of cells in a table.
 * Those can be a mix of &lt;td&gt; and &lt;th&gt; elements.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Tr extends HTMLTag<Tr> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Tr initialize() {
        this.tagName = TagNameEnum.TR;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Tr.class.getName());
        return this;
    }
}
