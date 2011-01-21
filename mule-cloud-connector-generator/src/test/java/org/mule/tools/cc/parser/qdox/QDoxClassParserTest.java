package org.mule.tools.cc.parser.qdox;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.parser.ParseException;
import org.junit.Test;
import org.mule.tools.cc.parser.ClassParseException;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QDoxClassParserTest {

    private static final String CLASS_A = "CLASS_A";

    @Test(expected=ClassParseException.class)
    public void noClasses() throws Exception {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenReturn(null);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.parse(inputStreamMock);
    }

    @Test(expected=ClassParseException.class)
    public void parseException() throws Exception {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenThrow(new ParseException("", 0, 0));

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.parse(inputStreamMock);
    }

    @Test(expected=ClassParseException.class)
    public void moreThanOneClass() throws Exception {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);

        JavaClass classMockA = mock(JavaClass.class);
        JavaClass classMockB = mock(JavaClass.class);
        JavaClass[] classes = new JavaClass[] { classMockA, classMockB };
        when(javaDocBuilder.getClasses()).thenReturn(classes);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.parse(inputStreamMock);
    }

    @Test
    public void success() throws Exception {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);

        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(CLASS_A);
        JavaClass[] classes = new JavaClass[] { classMock };
        when(javaDocBuilder.getClasses()).thenReturn(classes);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        org.mule.tools.cc.model.JavaClass result = parser.parse(inputStreamMock);

        assertEquals(CLASS_A, result.getName());
    }

}
