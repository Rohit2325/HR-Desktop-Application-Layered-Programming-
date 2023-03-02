import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationGetCountTestcase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface DesignationDAO;
DesignationDAO = new DesignationDAO();
System.out.println("total Count :"+DesignationDAO.getCount());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 