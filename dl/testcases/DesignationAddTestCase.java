import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationAddTestCase
{
public static void main(String gg[])
{
try
{
String title= gg[0];
DesignationDTOInterface DesignationDTO;
DesignationDTO = new DesignationDTO();
DesignationDTO.setTitle(title);
DesignationDAOInterface DesignationDAO;
DesignationDAO = new DesignationDAO();
DesignationDAO.add(DesignationDTO);
System.out.println("Designation "+DesignationDTO.getTitle()+  " added Successfully with code "+DesignationDTO.getCode());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 