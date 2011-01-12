#set($D = '$')
package ${package};

import org.junit.Test;

import ${package}.${cloudService}CloudConnector;

public class ${cloudService}TestCase
{
    @Test
    public void invokeSomeMethodOnTheCloudConnector()
    {
        /*
            Add code that tests the cloud connector at the API level. This means that you'll
            instantiate your cloud connector directly, invoke one of its methods and assert
            you get the correct result.

            Example:
            
            ${cloudService}CloudConnector connector = new ${cloudService}CloudConnector();
            Object result = connector.someMethod("sample input");
            assertEquals("expected output", result);
         */
    }
}
