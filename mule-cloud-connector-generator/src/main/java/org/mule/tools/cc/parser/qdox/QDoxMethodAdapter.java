package org.mule.tools.cc.parser.qdox;

import org.mule.tools.cc.model.JavaMethod;
import org.mule.tools.cc.model.JavaParameter;

import java.util.List;

public class QDoxMethodAdapter implements JavaMethod
{
    private com.thoughtworks.qdox.model.JavaMethod javaMethod;
    private List<JavaParameter> parameters;


    public QDoxMethodAdapter(com.thoughtworks.qdox.model.JavaMethod javaMethod) {
        this.javaMethod = javaMethod;
    }

    public String getName() {
        return this.javaMethod.getName();
    }

    public String getDescription() {
        return javaMethod.getComment();
    }

    public List<JavaParameter> getParameters() {
        if( parameters == null ) {
            buildParameterCollection();
        }

        return this.parameters;
    }

    public void buildParameterCollection() {
        com.thoughtworks.qdox.model.JavaParameter[] parameters = javaMethod.getParameters();
        for( int i = 0; i < parameters.length; i++ ) {
            this.parameters.add( new QDoxParameterAdapter(parameters[i]));
        }
    }
}
