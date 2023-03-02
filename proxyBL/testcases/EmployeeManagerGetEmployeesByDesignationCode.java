import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerGetEmployeesByDesignationCode
{
public static void main(String gg[])
{
int DesignationCode = Integer.parseInt(gg[0]);
try
{
EmployeeManagerInterface employeeManager;
employeeManager = EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees;
employees = employeeManager.getEmployeesByDesignationCode(DesignationCode);
employees.forEach((employee)->{
String EmployeeId = employee.getEmployeeId();
String name = employee.getName();
DesignationInterface designation;
designation = employee.getDesignation();
int designationCode = designation.getCode();
String title = designation.getTitle();
Date dateOfBirth = employee.getDateOfBirth();
char gender = employee.getGender();
boolean IsIndian = employee.getIsIndian();
BigDecimal basicSalary= employee.getBasicSalary();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
System.out.println("****************************************");
System.out.println("EmployeeId: "+EmployeeId);
System.out.println("Name: "+name);
System.out.println("designationCode: "+designationCode);
System.out.println("designation Title: "+title);
System.out.println("Date of birth: "+dateOfBirth);
System.out.println("Gender :"+gender);
System.out.println("IsIndian :"+IsIndian);
System.out.println("Basic salary: "+basicSalary);
System.out.println("PanNumber: "+panNumber);
System.out.println("Aadhar Card Number: "+aadharCardNumber);
});
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