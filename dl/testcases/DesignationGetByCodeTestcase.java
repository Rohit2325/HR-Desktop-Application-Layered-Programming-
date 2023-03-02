import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationGetByCodeTestcase
{
public static void main(String gg[])
{
try
{
int code= Integer.parseInt(gg[0]);
DesignationDTOInterface DesignationDTO;
DesignationDAOInterface DesignationDAO;
DesignationDAO=new DesignationDAO();
DesignationDTO = DesignationDAO.getByCode(code);
System.out.println("Code : "+DesignationDTO.getCode());
System.out.println("Title : "+DesignationDTO.getTitle());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 