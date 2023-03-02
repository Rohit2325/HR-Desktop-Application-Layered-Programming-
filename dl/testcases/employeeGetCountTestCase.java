import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class employeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
System.out.println("Total Number of Records :"+employeeDAO.getCount());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}
