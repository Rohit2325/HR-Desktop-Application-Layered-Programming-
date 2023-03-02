import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationGetByTitleTestcase
{
public static void main(String gg[])
{
try
{
String title= gg[0];
DesignationDTOInterface DesignationDTO;
DesignationDAOInterface DesignationDAO;
DesignationDAO=new DesignationDAO();
DesignationDTO = DesignationDAO.getByTitle(title);
System.out.println("Code : "+DesignationDTO.getCode());
System.out.println("Title : "+DesignationDTO.getTitle());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 