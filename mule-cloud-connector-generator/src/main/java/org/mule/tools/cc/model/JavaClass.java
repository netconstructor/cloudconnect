package org.mule.tools.cc.model;

import java.util.List;

public interface JavaClass {
    public String getName();
    public String getPackage();
    public String getDescription();
    public List<JavaProperty> getProperties();
    public boolean hasProperties();
    public List<JavaMethod> getOperations();
}
