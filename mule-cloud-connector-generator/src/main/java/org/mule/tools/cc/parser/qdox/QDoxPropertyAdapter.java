package org.mule.tools.cc.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
import org.mule.tools.cc.model.JavaProperty;

public class QDoxPropertyAdapter implements JavaProperty {
    private BeanProperty javaProperty;

    protected QDoxPropertyAdapter( BeanProperty javaProperty ) {
        this.javaProperty = javaProperty;
    }

    public String getName() {
        return this.javaProperty.getName();
    }

    public String getType() {
        return this.javaProperty.getType().getValue();
    }

    public String getDescription() {
        return this.javaProperty.getMutator().getComment();
    }
}
