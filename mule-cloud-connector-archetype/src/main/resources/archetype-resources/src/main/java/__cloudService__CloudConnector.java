#set($D='$')
package ${package};

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="${cloudServiceLower}", muleVersion="${D}{mule.version}")
public class ${cloudService}CloudConnector
{
    /*
     * The following is a sample operation
     */
    @Operation
    public void myOperation()
    {
    }
}
