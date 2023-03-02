import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager;
employeeManager = EmployeeManager.getEmployeeManager();
EmployeeInterface employee;
employee = new Employee();
DesignationInterface  designation;
designation = new Designation();
String employeeId;
String name="Arvind Sisodiya";
designation.setCode(4);
designation.setTitle("manager");
Date dateOfBirth= new Date(12/12/1998);
char gender= 'M';
boolean isIndian= true;
BigDecimal basicSalary= new BigDecimal("1000000");
String panNumber="ArvindPan";
String aadharCardNumber="ArvindAadhar"; 
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
employeeManager.addEmployee(employee);
System.out.println("employee Added with employeeId "+employee.getEmployeeId());
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