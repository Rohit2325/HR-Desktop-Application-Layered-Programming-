import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationgetAllTestcase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface DesignationDAO;
DesignationDAO=new DesignationDAO();
Set<DesignationDTOInterface> designations;
designations= DesignationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.println("Code :"+designationDTO.getCode());
System.out.println("Title :"+designationDTO.getTitle());
});
}catch(DAOExceptions daoException)
{
System.out.println(daoException.getMessage());
}
}
} 