package org.mule.tools.cc.model;

import java.util.List;

public interface JavaMethod {
    public String getName();
    public String getDescription();
    public List<JavaParameter> getParameters();
}
