/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.AbstractJavaClass;
import org.mule.tools.cloudconnect.model.JavaAnnotation;
import org.mule.tools.cloudconnect.model.JavaField;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.model.JavaParameter;
import org.mule.tools.cloudconnect.model.JavaProperty;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.Type;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QDoxClassAdapter extends AbstractJavaClass
{

    private static final Type LIST_TYPE = new Type("java.util.List");
    private static final Type MAP_TYPE = new Type("java.util.Map");
    private static final String CLASS_PROPERTY_NAME = "class";
    private JavaClass javaClass;
    private WeakReference<JavaModel> parentModel;
    private List<JavaProperty> properties;
    private List<JavaMethod> methods;
    private Set<JavaType> enums;
    private Set<JavaType> xmlTypes;
    private List<JavaField> fields;
    private List<org.mule.tools.cloudconnect.model.JavaClass> derived;
    private List<JavaAnnotation> annotations;

    protected QDoxClassAdapter(JavaClass javaClass, JavaModel parentModel)
    {
        this.javaClass = javaClass;
        this.parentModel = new WeakReference<JavaModel>(parentModel);

        buildPropertyCollection();
        buildMethodCollection();
        buildEnumCollection();
    }

    public String getName()
    {
        return javaClass.getName();
    }

    public String getElementName()
    {
        return getName();
    }

    public String getPackage()
    {
        return javaClass.getPackage().getName();
    }

    public String getDescription()
    {
        return javaClass.getComment();
    }

    public List<JavaProperty> getProperties()
    {
        if (this.properties == null)
        {
            buildPropertyCollection();
        }

        return this.properties;
    }

    public boolean hasProperties()
    {
        return getProperties().size() > 0;
    }

    private void buildPropertyCollection()
    {
        this.properties = new ArrayList<JavaProperty>();

        BeanProperty[] properties = javaClass.getBeanProperties(true);
        for (int i = 0; i < properties.length; i++)
        {
            if (isValidProperty(properties[i]))
            {
                com.thoughtworks.qdox.model.JavaField field = javaClass.getFieldByName(properties[i].getName());
                this.properties.add(new QDoxPropertyAdapter(properties[i], field));
            }
        }
    }

    private boolean isValidProperty(BeanProperty property)
    {
        return !CLASS_PROPERTY_NAME.equals(property.getName());
    }

    public List<JavaMethod> getMethods()
    {
        if (this.methods == null)
        {
            buildMethodCollection();
        }

        return this.methods;
    }

    public Set<JavaType> getEnums()
    {
        if (this.enums == null)
        {
            buildEnumCollection();
        }

        return this.enums;
    }

    public Set<JavaType> getXmlTypes()
    {
        if (this.xmlTypes == null)
        {
            buildXmlTypeCollection();
        }

        return this.xmlTypes;
    }

    public void buildAnnotationCollection()
    {
        this.annotations = new ArrayList<JavaAnnotation>();

        Annotation[] annotations = javaClass.getAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            this.annotations.add(new QDoxAnnotationAdapter(annotations[i]));
        }
    }

    public void buildFieldCollection()
    {
        this.fields = new ArrayList<JavaField>();

        com.thoughtworks.qdox.model.JavaField[] fields = javaClass.getFields();
        for (int i = 0; i < fields.length; i++)
        {
            this.fields.add(new QDoxFieldAdapter(fields[i]));
        }
    }


    public List<JavaAnnotation> getAnnotations()
    {
        if (annotations == null)
        {
            buildAnnotationCollection();
        }

        return annotations;
    }

    public List<JavaField> getFields()
    {
        if (fields == null)
        {
            buildFieldCollection();
        }

        return fields;
    }

    public JavaModel getParentModel()
    {
        return parentModel.get();
    }

    public String getFullyQualifiedName()
    {
        return javaClass.getFullyQualifiedName();
    }

    private void buildMethodCollection()
    {
        this.methods = new ArrayList<JavaMethod>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            this.methods.add(new QDoxMethodAdapter(methods[i], this));
        }

    }

    private void buildXmlTypeCollection()
    {
        this.xmlTypes = new HashSet<JavaType>();

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            com.thoughtworks.qdox.model.JavaParameter[] parameters = methods[i].getParameters();
            for (int j = 0; j < parameters.length; j++)
            {
                if (new QDoxTypeAdapter(parameters[j].getType()).getJavaClass().isXmlType())
                {
                    this.xmlTypes.add(new QDoxTypeAdapter(parameters[j].getType()));
                }
                else if (parameters[j].getType().isA(LIST_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]).getJavaClass().isXmlType())
                {
                    this.xmlTypes.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]));
                }
                else if (parameters[j].getType().isA(MAP_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]).getJavaClass().isXmlType())
                {
                    this.xmlTypes.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]));
                }
                else if (parameters[j].getType().isA(MAP_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]).getJavaClass().isXmlType())
                {
                    this.xmlTypes.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[1]));
                }

            }
        }
    }

    private void buildEnumCollection()
    {
        this.enums = new HashSet<JavaType>();

        BeanProperty[] properties = javaClass.getBeanProperties(true);
        for (int i = 0; i < properties.length; i++)
        {
            if (isValidProperty(properties[i]) &&
                properties[i].getType().getJavaClass().isEnum())
            {
                this.enums.add(new QDoxTypeAdapter(properties[i].getType()));
            }
        }

        com.thoughtworks.qdox.model.JavaMethod[] methods = javaClass.getMethods(true);
        for (int i = 0; i < methods.length; i++)
        {
            com.thoughtworks.qdox.model.JavaParameter[] parameters = methods[i].getParameters();
            for (int j = 0; j < parameters.length; j++)
            {
                if (parameters[j].getType().getJavaClass().isEnum())
                {
                    this.enums.add(new QDoxTypeAdapter(parameters[j].getType()));
                }
                else if (parameters[j].getType().isA(LIST_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         parameters[j].getType().getActualTypeArguments()[0].getJavaClass().isEnum())
                {
                    this.enums.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]));
                }
                else if (parameters[j].getType().isA(MAP_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         parameters[j].getType().getActualTypeArguments()[0].getJavaClass().isEnum())
                {
                    this.enums.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[0]));
                }
                else if (parameters[j].getType().isA(MAP_TYPE) &&
                         parameters[j].getType().getActualTypeArguments() != null &&
                         parameters[j].getType().getActualTypeArguments()[1].getJavaClass().isEnum())
                {
                    this.enums.add(new QDoxTypeAdapter(parameters[j].getType().getActualTypeArguments()[1]));
                }
            }
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 1;
        hash = hash * 31 + getName().hashCode();
        hash = hash * 31 + getPackage().hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof QDoxClassAdapter &&
                ((QDoxClassAdapter) o).getName().equals(getName()) &&
                ((QDoxClassAdapter) o).getPackage().equals(getPackage()));
    }

    public org.mule.tools.cloudconnect.model.JavaClass getSuperClass()
    {
        return new QDoxClassAdapter(this.javaClass.getSuperJavaClass(), this.parentModel.get());
    }

    public boolean isInterface()
    {
        return this.javaClass.isInterface();
    }

    public boolean isInnerClass()
    {
        return this.javaClass.isInner();
    }

    public boolean isFinal()
    {
        return this.javaClass.isFinal();
    }

    public boolean isAbstract()
    {
        return this.javaClass.isAbstract();
    }

    public boolean isEnum()
    {
        return this.javaClass.isEnum();
    }

    public void buildDerivedCollection()
    {
        this.derived = new ArrayList<org.mule.tools.cloudconnect.model.JavaClass>();

        JavaClass[] derived = this.javaClass.getDerivedClasses();
        for (int i = 0; i < derived.length; i++)
        {
            this.derived.add(new QDoxClassAdapter(derived[i], parentModel.get()));
        }
    }

    public List<org.mule.tools.cloudconnect.model.JavaClass> getDerivedClasses()
    {
        if (derived != null)
        {
            buildDerivedCollection();
        }

        return derived;
    }

    public boolean hasDefaultConstructor()
    {
        for (JavaMethod method : this.methods)
        {
            if (method.isConstructor() &&
                !method.hasParameters() &&
                method.isPublic())
            {
                return true;
            }
        }

        return false;
    }

    public JavaMethod getMethodBySignature(String name, List<JavaParameter> parameters)
    {
        Type[] types = new Type[parameters.size()];
        int i = 0;
        for (JavaParameter parameter : parameters)
        {
            types[i] = ((QDoxTypeAdapter) parameter.getType()).javaType;
            i++;
        }

        com.thoughtworks.qdox.model.JavaMethod method = this.javaClass.getMethodBySignature(name, types);

        if (method != null)
        {
            return new QDoxMethodAdapter(this.javaClass.getMethodBySignature(name, types), this);
        }

        return null;
    }
}
