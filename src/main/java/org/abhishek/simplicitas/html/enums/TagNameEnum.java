/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.enums;

/**
 * Defines all possible attributes supported by the Tag.
 * 
 * @author abhishek
 * @since 1.0
 */
public enum TagNameEnum {
    // common
    TABLE("table"),
    THEAD("thead"),
    TBODY("tbody"),
    TFOOT("tfoot"),
    TH("th"),
    TD("td"),
    TR("tr"),
    CAPTION("caption");

    private String value;

    private TagNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
