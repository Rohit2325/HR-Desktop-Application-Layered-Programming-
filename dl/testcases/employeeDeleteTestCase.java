import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class employeeDeleteTestCase
{
public static void main(String gg[])
{
String EmployeeId = gg[0];
try
{
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
employeeDAO.delete(EmployeeId);
System.out.println("Employee Deleted Against Employee id :"+EmployeeId);
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}
