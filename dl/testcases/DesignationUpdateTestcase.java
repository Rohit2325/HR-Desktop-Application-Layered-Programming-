import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationUpdateTestcase
{
public static void main(String gg[])
{
try
{
DesignationDTOInterface DesignationDTO;
DesignationDTO = new DesignationDTO();
DesignationDTO.setCode(4);
DesignationDTO.setTitle("Manager");
DesignationDAOInterface DesignationDAO;
DesignationDAO = new DesignationDAO();
DesignationDAO.update(DesignationDTO);
System.out.println("Designation "+DesignationDTO.getTitle()+  " updated Successfully with code "+DesignationDTO.getCode());
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 