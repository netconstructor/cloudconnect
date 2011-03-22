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

package org.mule.tools.cloudconnect.model.jaxb;

import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaField;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaType;

import com.sun.tools.internal.xjc.api.ErrorListener;
import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.internal.bind.v2.model.core.ArrayInfo;
import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import com.sun.xml.internal.bind.v2.model.core.Element;
import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
import com.sun.xml.internal.bind.v2.model.core.NonElement;
import com.sun.xml.internal.bind.v2.model.core.Ref;
import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
import com.sun.xml.internal.bind.v2.model.nav.Navigator;
import com.sun.xml.internal.bind.v2.schemagen.XmlSchemaGenerator;
import com.sun.xml.internal.txw2.output.ResultFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;

final class JAXBModel
{

    private final Map<QName, Reference> additionalElementDecls;

    private final List<String> classList = new ArrayList<String>();

    private final TypeInfoSet<JavaType, JavaClass, JavaField, JavaMethod> types;

    private final AnnotationReader<JavaType, JavaClass, JavaField, JavaMethod> reader;

    /**
     * Lazily created schema generator.
     */
    private XmlSchemaGenerator<JavaType, JavaClass, JavaField, JavaMethod> xsdgen;

    /**
     * Look up table from an externally visible {@link Reference} object
     * to our internal format.
     */
    private final Map<Reference, NonElement<JavaType, JavaClass>> refMap =
            new HashMap<Reference, NonElement<JavaType, JavaClass>>();

    public JAXBModel(TypeInfoSet<JavaType, JavaClass, JavaField, JavaMethod> types,
                     AnnotationReader<JavaType, JavaClass, JavaField, JavaMethod> reader,
                     Collection<Reference> rootClasses,
                     Map<QName, Reference> additionalElementDecls)
    {
        this.types = types;
        this.reader = reader;
        this.additionalElementDecls = additionalElementDecls;

        Navigator<JavaType, JavaClass, JavaField, JavaMethod> navigator = types.getNavigator();

        for (ClassInfo<JavaType, JavaClass> i : types.beans().values())
        {
            classList.add(i.getName());
        }

        for (ArrayInfo<JavaType, JavaClass> a : types.arrays().values())
        {
            String javaName = navigator.getTypeName(a.getType());
            classList.add(javaName);
        }

        for (EnumLeafInfo<JavaType, JavaClass> l : types.enums().values())
        {
            QName tn = l.getTypeName();
            if (tn != null)
            {
                String javaName = navigator.getTypeName(l.getType());
                classList.add(javaName);
            }
        }

        for (Reference ref : rootClasses)
        {
            refMap.put(ref, getXmlType(ref));
        }

        // check for collision between "additional" ones and the ones given to JAXB
        // and eliminate duplication
        Iterator<Map.Entry<QName, Reference>> itr = additionalElementDecls.entrySet().iterator();
        while (itr.hasNext())
        {
            Map.Entry<QName, Reference> entry = itr.next();
            if (entry.getValue() == null)
            {
                continue;
            }

            NonElement<JavaType, JavaClass> xt = getXmlType(entry.getValue());

            assert xt != null;
            refMap.put(entry.getValue(), xt);
            if (xt instanceof ClassInfo)
            {
                ClassInfo<JavaType, JavaClass> xct = (ClassInfo<JavaType, JavaClass>) xt;
                Element<JavaType, JavaClass> elem = xct.asElement();
                if (elem != null && elem.getElementName().equals(entry.getKey()))
                {
                    itr.remove();
                    continue;
                }
            }
            ElementInfo<JavaType, JavaClass> ei = types.getElementInfo(null, entry.getKey());
            if (ei != null && ei.getContentType() == xt)
            {
                itr.remove();
            }
        }
    }

    public List<String> getClassList()
    {
        return classList;
    }

    public QName getXmlTypeName(Reference javaType)
    {
        NonElement<JavaType, JavaClass> ti = refMap.get(javaType);

        if (ti != null)
        {
            return ti.getTypeName();
        }

        return null;
    }

    private NonElement<JavaType, JavaClass> getXmlType(Reference r)
    {
        if (r == null)
        {
            throw new IllegalArgumentException();
        }

        XmlJavaTypeAdapter xjta = r.annotations.getAnnotation(XmlJavaTypeAdapter.class);
        XmlList xl = r.annotations.getAnnotation(XmlList.class);

        Ref<JavaType, JavaClass> ref = new Ref<JavaType, JavaClass>(
                reader, types.getNavigator(), r.type, xjta, xl);

        return types.getTypeInfo(ref);
    }

    public void generateSchema(SchemaOutputResolver outputResolver, ErrorListener errorListener) throws IOException
    {
        getSchemaGenerator().write(outputResolver, errorListener);
    }

    public void generateEpisodeFile(Result output)
    {
        getSchemaGenerator().writeEpisodeFile(ResultFactory.createSerializer(output));
    }

    private synchronized XmlSchemaGenerator<JavaType, JavaClass, JavaField, JavaMethod> getSchemaGenerator()
    {
        if (xsdgen == null)
        {
            xsdgen = new XmlSchemaGenerator<JavaType, JavaClass, JavaField, JavaMethod>(types.getNavigator(), types);

            for (Map.Entry<QName, Reference> e : additionalElementDecls.entrySet())
            {
                Reference value = e.getValue();
                if (value != null)
                {
                    NonElement<JavaType, JavaClass> typeInfo = refMap.get(value);
                    if (typeInfo == null)
                    {
                        throw new IllegalArgumentException(e.getValue() + " was not specified to JavaCompiler.bind");
                    }
                    xsdgen.add(e.getKey(), !value.type.isPrimitive(), typeInfo);
                }
                else
                {
                    xsdgen.add(e.getKey(), false, null);
                }
            }
        }
        return xsdgen;
    }
}
