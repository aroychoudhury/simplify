/* Copyright 2016 Roychoudhury, Abhishek */

package org.abhishek.simplicitas.html.builder.impl;

import java.util.List;

import org.abhishek.simplicitas.html.base.HTMLTag;
import org.abhishek.simplicitas.html.builder.TagBuilder;

/**
 * TODO
 * @author abhishek
 * @since  1.0
 */
public class BaseTagBuilder implements TagBuilder {

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.simplicitas.html.builder.TagBuilder#construct(org.abhishek.simplicitas.html.base.HTMLTag)
     */
    @Override
    public String construct(HTMLTag<?> tag) {
        StringBuilder tagBuilder = new StringBuilder();
        this.traverseChildren(tag, tagBuilder);
        return tagBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    private void traverseChildren(HTMLTag<?> tag, StringBuilder tagBuilder) {
        this.constructStart(tag, tagBuilder);
        tagBuilder.append(tag.innerContent());
        if (tag.hasChildren()) {
            List<? extends HTMLTag> children = tag.children();
            for (HTMLTag childTag : children) {
                this.traverseChildren(childTag, tagBuilder);
            }
        }
        this.constructEnd(tag, tagBuilder);
    }

    private StringBuilder constructStart(HTMLTag<?> tag, StringBuilder tagBuilder) {
        return tagBuilder
            .append('<')
            .append(tag.getTagName().getValue())
            .append(tag.attributes())
            .append('>');
    }

    private StringBuilder constructEnd(HTMLTag<?> tag, StringBuilder tagBuilder) {
        return tagBuilder
            .append("</")
            .append(tag.getTagName().getValue())
            .append('>');
    }
}
