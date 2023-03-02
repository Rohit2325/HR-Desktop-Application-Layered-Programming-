import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationTitleExists
{
public static void main(String gg[])
{
String title = gg[0];
try
{
DesignationDAOInterface DesignationDAO;
DesignationDAO=new DesignationDAO();
System.out.println(DesignationDAO.titleExists(title));
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 