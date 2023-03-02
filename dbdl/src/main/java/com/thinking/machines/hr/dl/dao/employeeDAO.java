//private String employeeId;
//private String name;
//private int designationCode;
//private Date dateOfBirth;
//private char gender;
//private boolean IsIndian;
//private BigDecimal basicSalary;
//private String PANNumber;
//private String AadharCardNumber;

package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.sql.*;
public class employeeDAO implements employeeDAOInterface
{
public static final String FILE_NAME = "employee.data";
public void add(employeeDTOInterface employeeDTO)throws DAOExceptions
{
if(employeeDTO==null) throw new DAOExceptions("Employee is null");
String employeeId;
String name = employeeDTO.getName();
if(name==null) throw new DAOExceptions("name is null");
name=name.trim();
if(name.length()==0) throw new DAOExceptions("length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code =?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
connection.close();
preparedStatement.close();
resultSet.close();
throw new DAOExceptions("designation code is invalid "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}

java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("date of birth is null");
}

char gender = employeeDTO.getGender();
boolean IsIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Salary is negative");
}

String PANNumber = employeeDTO.getPANNumber();
if(PANNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan Number is Null");
}

PANNumber = PANNumber.trim();
if(PANNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("length of panNumber is zero");
}

String AadharCardNumber = employeeDTO.getAadharCardNumber();
if(AadharCardNumber== null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Aadhar Card Number is null");
}
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("length of Aadhar Card Number is Zero");
}

boolean PanNumberExists;
boolean AadharCardNumberExists;
try
{
preparedStatement =connection.prepareStatement("select gender from employee where pan_number=?"); 
preparedStatement.setString(1,PANNumber);
resultSet = preparedStatement.executeQuery();
PanNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();

preparedStatement =connection.prepareStatement("select gender from employee where aadhar_Card_Number=?"); 
preparedStatement.setString(1,AadharCardNumber);
resultSet = preparedStatement.executeQuery();
AadharCardNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}

if(PanNumberExists && AadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan Number and Aadhar Card Number exists "+PANNumber+""+AadharCardNumber);
}

if(PanNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan number Exists "+PANNumber);
}

if(AadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Aadhar card number Exists "+AadharCardNumber);
}

try
{
preparedStatement = connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,basic_salary,is_indian,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth;
sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBigDecimal(5,basicSalary);
preparedStatement.setBoolean(6,IsIndian);
preparedStatement.setString(7,PANNumber);
preparedStatement.setString(8,AadharCardNumber);
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
int lastGeneratedKey = resultSet.getInt(1); 
employeeDTO.setEmployeeId("A"+(lastGeneratedKey+10000000));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//add methoad ends


//*********************************************************



public void update(employeeDTOInterface employeeDTO) throws DAOExceptions
{
if(employeeDTO==null) throw new DAOExceptions("Employee is null");
String employeeId= employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId :"+employeeId);
if(employeeId.length()==0) throw new DAOExceptions("employeeID is of Zero length "+employeeId);
employeeId = employeeId.trim();
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOExceptions(exception.getMessage());
}

String name = employeeDTO.getName();
if(name==null) throw new DAOExceptions("name is null");
name=name.trim();
if(name.length()==0) throw new DAOExceptions("length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code =?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
connection.close();
preparedStatement.close();
resultSet.close();
throw new DAOExceptions("designation code is invalid "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}

java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("date of birth is null");
}

char gender = employeeDTO.getGender();
boolean IsIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Salary is negative");
}

String PANNumber = employeeDTO.getPANNumber();
if(PANNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan Number is Null");
}

PANNumber = PANNumber.trim();
if(PANNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("length of panNumber is zero");
}

String AadharCardNumber = employeeDTO.getAadharCardNumber();
if(AadharCardNumber== null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Aadhar Card Number is null");
}
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("length of Aadhar Card Number is Zero");
}

boolean PanNumberExists;
boolean AadharCardNumberExists;
try
{
preparedStatement =connection.prepareStatement("select gender from employee where pan_number=? and employee_Id<>?"); 
preparedStatement.setString(1,PANNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
PanNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();

preparedStatement =connection.prepareStatement("select gender from employee where aadhar_Card_Number=? and employee_Id<>?"); 
preparedStatement.setString(1,AadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
AadharCardNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}

if(PanNumberExists && AadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan Number and Aadhar Card Number exists "+PANNumber+""+AadharCardNumber);
}
if(PanNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Pan number Exists "+PANNumber);
}

if(AadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
throw new DAOExceptions("Aadhar card number Exists "+AadharCardNumber);
}
try
{
preparedStatement = connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,basic_salary=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth;
sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBigDecimal(5,basicSalary);
preparedStatement.setBoolean(6,IsIndian);
preparedStatement.setString(7,PANNumber);
preparedStatement.setString(8,AadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
//resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}// update Ends

//************************************************************

public void delete(String employeeId)throws DAOExceptions
{
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId :"+employeeId);
if(employeeId.length()==0) throw new DAOExceptions("employeeID is of Zero length "+employeeId);
employeeId = employeeId.trim();
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOExceptions(exception.getMessage());
}
Connection connection=null;
PreparedStatement preparedStatement; 
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOExceptions("EmployeeId is invalid");
}
preparedStatement.close();
resultSet.close();
preparedStatement = connection.prepareStatement("delete from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}// delete ends


//********************************************************


public Set<employeeDTOInterface> getAll() throws DAOExceptions
{
Set<employeeDTOInterface> employees = new TreeSet<>();
Connection connection=null;
Statement statement;
ResultSet resultSet;
employeeDTOInterface employeeDTO= null;
try
{
connection = DAOConnection.getConnection();
statement = connection.createStatement();
resultSet = statement.executeQuery("Select * from employee");
java.sql.Date sqlDate;
java.util.Date utilDate;
String gender;
while(resultSet.next())
{
employeeDTO = new employeeDTO();
int employeeId = resultSet.getInt("employee_id");
String actualEmployeeId = "A1000000"+employeeId;
employeeDTO.setEmployeeId(actualEmployeeId);
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code")); 
sqlDate = resultSet.getDate("date_of_birth");
utilDate = new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
employeeDTO.setDateOfBirth(utilDate);
gender = resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
statement.close();
resultSet.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return employees;
}

//*******************************************************

public Set<employeeDTOInterface> getByDesignationCode(int designationCode) throws DAOExceptions
{
Set<employeeDTOInterface> employees = new TreeSet<>();
if(designationCode<=0) throw new DAOExceptions("Invalid Designation Code");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
employeeDTOInterface employeeDTO= null;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("Select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
java.sql.Date sqlDate;
java.util.Date utilDate;
String gender;
while(resultSet.next())
{
employeeDTO = new employeeDTO();
int employeeId = resultSet.getInt("employee_id");
String actualEmployeeId = "A1000000"+employeeId;
employeeDTO.setEmployeeId(actualEmployeeId);
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code")); 
sqlDate = resultSet.getDate("date_of_birth");
utilDate = new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
employeeDTO.setDateOfBirth(utilDate);
gender = resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
preparedStatement.close();
resultSet.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return employees;
}//getByDesignationCode

//*****************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOExceptions
{
if(designationCode<=0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select name from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return true;
}

//*****************************************************

public employeeDTOInterface getByEmployeeId(String employeeId) throws DAOExceptions
{
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId. :"+employeeId );
employeeId = employeeId.trim();
if(employeeId.length()==0) throw new DAOExceptions("Zero length :"+employeeId);
int actualEmployeeId = Integer.parseInt(employeeId.substring(1))-10000000;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
employeeDTOInterface employeeDTO=null;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
java.sql.Date sqlDate;
java.util.Date utilDate;
String gender;
if(resultSet.next())
{
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId("A1000000"+(resultSet.getInt("employee_Id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code")); 
sqlDate = resultSet.getDate("date_of_birth");
utilDate = new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
employeeDTO.setDateOfBirth(utilDate);
gender = resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
}
if(employeeDTO==null)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOExceptions("Invalid employeeId");
}
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return employeeDTO;
}//getByEmployeeId ends;

//*****************************************************


public employeeDTOInterface getByPANNumber(String panNumber) throws DAOExceptions
{
if(panNumber==null) throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
panNumber = panNumber.trim();
if(panNumber.length()==0) throw new DAOExceptions("Zero length");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
employeeDTOInterface employeeDTO=null;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
java.sql.Date sqlDate;
java.util.Date utilDate;
String gender;
if(resultSet.next())
{
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId("A1000000"+(resultSet.getInt("employee_Id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code")); 
sqlDate = resultSet.getDate("date_of_birth");
utilDate = new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
employeeDTO.setDateOfBirth(utilDate);
gender = resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
}
if(employeeDTO==null)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOExceptions("Invalid Pan Number");
}
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return employeeDTO;
}

//*********************************************************

public employeeDTOInterface getByAadharCardNumber(String AadharCardNumber) throws DAOExceptions
{
if(AadharCardNumber==null) throw new DAOExceptions("Invalid Aadhar Card Number. :"+AadharCardNumber);
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) throw new DAOExceptions("Zero length");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
employeeDTOInterface employeeDTO=null;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,AadharCardNumber);
resultSet = preparedStatement.executeQuery();
java.sql.Date sqlDate;
java.util.Date utilDate;
String gender;
if(resultSet.next())
{
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId("A1000000"+(resultSet.getInt("employee_Id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code")); 
sqlDate = resultSet.getDate("date_of_birth");
utilDate = new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
employeeDTO.setDateOfBirth(utilDate);
gender = resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
}
if(employeeDTO==null)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOExceptions("Invalid Aadhar card Number");
}
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return employeeDTO;
}

//****************************************************

public boolean employeeIdExists(String employeeId) throws DAOExceptions
{
if(employeeId==null) return false;
employeeId = employeeId.trim();
if(employeeId.length()==0) return false;
int actualEmployeeId = Integer.parseInt(employeeId.substring(1))-10000000;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select name from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return true;
}//employeeIdExists ends

//**********************************************

public boolean PANNumberExists(String panNumber) throws DAOExceptions
{
if(panNumber==null) return false;
panNumber = panNumber.trim();
if(panNumber.length()==0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select name from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return true;
}

//***********************************************************
public boolean AadharCardNumberExists(String AadharCardNumber) throws DAOExceptions
{
if(AadharCardNumber==null) return false;
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select name from employee where aadhar_card_number = ?");
preparedStatement.setString(1,AadharCardNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
return true;

}

//***********************************************************

public int getCount() throws DAOExceptions
{
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select Count(employee_id) As Count  from employee");
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
int recordsCount=0;
if(resultSet.next())
{
recordsCount = resultSet.getInt("Count");
resultSet.close();
preparedStatement.close();
connection.close();
return recordsCount;
}
resultSet.close();
preparedStatement.close();
connection.close();
return recordsCount;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}

//**************************************************

public int getCountByDesignation(int designationCode) throws DAOExceptions
{
if(designationCode<=0) return 0;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select Count(employee_id) As Count  from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
int recordsCount=0;
if(resultSet.next())
{
recordsCount = resultSet.getInt("Count");
resultSet.close();
preparedStatement.close();
connection.close();
return recordsCount;
}
resultSet.close();
preparedStatement.close();
connection.close();
return recordsCount;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//getCountByDesignation ends


}//Class ends