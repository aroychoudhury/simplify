/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.display.domain;

/**
 * TODO
 * 
 * @author abhishek
 * @since 1.0
 */
public class DisplayBase {
    private String name;
    private Object value;
    private String dataType;
    private String[] paramType;

    /**
     * @param name
     *            the name of the field to set
     * @param value
     *            the value of the field to set
     * @param dataType
     *            the data type for the field
     * @author abhishek
     * @since 1.0
     */
    public DisplayBase(String name, Object value, String dataType) {
        super();
        this.name = name;
        this.value = value;
        this.dataType = dataType;
    }

    /**
     * @param name
     *            the name of the field to set
     * @param value
     *            the value of the field to set
     * @param dataType
     *            the data type for the field
     * @param paramType
     *            if the field follows generics add the parameterized
     *            information; this is defined as an array as the number of
     *            parameters depends mainly on the implementation.
     * @author abhishek
     * @since 1.0
     */
    public DisplayBase(String name, Object value, String dataType, String[] paramType) {
        super();
        this.name = name;
        this.value = value;
        this.dataType = dataType;
        this.paramType = paramType;
    }



    /**
     * @return the name
     * @since 1.0
     * @see String
     */
    public String getName() {
        if (null == name) {
            return "";
        }
        return name;
    }

    /**
     * @return the value
     * @since 1.0
     * @see Object
     */
    public Object getValue() {
        if (null == value) {
            return new Object();
        }
        return value;
    }

    /**
     * @return the dataType
     * @since 1.0
     * @see String
     */
    public String getDataType() {
        if (null == dataType) {
            return "";
        }
        return dataType;
    }

    /**
     * @return the paramType
     * @since 1.0
     * @see String
     */
    public String[] getParamType() {
        if (null == paramType) {
            return new String[0];
        }
        return paramType;
    }

    /**
     * @return
     * @author abhishek
     * @since  1.0
     */
    public boolean isArray() {
        if (null == dataType) {
            return false;
        }
        return (-1 != dataType.indexOf("[]"));
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DisplayBase [ " + getName() + " : " + getValue().toString() + " : " + getDataType() + " ( " + getParamType().length + " ) ]";
    }
}
