import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerGetEmployeeCountTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager;
employeeManager = EmployeeManager.getEmployeeManager();
System.out.println(employeeManager.getEmployeeCount());

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