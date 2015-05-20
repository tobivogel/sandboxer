package com.gotocon.cdworkshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WebsiteFragmentVO {

    private String author;
    private String comment;
    private String htmlPayload;

    @JsonCreator
    public WebsiteFragmentVO(
            @JsonProperty("author") String author,
            @JsonProperty("comment") String comment,
            @JsonProperty("htmlPayload") String htmlPayload) {
        this.author = author;
        this.comment = comment;
        this.htmlPayload = htmlPayload;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public String getHtmlPayload() {
        return htmlPayload;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
