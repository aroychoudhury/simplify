/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.util.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.abhishek.simplicitas.html.enums.TagAttributeEnum;

/**
 * Simpler implementation of the Map Interface. Keeps the object light.
 * 
 * @author abhishek
 * @since 1.0
 */
public class AttributesMap implements Map<TagAttributeEnum, String> {
    private TagAttributeEnum[] TOTAL_ATTRIBUTES = TagAttributeEnum.values();
    private String[]       attributes       = null;
    private int            size             = 0;

    public AttributesMap() {
        this.clear();
    }

    public AttributesMap(AttributesMap attributesMap) {
        if (attributesMap == null || attributesMap.isEmpty()) {
            throw new IllegalArgumentException("NULL or EMPTY not supported");
        }
        for (TagAttributeEnum attribute : TOTAL_ATTRIBUTES) {
            this.put(attribute, attributesMap.get(attribute));
        }
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return (0 == this.size);
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        TagAttributeEnum attrKey = (TagAttributeEnum) key;
        String value = this.attributes[attrKey.getPosition()];
        if (null != value && !"".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        String attrValue = (String) value;
        for (String attribute : this.attributes) {
            if (attribute.equals(attrValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public String get(Object key) {
        TagAttributeEnum attrKey = (TagAttributeEnum) key;
        String value = this.attributes[attrKey.getPosition()];
        if (null == value) {
            return "";
        }
        return value;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public String put(TagAttributeEnum key, String value) {
        if (null == value || "".equals(value)) {
            return value;
        }
        String currValue = this.attributes[key.getPosition()];
        this.attributes[key.getPosition()] = value;
        this.size++;
        return currValue;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public String remove(Object key) {
        TagAttributeEnum attrKey = (TagAttributeEnum) key;
        String currValue = this.attributes[attrKey.getPosition()];
        this.attributes[attrKey.getPosition()] = null;
        this.size--;
        return currValue;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends TagAttributeEnum, ? extends String> m) {
        if (m == null || m.isEmpty()) {
            throw new IllegalArgumentException("NULL or EMPTY not supported");
        }
        Iterator<? extends TagAttributeEnum> iterator = m.keySet().iterator();
        while (iterator.hasNext()) {
            TagAttributeEnum tagAttribute = iterator.next();
            this.put(tagAttribute, m.get(tagAttribute));
        }
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#clear()
     */
    @Override
    public void clear() {
        this.attributes = new String[TOTAL_ATTRIBUTES.length];
        this.size = 0;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<TagAttributeEnum> keySet() {
        return new HashSet<TagAttributeEnum>(Arrays.asList(TagAttributeEnum.values()));
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#values()
     */
    @Override
    public Collection<String> values() {
        return Arrays.asList(this.attributes);
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<TagAttributeEnum, String>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder attrStr = new StringBuilder("");
        for (TagAttributeEnum attribute : TOTAL_ATTRIBUTES) {
            if (0 == attrStr.length()) {
                attrStr.append("AttributesMap[");
            } else {
                attrStr.append(", ");
            }
            attrStr.append(attribute.getValue()).append("=").append(this.attributes[attribute.getPosition()]);
        }
        return attrStr.append("]").toString();
    }

    public String toAttributesString() {
        StringBuilder attrStr = new StringBuilder(" ");
        for (TagAttributeEnum attribute : TOTAL_ATTRIBUTES) {
            String value = this.attributes[attribute.getPosition()];
            if (null != value && !"".equals(value.trim())) {
                attrStr
                    .append(attribute.getValue())
                    .append("=\"")
                    .append(this.attributes[attribute.getPosition()])
                    .append("\" ");
            }
        }
        return attrStr.toString();
    }
}
