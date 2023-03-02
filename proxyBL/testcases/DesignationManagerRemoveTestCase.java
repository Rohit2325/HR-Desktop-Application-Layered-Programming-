import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerRemoveTestCase
{
public static void main(String gg[])
{
try
{
int code = Integer.parseInt(gg[0]);
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation Removed Successfully");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getMessage());
}
List<String>properties = blException.getProperty();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}