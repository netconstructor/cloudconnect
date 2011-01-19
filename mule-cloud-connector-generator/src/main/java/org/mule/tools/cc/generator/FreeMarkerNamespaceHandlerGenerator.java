package org.mule.tools.cc.generator;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerNamespaceHandlerGenerator extends AbstractGenerator {

    private static final String TEMPLATES_DIRECTORY = "/org/mule/tools/cc/generator/templates";
    private static final String NAMESPACE_HANDLER_TEMPLATE = "namespacehandler.ftl";

    private String packageName;
    private String className;

    @Override
    public void generate(OutputStream output) throws IOException {
        Configuration cfg = createConfiguration();
        Template temp = cfg.getTemplate(NAMESPACE_HANDLER_TEMPLATE);
        Map model = createModel();

        write(output, temp, model);
    }

    private void write(OutputStream output, Template temp, Map model) throws IOException {
        Writer out = new OutputStreamWriter(output);
        try {
            temp.process(model, out);
        } catch (TemplateException e) {
            throw new RuntimeException("Unable to generate namespace handler template", e);
        }
        out.flush();
    }

    private Map createModel() {
        Map root = new HashMap();
        root.put("packageName", packageName);
        root.put("className", className);
        root.put("javaClass", javaClass);
        root.put("hasSetters", JavaClassUtils.collectSetters(javaClass).size() > 0);
        root.put("methods", JavaClassUtils.collectNonSettersAndGetters(javaClass));
        return root;
    }

    private Configuration createConfiguration() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(getClass(), TEMPLATES_DIRECTORY);
        cfg.setSharedVariable("splitCamelCase", new SplitCamelCaseDirective());

        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        BeansWrapper bw = (BeansWrapper) cfg.getObjectWrapper();
        bw.setSimpleMapWrapper(true);
        bw.setExposureLevel(BeansWrapper.EXPOSE_ALL);
        cfg.setObjectWrapper(bw);
        return cfg;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
