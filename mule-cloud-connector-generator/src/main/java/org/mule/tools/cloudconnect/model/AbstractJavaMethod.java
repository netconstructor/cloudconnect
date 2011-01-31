package org.mule.tools.cloudconnect.model;

public abstract class AbstractJavaMethod implements JavaMethod
{

    private static final String OBJECT_CLASS_NAME = "Object";

    public boolean isOperation()
    {
        for (JavaAnnotation annotation : getAnnotations())
        {
            if (annotation.getType().equals("org.mule.tools.cloudconnect.annotations.Operation"))
            {
                if (isPublic()
                    && !isStatic()
                    && !isPropertyAccessor()
                    && !isPropertyMutator()
                    && !isConstructor()
                    && !OBJECT_CLASS_NAME.equals(getParentClass().getName()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }
}
