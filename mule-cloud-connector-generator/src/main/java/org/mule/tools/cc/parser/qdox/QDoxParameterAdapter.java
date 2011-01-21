package org.mule.tools.cc.parser.qdox;

import com.thoughtworks.qdox.model.DocletTag;
import org.mule.tools.cc.model.JavaParameter;

public class QDoxParameterAdapter implements JavaParameter {
    private com.thoughtworks.qdox.model.JavaParameter javaParameter;

    public QDoxParameterAdapter(com.thoughtworks.qdox.model.JavaParameter javaParameter) {
        this.javaParameter = javaParameter;
    }

    public String getName() {
        return javaParameter.getName();
    }

    public String getType() {
        return javaParameter.getType().getValue();
    }

    public String getDescription() {
        DocletTag[] doclets = javaParameter.getParentMethod().getTagsByName("param");

        for( int i = 0; i < doclets.length; i++ ) {
            if( doclets[i].getParameters()[0].equals(javaParameter.getName())) {
                return doclets[i].getValue().substring(doclets[i].getValue().indexOf(' '));
            }
        }

        return null;
    }
}
