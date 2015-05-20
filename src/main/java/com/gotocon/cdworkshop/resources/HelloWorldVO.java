package com.gotocon.cdworkshop.resources;

class HelloWorldVO {
    private String text;

    public HelloWorldVO() {
        this.text = "Hello, World!";
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object other) {
        return org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this);
    }
}

