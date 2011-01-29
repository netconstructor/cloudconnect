package org.mule.tools.cloudconnect.parser.qdox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class QDoxClassParserTest
{

    private static final String CLASS_A = "CLASS_A";
         /*
    @Test(expected = ClassParseException.class)
    public void noClasses() throws Exception
    {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenReturn(null);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.parse(CLASS_A, inputStreamMock);
    }

    @Test(expected = ClassParseException.class)
    public void parseException() throws Exception
    {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);
        when(javaDocBuilder.getClasses()).thenThrow(new ParseException("", 0, 0));

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        parser.parse(CLASS_A, inputStreamMock);
    }


    @Test
    public void success() throws Exception
    {
        InputStream inputStreamMock = mock(InputStream.class);
        JavaDocBuilder javaDocBuilder = mock(JavaDocBuilder.class);

        URL sourceUrlMock = new URL("file://localhost/CLASS_A.java");
        JavaSource sourceMock = mock(JavaSource.class);
        when(sourceMock.getURL()).thenReturn(sourceUrlMock);
        JavaClass classMock = mock(JavaClass.class);
        when(classMock.getName()).thenReturn(CLASS_A);
        when(classMock.getSource()).thenReturn(sourceMock);
        JavaClass[] classes = new JavaClass[] {classMock};
        when(javaDocBuilder.getClasses()).thenReturn(classes);

        QDoxClassParser parser = new QDoxClassParser();
        parser.setJavaDocBuilder(javaDocBuilder);
        org.mule.tools.cloudconnect.model.JavaClass result = parser.parse("/CLASS_A.java", inputStreamMock);

        assertEquals(CLASS_A, result.getName());
    }
    */

}
