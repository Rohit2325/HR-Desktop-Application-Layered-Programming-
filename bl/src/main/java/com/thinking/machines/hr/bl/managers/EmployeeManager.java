package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManagerInterface employeeManager=null;
private  EmployeeManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeesMap = new HashMap<>();
this.panNumberWiseEmployeesMap = new HashMap<>();
this.aadharCardNumberWiseEmployeesMap = new HashMap<>();
this.employeesSet = new TreeSet<>();
this.designationCodeWiseEmployeesMap = new HashMap<>();
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
try
{
EmployeeInterface employee;
Set<employeeDTOInterface> dlEmployees;
dlEmployees = new employeeDAO().getAll();
for(employeeDTOInterface dlEmployee:dlEmployees)
{
employee = new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation = designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets = this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets = new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
} 
}//forLoop
}catch(DAOExceptions daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager = new EmployeeManager();
return employeeManager;
}

//**********************************************************
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException;
blException = new BLException();
String employeeId = employee.getEmployeeId();
String name = employee.getName();
DesignationInterface designation= employee.getDesignation();
int designationCode=0;
Date dateOfBirth = employee.getDateOfBirth();
char gender = employee.getGender();
Boolean IsIndian = employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
if(employeeId!=null)
{
employeeId =employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("EmployeeID","employeeId should be nil/empty");
}
}
if(name==null)
{
blException.addException("Name","Name required");
name = "";
}
name = name.trim();
if(name.length()<0)
{
blException.addException("Name","Name required");
}
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationCode = designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("Designation","Invalid designation Code");
}
if(dateOfBirth==null)
{
blException.addException("DateOfBirth","date of birth required");
}
if(gender==' ')
{
blException.addException("Gender","gender required");
}
if(basicSalary==null)
{
blException.addException("BasicSalary","basic salary required");
}
if(basicSalary.signum()==-1)
{
blException.addException("BasicSalary","basic salary should not be negative");
}
if(panNumber==null)
{
blException.addException("PanNumber","PANNumber required");
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
if(panNumber!=null && panNumber.length()>0)
{
if(this.panNumberWiseEmployeesMap.containsKey(panNumber))
{
blException.addException("PanNumber "," PanNumber Exists ");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber))
{
blException.addException("AadharCardNumber "," Aadhar card number Exists ");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
employeeDTOInterface dlEmployee;
dlEmployee = new employeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designationCode);
dlEmployee.setDateOfBirth(dateOfBirth);
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(IsIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
new employeeDAO().add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface employeeDS;
employeeDS = new Employee();
employeeDS.setEmployeeId(dlEmployee.getEmployeeId());
employeeDS.setName(name);
employeeDS.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
employeeDS.setDateOfBirth((Date)dateOfBirth.clone());
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
employeeDS.setGender(GENDER.FEMALE);
}
employeeDS.setIsIndian(IsIndian);
employeeDS.setBasicSalary(basicSalary);
employeeDS.setPANNumber(panNumber);
employeeDS.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(dlEmployee.getEmployeeId().toUpperCase(),employeeDS);
this.panNumberWiseEmployeesMap.put(dlEmployee.getPANNumber().toUpperCase(),employeeDS);
this.aadharCardNumberWiseEmployeesMap.put(dlEmployee.getAadharCardNumber().toUpperCase(),employeeDS);
this.employeesSet.add(employeeDS);
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeesMap.get(employeeDS.getDesignation().getCode());
if(ets==null)
{
ets= new TreeSet<>();
ets.add(employeeDS);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employeeDS);
}
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}

}//ADDmethodsEnds

//***********************************************************

public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException;
blException = new BLException();
String employeeId = employee.getEmployeeId();
String name = employee.getName();
DesignationInterface designation= employee.getDesignation();
int designationCode=0;
Date dateOfBirth = employee.getDateOfBirth();
char gender = employee.getGender();
Boolean IsIndian = employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("EmployeeId","employeeId required");
throw blException;
}
if(employeeId!=null)
{
employeeId =employeeId.trim();
if(employeeId.length()<=0)
{
blException.addException("EmployeeId","invalid employeeId");
throw blException;
}
}
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId)==false)
{
blException.addException("EmployeeID","invalid employeeId");
throw blException;
}
if(name==null)
{
blException.addException("Name","Name required");
name = "";
}
name = name.trim();
if(name.length()<0)
{
blException.addException("Name","Name required");
}
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationCode = designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("Designation","Invalid designation Code");
}
if(dateOfBirth==null)
{
blException.addException("DateOfBirth","date of birth required");
}
if(gender==' ')
{
blException.addException("Gender","gender required");
}
if(basicSalary==null)
{
blException.addException("BasicSalary","basic salary required");
}
if(basicSalary.signum()==-1)
{
blException.addException("BasicSalary","basic salary should not be negative");
}
if(panNumber==null)
{
blException.addException("PanNumber","PANNumber required");
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ePan = this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ePan!=null && employeeId.equalsIgnoreCase(ePan.getEmployeeId())==false)
{
blException.addException("PanNumber ","PanNumber exists against different employeeId");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface eAadhar = this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(eAadhar!=null && employeeId.equalsIgnoreCase(eAadhar.getEmployeeId())==false)
{
blException.addException("AadharCardNumber ","Aadhar card number exists against different employeeId");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
employeeDTOInterface dlEmployee;
dlEmployee = new employeeDTO();
dlEmployee.setEmployeeId(employeeId);
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designationCode);
dlEmployee.setDateOfBirth(dateOfBirth);
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(IsIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
new employeeDAO().update(dlEmployee);
EmployeeInterface Employee=this.employeeIdWiseEmployeesMap.get(employeeId);
this.employeeIdWiseEmployeesMap.remove(Employee.getEmployeeId());
this.panNumberWiseEmployeesMap.remove(employee.getPANNumber());
this.aadharCardNumberWiseEmployeesMap.remove(employee.getAadharCardNumber());
this.employeesSet.remove(Employee);
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeesMap.get(Employee.getDesignation().getCode());
ets.remove(Employee);
EmployeeInterface employeeDS;
employeeDS = new Employee();
employeeDS.setEmployeeId(employeeId);
employeeDS.setName(name);
employeeDS.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
employeeDS.setDateOfBirth((Date)dateOfBirth.clone());
if(gender=='M')
{
employeeDS.setGender(GENDER.MALE);
}
else
{
employeeDS.setGender(GENDER.FEMALE);
}
employeeDS.setIsIndian(IsIndian);
employeeDS.setBasicSalary(basicSalary);
employeeDS.setPANNumber(panNumber);
employeeDS.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),employeeDS);
this.panNumberWiseEmployeesMap.put(employeeDS.getPANNumber().toUpperCase(),employeeDS);
this.aadharCardNumberWiseEmployeesMap.put(employeeDS.getAadharCardNumber().toUpperCase(),employeeDS);
this.employeesSet.add(employeeDS);
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}


}//updateMethod ends

//************************************************************

public void removeEmployee(String employeeId) throws BLException
{
BLException blException;
blException = new BLException();
if(employeeId!=null)
{
employeeId = employeeId.trim();
if(employeeId.length()<=0)
{
blException.addException("EmployeeId","invalid employeeId");
throw blException;
}
}
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("EmployeeID","invalid employeeId");
throw blException;
}
EmployeeInterface employee;
try
{
new employeeDAO().delete(employeeId);
employee = this.employeeIdWiseEmployeesMap.get(employeeId);
this.employeeIdWiseEmployeesMap.remove(employeeId);
this.panNumberWiseEmployeesMap.remove(employee.getPANNumber());
this.aadharCardNumberWiseEmployeesMap.remove(employee.getAadharCardNumber());
this.employeesSet.remove(employee);
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeesMap.get(employee.getDesignation().getCode());
ets.remove(employee);
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//removeMethoad Ends

//************************************************************

public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException;
blException = new BLException();
if(employeeId!=null)
{
employeeId = employeeId.trim();
if(employeeId.length()<=0)
{
blException.addException("EmployeeId","invalid employeeId");
throw blException;
}
}
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("EmployeeId","invalid employeeId");
throw blException;
}
EmployeeInterface dlEmployee;
EmployeeInterface employee;
DesignationInterface designation;
employee = this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
dlEmployee = new Employee();
dlEmployee.setEmployeeId(employee.getEmployeeId());
dlEmployee.setName(employee.getName());
designation = new  Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dlEmployee.setDesignation(designation);
dlEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
char gender = employee.getGender();
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dlEmployee;
}//getEmployeeByEmployeeId ends

//**********************************************************

public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException;
blException = new BLException();
if(panNumber!=null)
{
panNumber = panNumber.trim();
if(panNumber.length()<=0)
{
blException.addException("PanNumber","invalid panNumber");
throw blException;
}
}
if(this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==false)
{
blException.addException("PanNumber","invalid panNumber");
throw blException;
}
EmployeeInterface dlEmployee;
EmployeeInterface employee;
DesignationInterface designation;
employee = this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
dlEmployee = new Employee();
dlEmployee.setEmployeeId(employee.getEmployeeId());
dlEmployee.setName(employee.getName());
designation = new  Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dlEmployee.setDesignation(designation);
dlEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
char gender = employee.getGender();
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dlEmployee;
}//getEmployeeBypanNumber ends


//************************************************************

public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException;
blException = new BLException();
if(aadharCardNumber!=null)
{
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length()<=0)
{
blException.addException("aadharCardNumber","invalid aadhar Card Number");
throw blException;
}
}
if(this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())==false)
{
blException.addException("aadharCardNumber","invalid aadhar Card Number");
throw blException;
}
EmployeeInterface dlEmployee;
EmployeeInterface employee;
DesignationInterface designation;
employee = this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
dlEmployee = new Employee();
dlEmployee.setEmployeeId(employee.getEmployeeId());
dlEmployee.setName(employee.getName());
designation = new  Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dlEmployee.setDesignation(designation);
dlEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
char gender = employee.getGender();
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dlEmployee;
}//getByAadhar ends;

//***********************************************************

public int getEmployeeCount()
{
return employeesSet.size();
}//GetEmployeeCount ends;


//******************************************************


public boolean employeeIdExists(String employeeId) 
{
return this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}//employeeIdExists ends

//**********************************************************

public boolean employeePANNumberExists(String panNumber)
{
return this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}//PanNumberExists ends;

//**********************************************************


public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}//aadharCardExists ends;

//************************************************************

public Set<EmployeeInterface> getEmployees() 
{
Set<EmployeeInterface> employees= new TreeSet<>();
this.employeesSet.forEach((employee)->{
EmployeeInterface dlEmployee; 
dlEmployee = new Employee();
dlEmployee.setEmployeeId(employee.getEmployeeId());
dlEmployee.setName(employee.getName());
DesignationInterface designation= employee.getDesignation();
DesignationInterface dlDesignation = new Designation();
dlDesignation.setCode(designation.getCode());
dlDesignation.setTitle(designation.getTitle());
dlEmployee.setDesignation(dlDesignation);
dlEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
char gender = employee.getGender();
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(dlEmployee);
});
return employees;
}//getEmployees ends

//**********************************************************

public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> employees= new TreeSet<>();
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return employees;
}
ets.forEach((employee)->{
EmployeeInterface dlEmployee; 
dlEmployee = new Employee();
dlEmployee.setEmployeeId(employee.getEmployeeId());
dlEmployee.setName(employee.getName());
DesignationInterface designation= employee.getDesignation();
DesignationInterface dlDesignation = new Designation();
dlDesignation.setCode(designation.getCode());
dlDesignation.setTitle(designation.getTitle());
dlEmployee.setDesignation(dlDesignation);
dlEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
char gender = employee.getGender();
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(dlEmployee);
});
return employees;
}

//***********************************************************

public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}

//*************************************************************

public boolean designationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}