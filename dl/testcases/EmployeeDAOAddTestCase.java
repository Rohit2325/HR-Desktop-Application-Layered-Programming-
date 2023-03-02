import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeDAOAddTestCase
{
public static void main(String gg[])
{
String name = gg[0];
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
int designationCode = Integer.parseInt(gg[1]);
Date dateOfBirth = null;
try
{
dateOfBirth= sdf.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[3].charAt(0);
boolean isIndian= Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary= new BigDecimal(gg[5]);
String panNumber = gg[6];
String AadharCardNumber = gg[7];
try
{
employeeDTOInterface employeeDTO;
employeeDTO = new employeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.MALE);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(AadharCardNumber);
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("employee added Successfully "+employeeDTO.getEmployeeId());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}