package com.gotocon.cdworkshop.views;

import com.yammer.dropwizard.views.View;

public class FreemarkerView extends View {

    private Object templateData;

    public FreemarkerView(String templateName) {
        super(templateName + ".ftl");
    }

    public FreemarkerView(String templateName, Object templateData) {
        super(templateName + ".ftl");
        this.templateData = templateData;
    }

    public Object getTemplateData() {
        return templateData;
    }
}
