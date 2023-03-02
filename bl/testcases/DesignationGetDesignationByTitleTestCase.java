import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationGetDesignationByTitleTestCase
{
public static void main(String gg[])
{
String title =gg[0];
try
{
DesignationInterface designation;
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designation=designationManager.getDesignationByTitle(title);
System.out.println("Code: "+designation.getCode()+ "title: "+designation.getTitle() );
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