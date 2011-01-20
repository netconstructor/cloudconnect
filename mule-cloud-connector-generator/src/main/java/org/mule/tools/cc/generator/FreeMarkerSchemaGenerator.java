package org.mule.tools.cc.generator;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerSchemaGenerator extends AbstractGenerator {

    private static final String TEMPLATES_DIRECTORY = "/org/mule/tools/cc/generator/templates";
    private static final String NAMESPACE_HANDLER_TEMPLATE = "schema.ftl";

    private String namespaceIdentifierSuffix;
    private String schemaVersion;

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
            throw new RuntimeException("Unable to generate xml schema", e);
        }
        out.flush();
    }

    private Map createModel() {
        Map root = new HashMap();
        root.put("namespaceIdentifierSuffix", namespaceIdentifierSuffix);
        root.put("schemaVersion", schemaVersion);
        root.put("hasSetters", JavaClassUtils.collectSetters(javaClass).size() > 0);
        root.put("setters", JavaClassUtils.collectSetters(javaClass));
        root.put("operations", JavaClassUtils.collectOperations(javaClass));
        return root;
    }

    private Configuration createConfiguration() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(getClass(), TEMPLATES_DIRECTORY);
        cfg.setSharedVariable("splitCamelCase", new SplitCamelCaseDirective());
        cfg.setSharedVariable("uncapitalize", new UncapitalizeDirective());
        cfg.setSharedVariable("typeMap", new TypeMapDirective());

        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        BeansWrapper bw = (BeansWrapper) cfg.getObjectWrapper();
        bw.setSimpleMapWrapper(true);
        bw.setExposureLevel(BeansWrapper.EXPOSE_ALL);
        cfg.setObjectWrapper(bw);
        return cfg;
    }

    public String getNamespaceIdentifierSuffix() {
        return namespaceIdentifierSuffix;
    }

    public void setNamespaceIdentifierSuffix(String namespaceIdentifierSuffix) {
        this.namespaceIdentifierSuffix = namespaceIdentifierSuffix;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
}
