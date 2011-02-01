package org.mule.tools.cloudconnect.model;

import java.util.ArrayList;
import java.util.List;

public class JavaModel
{
    private List<JavaClass> classes;

    public JavaModel()
    {
        classes = new ArrayList<JavaClass>();
    }

    public void addClass(JavaClass clazz)
    {
        classes.add(clazz);
    }

    public List<JavaClass> getClasses()
    {
        return classes;
    }
}
