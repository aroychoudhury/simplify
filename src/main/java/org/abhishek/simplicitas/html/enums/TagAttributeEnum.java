/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.enums;

/**
 * Defines all possible attributes supported by the Tag.
 * 
 * @author abhishek
 * @since 1.0
 */
public enum TagAttributeEnum {
    // common
    ID("id", 0),
    ALT("alt", 1),
    SRC("src", 2),
    CLASS("class", 3),
    STYLE("style", 4),
    SOURCE_CLASS("src-class", 5),

    // th
    COLSPAN("colspan", 6),
    ROWSPAN("rowspan", 7);

    private String value;
    private int position;

    private TagAttributeEnum(String value, int position) {
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return this.value;
    }

    public int getPosition() {
        return this.position;
    }
}
