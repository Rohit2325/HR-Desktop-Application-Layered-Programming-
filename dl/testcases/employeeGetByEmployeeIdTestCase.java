import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class employeeGetByEmployeeIdTestCase
{
public static void main(String gg[])
{
String EmployeeId = gg[0];
try
{
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
employeeDTOInterface employeeDTO;
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
employeeDTO = employeeDAO.getByEmployeeId(EmployeeId);
System.out.println("EmployeeId: "+employeeDTO.getEmployeeId());
System.out.println("Name: "+employeeDTO.getName());
System.out.println("Designation Code: "+employeeDTO.getDesignationCode());
System.out.println("DOB: "+ simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender: "+employeeDTO.getGender());
System.out.println("IsIndian: "+employeeDTO.getIsIndian());
System.out.println("basicSalary: "+employeeDTO.getBasicSalary());
System.out.println("PAN Number: "+employeeDTO.getPANNumber());
System.out.println("Aadhar Card Number: "+employeeDTO.getAadharCardNumber());
System.out.println("***********************");
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}
