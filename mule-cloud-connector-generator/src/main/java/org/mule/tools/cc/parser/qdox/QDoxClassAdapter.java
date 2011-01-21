package org.mule.tools.cc.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import org.mule.tools.cc.model.JavaMethod;
import org.mule.tools.cc.model.JavaProperty;

import java.util.ArrayList;
import java.util.List;

public class QDoxClassAdapter implements org.mule.tools.cc.model.JavaClass {
    private JavaClass javaClass;
    private List<JavaProperty> properties;
    private List<JavaMethod> operations;

    protected QDoxClassAdapter(JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    public String getName() {
        return javaClass.getName();
    }

    public String getPackage() {
        return javaClass.getPackage();
    }

    public String getDescription() {
        return javaClass.getComment();
    }

    public List<JavaProperty> getProperties() {
        if (this.properties == null) {
            buildPropertyCollection();
        }

        return this.properties;
    }

    public boolean hasProperties() {
        return getProperties().size() > 0;
    }

    private void buildPropertyCollection() {
        this.properties = new ArrayList<JavaProperty>();

        BeanProperty[] properties = javaClass.getBeanProperties(true);
        for (int i = 0; i < properties.length; i++) {
            this.properties.add(new QDoxPropertyAdapter(properties[i]));
        }
    }

    public List<JavaMethod> getOperations() {
        if (this.operations == null) {
            buildOperationCollection();
        }

        return this.operations;
    }

    private void buildOperationCollection() {
        this.operations = new ArrayList<JavaMethod>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isPublic() && !methods[i].isStatic() && !methods[i].isPropertyAccessor() &&
                    !methods[i].isPropertyMutator() && !methods[i].isConstructor()) {
                this.operations.add(new QDoxMethodAdapter(methods[i]));
            }
        }

    }
}
