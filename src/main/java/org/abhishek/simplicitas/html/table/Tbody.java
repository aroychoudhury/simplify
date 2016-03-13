/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.impl.BaseTagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;

/**
 * The HTML Table Body Element (&lt;tbody&gt;) defines one or more &lt;tr&gt;
 * element data-rows to be the body of its parent &lt;table&gt; element (as long
 * as no &lt;tr&gt; elements are immediate children of that table element.) In
 * conjunction with a preceding &lt;thead&gt; and/or &lt;tfoot&gt; element,
 * &lt;tbody&gt; provides additional semantic information for devices such as
 * printers and displays.
 * 
 * Of the parent table's child elements, &lt;tbody&gt; represents the content
 * which, when longer than a page, will most likely differ for each page
 * printed; while the content of &lt;thead&gt; and &lt;tfoot&gt; will be the
 * same or similar for each page printed. For displays, &lt;tbody&gt; will
 * enable separate scrolling of the &lt;thead&gt;, &lt;tfoot&gt;, and
 * &lt;caption&gt; elements of the same parent &lt;table&gt; element. Note that
 * unlike the &lt;thead&gt;, &lt;tfoot&gt;, and &lt;caption&gt; elements
 * however, multiple &lt;tbody&gt; elements are permitted (if consecutive),
 * allowing the data-rows in long tables to be divided into different sections,
 * each separately formatted as needed.
 * 
 * @author abhishek
 * @since 1.0
 */
public class Tbody extends HTMLTag<Tbody> {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.base.HTMLTag#initialize()
     */
    @Override
    public Tbody initialize() {
        this.tagName = TagNameEnum.TBODY;
        this.builderClazz = BaseTagBuilder.class;
        this.setAttribute(TagAttributeEnum.SOURCE_CLASS, Tbody.class.getName());
        return this;
    }
}
