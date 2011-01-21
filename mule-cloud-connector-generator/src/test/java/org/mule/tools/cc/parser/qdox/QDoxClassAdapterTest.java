package org.mule.tools.cc.parser.qdox;

import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxClassAdapterTest {

    private static final String COMMENT = "COMMENT";
    private static final String PACKAGE = "PACKAGE";
    private static final String NAME = "NAME";
    private static final String PROPERTY_A = "PROPERTY_A";
    private static final String PROPERTY_B = "PROPERTY_B";
    private static final String PROPERTY_C = "PROPERTY_C";
    private static final String METHOD_A = "METHOD_A";

    @Test
    public void name() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(NAME);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(NAME, adapter.getName());
    }

    @Test
    public void pkg() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getPackage()).thenReturn(PACKAGE);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(PACKAGE, adapter.getPackage());
    }

    @Test
    public void description() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getComment()).thenReturn(COMMENT);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(COMMENT, adapter.getDescription());
    }

    @Test
    public void properties() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        BeanProperty propertyMockA = mock(BeanProperty.class);
        when(propertyMockA.getName()).thenReturn(PROPERTY_A);
        BeanProperty propertyMockB = mock(BeanProperty.class);
        when(propertyMockB.getName()).thenReturn(PROPERTY_B);
        BeanProperty propertyMockC = mock(BeanProperty.class);
        when(propertyMockC.getName()).thenReturn(PROPERTY_C);
        BeanProperty[] properties = new BeanProperty[] { propertyMockA, propertyMockB, propertyMockC };
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(3, adapter.getProperties().size());
        assertEquals(PROPERTY_A, adapter.getProperties().get(0).getName());
        assertEquals(PROPERTY_B, adapter.getProperties().get(1).getName());
        assertEquals(PROPERTY_C, adapter.getProperties().get(2).getName());
    }

    @Test
    public void hasProperties() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        BeanProperty propertyMock = mock(BeanProperty.class);
        BeanProperty[] properties = new BeanProperty[] { propertyMock };
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertTrue(adapter.hasProperties());
    }

    @Test
    public void doesntHaveProperties() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        BeanProperty[] properties = new BeanProperty[] { };
        when(classMock.getBeanProperties(eq(true))).thenReturn(properties);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertFalse(adapter.hasProperties());
    }

    @Test
    public void operations() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(1, adapter.getOperations().size());
        assertEquals(METHOD_A, adapter.getOperations().get(0).getName());
    }

    @Test
    public void discardStatic() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(true);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardPropertyAccessor() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(true);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardPropertyMutator() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(true);
        when(methodMock.isConstructor()).thenReturn(false);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardConstructors() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(true);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(true);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

    @Test
    public void discardNonPublicMethods() throws Exception {
        JavaClass classMock = mock(JavaClass.class);
        JavaMethod methodMock = mock(JavaMethod.class);
        when(methodMock.getName()).thenReturn(METHOD_A);
        when(methodMock.isPublic()).thenReturn(false);
        when(methodMock.isStatic()).thenReturn(false);
        when(methodMock.isPropertyAccessor()).thenReturn(false);
        when(methodMock.isPropertyMutator()).thenReturn(false);
        when(methodMock.isConstructor()).thenReturn(false);
        JavaMethod[] methods = new JavaMethod[] { methodMock };
        when(classMock.getMethods(eq(true))).thenReturn(methods);

        QDoxClassAdapter adapter = new QDoxClassAdapter(classMock);

        assertEquals(0, adapter.getOperations().size());
    }

}
