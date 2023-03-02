import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class employeeUpdateTestCase
{
public static void main(String gg[])
{
String employeeId = gg[0];
String name = gg[1];
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
int designationCode = Integer.parseInt(gg[2]);
Date dateOfBirth = null;
try
{
dateOfBirth= sdf.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[4].charAt(0);
boolean isIndian= Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary= new BigDecimal(gg[6]);
String panNumber = gg[7];
String AadharCardNumber = gg[8];
try
{
employeeDTOInterface employeeDTO;
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId(employeeId);
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
employeeDAO.update(employeeDTO);
System.out.println("employee updated Successfully");
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}