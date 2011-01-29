package org.mule.tools.cloudconnect.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxClassAdapterTest
{

    private static final String COMMENT = "COMMENT";
    private static final String PACKAGE = "PACKAGE";
    private static final String NAME = "NAME";
    private static final String PROPERTY_A = "PROPERTY_A";
    private static final String PROPERTY_B = "PROPERTY_B";
    private static final String PROPERTY_C = "PROPERTY_C";
    private static final String METHOD_A = "METHOD_A";
    private static final String CLASS_PROPERTY = "class";
    private static final String HASHCODE_METHOD_NAME = "hashCode";
    private static final String WAIT_METHOD_NAME = "wait";
    private static final String TOSTRING_METHOD_NAME = "toString";

    @Test
    public void name() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(NAME);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] {});

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(NAME, adapter.getName());
    }

    @Test
    public void pkg() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        JavaPackage packageMock = mock(JavaPackage.class);
        when(classMock.getPackage()).thenReturn(packageMock);
        when(packageMock.getName()).thenReturn(PACKAGE);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(PACKAGE, adapter.getPackage());
    }

    @Test
    public void description() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getComment()).thenReturn(COMMENT);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(COMMENT, adapter.getDescription());
    }

    @Test
    public void properties() throws Exception
    {
        JavaClass typeClassMock = mock(JavaClass.class);
        when(typeClassMock.isEnum()).thenReturn(false);
        Type typeMock = mock(Type.class);
        when(typeMock.getJavaClass()).thenReturn(typeClassMock);
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] {});
        BeanProperty propertyMockA = mock(BeanProperty.class);
        when(propertyMockA.getName()).thenReturn(PROPERTY_A);
        when(propertyMockA.getType()).thenReturn(typeMock);
        BeanProperty propertyMockB = mock(BeanProperty.class);
        when(propertyMockB.getName()).thenReturn(PROPERTY_B);
        when(propertyMockB.getType()).thenReturn(typeMock);
        BeanProperty propertyMockC = mock(BeanProperty.class);
        when(propertyMockC.getName()).thenReturn(PROPERTY_C);
        when(propertyMockC.getType()).thenReturn(typeMock);
        BeanProperty[] properties = new BeanProperty[] {propertyMockA, propertyMockB, propertyMockC};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(3, adapter.getProperties().size());
        assertEquals(PROPERTY_A, adapter.getProperties().get(0).getName());
        assertEquals(PROPERTY_B, adapter.getProperties().get(1).getName());
        assertEquals(PROPERTY_C, adapter.getProperties().get(2).getName());
    }

    @Test
    public void classBeanPropertyNotAvailable() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        BeanProperty propertyMockA = mock(BeanProperty.class);
        when(propertyMockA.getName()).thenReturn(CLASS_PROPERTY);
        BeanProperty[] properties = new BeanProperty[] {propertyMockA};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getProperties().size());
    }

    @Test
    public void hasProperties() throws Exception
    {
        JavaClass typeClassMock = mock(JavaClass.class);
        when(typeClassMock.isEnum()).thenReturn(false);
        Type typeMock = mock(Type.class);
        when(typeMock.getJavaClass()).thenReturn(typeClassMock);
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        BeanProperty propertyMock = mock(BeanProperty.class);
        when(propertyMock.getType()).thenReturn(typeMock);
        BeanProperty[] properties = new BeanProperty[] {propertyMock};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertTrue(adapter.hasProperties());
    }

    @Test
    public void doesntHaveProperties() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getMethods(true)).thenReturn(new JavaMethod[] { });
        BeanProperty[] properties = new BeanProperty[] {};
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertFalse(adapter.hasProperties());
    }

    @Test
    public void operations() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        when(methodMock.getParameters()).thenReturn(new JavaParameter[] { });
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(1, adapter.getOperations().size());
        assertEquals(METHOD_A, adapter.getOperations().get(0).getName());
    }

    @Test
    public void discardStatic() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(true);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardPropertyAccessor() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(true);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardPropertyMutator() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(true);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardConstructors() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(true);
        when(methodMock.getParentClass()).thenReturn(classMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardNonPublicMethods() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        when(classMock.getName()).thenReturn(NAME);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(false);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(classMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardBaseObjectMethods() throws Exception
    {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getBeanProperties(true)).thenReturn(new BeanProperty[] { });
        JavaMethod methodMock = mock(JavaMethod.class);
        JavaClass objectClassMock = mock(JavaClass.class);
        when(objectClassMock.getName()).thenReturn("Object");
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        when(methodMock.getParentClass()).thenReturn(objectClassMock);
        JavaMethod[] methods = new JavaMethod[] {methodMock};
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }
}
