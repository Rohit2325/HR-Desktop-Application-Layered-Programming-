import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationCodeExists
{
public static void main(String gg[])
{
int code = Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface DesignationDAO;
DesignationDAO=new DesignationDAO();
System.out.println(DesignationDAO.codeExists(code));
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 