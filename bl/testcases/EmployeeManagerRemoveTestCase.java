import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
try
{
String employeeId = gg[0];
EmployeeManagerInterface employeeManager;
employeeManager = EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.println("Employee Removed Successfully");
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