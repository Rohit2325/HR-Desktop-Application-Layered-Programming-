import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class employeeIsDesignationAllotedTestCase
{
public static void main(String gg[])
{
int designationCode =Integer.parseInt(gg[0]);
try
{
employeeDAOInterface employeeDAO;
employeeDAO = new employeeDAO();
System.out.println("Designation Alloted :"+employeeDAO.isDesignationAlloted(designationCode));
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
}
