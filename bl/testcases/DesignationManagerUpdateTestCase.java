import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation;
designation = new  Designation();
designation.setCode(1);
designation.setTitle("Clerk");
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Designation Updated Successfully");
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