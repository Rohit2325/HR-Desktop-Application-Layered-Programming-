import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;import java.util.*;
import java.text.*;
import java.math.*;
public class employeeGetAllTestCase
{
public static void main(String gg[])
{
try
{
Set<employeeDTOInterface> employees;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
employeeDTOInterface employeeDTO;
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
employees = employeeDAO.getAll();
employees.forEach((employeeDTo)->{
System.out.println("EmployeeId: "+employeeDTo.getEmployeeId());
System.out.println("Name: "+employeeDTo.getName());
System.out.println("Designation Code: "+employeeDTo.getDesignationCode());
System.out.println("DOB: "+ simpleDateFormat.format(employeeDTo.getDateOfBirth()));
System.out.println("Gender: "+employeeDTo.getGender());
System.out.println("IsIndian: "+employeeDTo.getIsIndian());
System.out.println("basicSalary: "+employeeDTo.getBasicSalary());
System.out.println("PAN Number: "+employeeDTo.getPANNumber());
System.out.println("Aadhar Card Number: "+employeeDTo.getAadharCardNumber());
System.out.println("***********************");
});
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}
