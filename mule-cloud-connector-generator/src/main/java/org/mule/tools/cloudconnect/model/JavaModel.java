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

package org.mule.tools.cloudconnect.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class JavaModel
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

    public abstract JavaType getVoidType();

    public abstract void parse(List<File> sourceTrees);

    public abstract JavaType getType(Class cls);

    public abstract JavaClass getClass(String name);
}
