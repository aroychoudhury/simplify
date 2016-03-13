/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.base;

import java.util.Arrays;
import java.util.List;

import org.abhishek.simplicitas.html.builder.TagBuilder;
import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.enums.TagNameEnum;
import org.abhishek.simplicitas.util.collection.AttributesMap;

/**
 * Base class for all HTML tags.
 * 
 * @author abhishek
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class HTMLTag<T extends HTMLTag> {
    /* Instance variables */
    private boolean                       empty        = true;
    private String                        tagContent   = null;
    private String                        innerContent = null;
    protected Class<? extends TagBuilder> builderClazz = null;
    protected TagNameEnum                     tagName      = null;
    protected AttributesMap               attributes   = null;
    protected List<? extends HTMLTag>     children     = null;

    /**
     * Constructs the {@link HTMLTag} component. Invokes intialization steps and
     * mandatory checks for the construction of {@link HTMLTag}.
     * 
     * @author abhishek
     * @since 1.0
     */
    public HTMLTag() {
        this.initialize();
    }

    /* Overridable Methods */
    /**
     * Method responsible for initializing the base properties of the class.
     * 
     * @return the current instance of the {@link HTMLTag} object
     * @throws IllegalStateException
     * @author abhishek
     * @since 1.0
     */
    public abstract T initialize() throws IllegalStateException;

    /**
     * Returns the main content of the Tag. This could be an HTML fragment or a
     * simple value encapsulated by this Tag element.
     * 
     * A {@link TagBuilder} is used to create the actual content of the Tag.
     * 
     * @param constructor
     * @return the current instance {@link HTMLTag}
     * @throws InvalidContentException
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public T build(TagBuilder constructor) throws IllegalStateException {
        this.setContent(constructor.construct(this));
        return (T) this;
    }

    /* Public Final API Methods */
    /**
     * A flag to indicate if the content of this tag is empty.
     * 
     * @return {@code true} if the {@link HTMLTag} is marked to be empty, or
     *         {@code false} if otherwise
     * @author abhishek
     * @since 1.0
     */
    public final boolean isEmpty() {
        return this.empty;
    }

    /**
     * Returns the main content of the Tag. This could be an HTML fragment or a
     * simple value encapsulated by this Tag element.
     * 
     * @return the {@link HTMLTag} content; returns "" if {@code empty} is set
     *         to {@code true}
     * @author abhishek
     * @since 1.0
     */
    public final String content() {
        if ((null == this.tagContent || "".equals(this.tagContent)) && null != this.builderClazz) {
            try {
                this.build(this.builderClazz.newInstance());
            } catch (InstantiationException e) {
                throw new IllegalStateException("Builder could not be initialized", e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Builder Access could not be done", e);
            }
        } else if (null == this.tagContent && !"".equals(this.tagContent) && null == this.builderClazz) {
            throw new IllegalStateException("Builder not set");
        }
        return this.tagContent;
    }

    /**
     * @return the {@link HTMLTag} inner content; returns "" if {@code empty} is set
     *         to {@code true}
     * @author abhishek
     * @since  1.0
     */
    public final String innerContent() {
        if (this.empty) {
            return "";
        }
        return this.innerContent;
    }

    /**
     * Returns the name of this {@link HTMLTag}.
     * 
     * @return the name of this {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    public final TagNameEnum getTagName() {
        return this.tagName;
    }

    /**
     * Returns the class of the {@link TagBuilder}.
     * 
     * @return the builder class of this {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    public final Class<? extends TagBuilder> getBuilderClazz() {
        return this.builderClazz;
    }

    /**
     * Sets the content of this tag; it can be inner HTML or inner Text.
     * 
     * @param content
     *            the content to be set
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final T setContent(String content) {
        if (null == content || "".equals(content)) {
            return (T) this;
        }
        this.tagContent = content.trim();
        return (T) this;
    }

    /**
     * Sets the inner content of this tag; it can be inner HTML or inner Text.
     * 
     * @param content the content to be set
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since  1.0
     */
    @SuppressWarnings("unchecked")
    public final T setInnerContent(String content) {
        if (null == content || "".equals(content)) {
            return (T) this;
        }
        this.empty = false;
        this.innerContent = content.trim();
        return (T) this;
    }

    /**
     * Appends a tag before the current Content of this tag.
     *
     * @param tag
     *            the tag to append
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final <H extends HTMLTag> T appendBefore(H tag) {
        if (tag != null) {
            return this.appendBefore(tag.validateTag().content());
        }

        return (T) this;
    }

    /**
     * Appends the input text before the Content of this tag.
     *
     * @param content
     *            the value to append to the body
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final T appendBefore(String content) {
        if (null == content || "".equals(content)) {
            return (T) this;
        }

        this.empty = false;
        if ("".equals(this.innerContent)) {
            this.innerContent = content.trim();
        } else {
            this.innerContent = content.trim() + this.innerContent;
        }

        this.validateTag();

        return (T) this;
    }

    /**
     * Appends a tag after the current Content of this tag.
     *
     * @param tag
     *            the tag to append
     * @return the current instance of the {@link HTMLTag} object
     * @throws InvalidContentException
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final <H extends HTMLTag> T appendAfter(H tag) {
        if (tag != null) {
            return this.appendAfter(tag.validateTag().content());
        }

        return (T) this;
    }

    /**
     * Appends the input text after the Content of this tag.
     *
     * @param content
     *            the value to append to the body
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final T appendAfter(String content) {
        if (null == content || "".equals(content)) {
            return (T) this;
        }

        this.empty = false;
        if ("".equals(this.innerContent)) {
            this.innerContent = content;
        } else {
            this.innerContent += content;
        }

        this.validateTag();

        return (T) this;
    }

    /**
     * Checks whether the attribute key is present for this tag.
     * 
     * @param key
     *            the attribute key
     * @return {@code true} if the attribute has been defined for this
     *         {@link HTMLTag}, or {@code false} if otherwise
     * @author abhishek
     * @since 1.0
     */
    public final boolean hasAttribute(TagAttributeEnum key) {
        if (null != this.attributes)
            return this.attributes.containsKey(key);
        return false;
    }

    /**
     * Checks whether any attribute has been defined by the Tag.
     * 
     * @return {@code true} if at least one attribute has been defined for this
     *         {@link HTMLTag}, or {@code false} if otherwise
     * @author abhishek
     * @since 1.0
     */
    public final boolean hasAttributes() {
        if (null != this.attributes)
            return this.attributes.size() != 0;
        return false;
    }

    /**
     * Gets a certain attribute value by key.
     *
     * @param key
     *            the attribute key
     * @return the attribute value
     * @author abhishek
     * @since 1.0
     */
    public final String getAttribute(TagAttributeEnum key) {
        if (null != this.attributes)
            return this.attributes.get(key);
        return "";
    }

    /**
     * Adds the attribute key-value pair to the existing set of attributes
     * already defined for this Tag.
     * 
     * @param key
     *            the attribute key
     * @param value
     *            the attribute value
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final T setAttribute(TagAttributeEnum key, String value) {
        this.initializeAttributes();
        this.attributes.put(key, value);
        return (T) this;
    }

    /**
     * Removes the attribute by the key.
     * 
     * @param key
     *            the attribute key
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final T removeAttribute(TagAttributeEnum key) {
        if (null != this.attributes)
            this.attributes.remove(key);
        return (T) this;
    }

    /**
     * Returns a copy of all the attributes on the Tag. This method does not
     * return the inner instance, instead returns a copy of the values already
     * present.
     * 
     * @return map of attributes key-value pair
     * @author abhishek
     * @since 1.0
     */
    public final AttributesMap attributesAsMap() {
        return new AttributesMap(this.attributes);
    }

    public final String attributes() {
        return this.attributes.toAttributesString();
    }

    public final List<? extends HTMLTag> children() {
        return this.children;
    }

    public final boolean hasChildren() {
        return (null != this.children && !this.children.isEmpty());
    }

    /**
     * @param tags
     * @author abhishek
     * @since  1.0
     */
    @SuppressWarnings("unchecked")
    public final T registerChildren(HTMLTag... tags) {
        if (null != tags && 0 != tags.length) {
            this.children = Arrays.asList(tags);
        }
        return (T) this;
    }

    /*
     * Protected Final Methods: Utility Methods which allows direct interaction
     * with the class properties.
     */
    protected final void initializeAttributes() {
        if (null == this.attributes)
            this.attributes = new AttributesMap();
    }

    /**
     * Validates the contents of the Tag at the time of Tag creation. This
     * method has not been declared as final and can be overridden by the
     * extending classes.
     *
     * @return the current instance of the {@link HTMLTag} object
     * @author abhishek
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    protected T validateTag() {
        if (null == this.tagName) {
            throw new IllegalArgumentException("HTML Tag must have a Name defined");
        }
        if (null == this.builderClazz) {
            throw new IllegalArgumentException("HTML Tag must have a Builder Class defined");
        }

        // If not empty check if the tagContent is valid; if empty, set the
        // content to "".
        if (!this.empty && (null == this.innerContent || "".equals(this.innerContent.trim()))) {
            throw new IllegalStateException("HTML Tag must contain Inner Content when it is NOT Empty");
        } else {
            this.innerContent = "";
        }

        return (T) this;
    }

}
