import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerAddTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation;
designation = new  Designation();
designation.setCode(0);
designation.setTitle("Clerk");
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designation "+designation.getTitle()+ " Added with code "+designation.getCode());
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