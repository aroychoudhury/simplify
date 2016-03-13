/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.display.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates data in the format such that it can be used to display data as a
 * column.
 * 
 * @author abhishek
 * @since 1.0
 */
public class ColumnDisplay {
    private String name;
    private List<Object> values;
    private String dataType;
    private int order;
    private boolean sortable;
    private boolean filterable;

    /**
     * @param name
     *            the name of the column
     * @param dataType
     *            the datatype of the column
     * @author abhishek
     * @since 1.0
     */
    public ColumnDisplay(String name, String dataType) {
        super();
        this.name = name;
        this.dataType = dataType;
        this.order = 1;
        this.sortable = true;
        this.filterable = true;
    }

    /**
     * @param name
     *            the name of the column
     * @param dataType
     *            the datatype of the column
     * @param order
     *            the order of the column
     * @author abhishek
     * @since 1.0
     */
    public ColumnDisplay(String name, String dataType, int order) {
        super();
        this.name = name;
        this.dataType = dataType;
        this.order = order;
        this.sortable = true;
        this.filterable = true;
    }

    /**
     * @param name
     *            the name of the column
     * @param dataType
     *            the datatype of the column
     * @param values
     *            the values to be displayed in the column
     * @param order
     *            the order of the column
     * @param sortable
     *            indicator to enable sorting functionality on the column
     * @param filterable
     *            indicator to enable filtering functionality on the column
     * @author abhishek
     * @since 1.0
     */
    public ColumnDisplay(String name, List<Object> values, String dataType, int order, boolean sortable, boolean filterable) {
        super();
        this.name = name;
        this.values = values;
        this.dataType = dataType;
        this.order = order;
        this.sortable = sortable;
        this.filterable = filterable;
    }

    /**
     * @return the name
     * @since 1.0
     * @see String
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     * @since 1.0
     * @see String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the values
     * @since 1.0
     * @see List<Object>
     */
    public List<Object> getValues() {
        if (null == this.values) {
            return new ArrayList<Object>(1);
        }
        return values;
    }

    /**
     * @param values
     *            the values to set
     * @since 1.0
     * @see List<Object>
     */
    public void setValues(List<Object> values) {
        this.values = values;
    }

    /**
     * @param value
     *            the value to add
     * @author abhishek
     * @since 1.0
     */
    protected void addValue(Object value) {
        if (null == this.values) {
            this.values = new ArrayList<Object>();
        }
        this.values.add(value);
    }

    /**
     * @return the dataType
     * @since 1.0
     * @see String
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set
     * @since 1.0
     * @see String
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the order
     * @since 1.0
     * @see int
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     * @since 1.0
     * @see int
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the sortable
     * @since 1.0
     * @see boolean
     */
    public boolean isSortable() {
        return sortable;
    }

    /**
     * @param sortable
     *            the sortable to set
     * @since 1.0
     * @see boolean
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * @return the filterable
     * @since 1.0
     * @see boolean
     */
    public boolean isFilterable() {
        return filterable;
    }

    /**
     * @param filterable
     *            the filterable to set
     * @since 1.0
     * @see boolean
     */
    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Column [" + name + " : " + dataType + " ( " + (null != values ? values.size() : 0) + " | " + order + " ) ]";
    }
}
