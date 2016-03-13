/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML &lt;caption&gt; Element (or HTML Table Caption Element) represents
 * the title of a table. Though it is always the first descendant of a
 * &lt;table&gt;, its styling, using CSS, may place it elsewhere, relative to
 * the table.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Caption extends HTMLTag<Caption> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Caption initialize() {
        this.tagName = TagNameEnum.CAPTION;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Caption.class.getName());
        return this;
    }
}
