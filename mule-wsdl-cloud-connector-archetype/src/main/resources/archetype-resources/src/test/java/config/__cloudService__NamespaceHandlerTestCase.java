#set($D = '$')
package ${package}.config;

import org.mule.tck.FunctionalTestCase;

public class ${cloudService}NamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "${cloudServiceLower}-namespace-config.xml";
    }

    public void testNamespace()
    {
        // TODO implement some tests
    }
}
