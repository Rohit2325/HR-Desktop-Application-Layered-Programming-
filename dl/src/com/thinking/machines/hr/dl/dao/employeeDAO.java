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
if(designationCode<=0) throw new DAOExceptions("designation code is invalid "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
boolean isDesignationCodeValid = designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false) throw new DAOExceptions("desigantion code is invalid "+designationCode);
Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOExceptions("date of birth is null");
char gender = employeeDTO.getGender();
boolean IsIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1) throw new DAOExceptions("Salary is negative");
String PANNumber = employeeDTO.getPANNumber();
if(PANNumber==null) throw new DAOExceptions("Pan Number is Null");
PANNumber = PANNumber.trim();
if(PANNumber.length()==0) throw new DAOExceptions("length of panNumber is zero");
String AadharCardNumber = employeeDTO.getAadharCardNumber();
if(AadharCardNumber== null) throw new DAOExceptions("Aadhar Card Number is null");
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) throw new DAOExceptions("length of Aadhar Card Number is Zero");
try
{
int lastGeneratedEmployeeId = 10000000;
String lastGeneratedEmployeeIdString = "";
int recordsCount = 0;
String recordsCountString ="";
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString = String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordsCountString = String.format("%-10s","0");
randomAccessFile.writeBytes(recordsCountString+"\n");
}
else
{
lastGeneratedEmployeeId = Integer.parseInt(randomAccessFile.readLine().trim());
recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
}
boolean PanNumberExists,AadharCardNumberExists;
PanNumberExists = false;
AadharCardNumberExists =false;
String fPanNumber;
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fPanNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(PanNumberExists==false && fPanNumber.equalsIgnoreCase(PANNumber))
{
PanNumberExists =true;
}
if(AadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(AadharCardNumber))
{
AadharCardNumberExists= true;
}
if(PanNumberExists && AadharCardNumberExists) break;
}
if(PanNumberExists && AadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Pan Number and Aadhar Card Number exists "+PANNumber+""+AadharCardNumber);
}
if(PanNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Pan number Exists "+PANNumber);
}
if(AadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Aadhar card number Exists "+AadharCardNumber);
}
lastGeneratedEmployeeId++;
employeeId= "A"+lastGeneratedEmployeeId;
recordsCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(IsIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(PANNumber+"\n");
randomAccessFile.writeBytes(AadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString= String.format("%-10d",lastGeneratedEmployeeId);
recordsCountString = String.format("%-10d",recordsCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordsCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//add methoad ends


//*********************************************************



public void update(employeeDTOInterface employeeDTO) throws DAOExceptions
{
if(employeeDTO==null) throw new DAOExceptions("Employee is null");
String employeeId= employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId :"+employeeId);
if(employeeId.length()==0) throw new DAOExceptions("employeeID is of Zero length "+employeeId);
String name = employeeDTO.getName();
if(name==null) throw new DAOExceptions("name is null");
name=name.trim();
if(name.length()==0) throw new DAOExceptions("length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOExceptions("designation code is invalid "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
boolean isDesignationCodeValid = designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false) throw new DAOExceptions("desigantion code is invalid "+designationCode);
Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOExceptions("date of birth is null");
char gender = employeeDTO.getGender();
if(gender==' ') throw new DAOExceptions("Gender is not set to male/female");
boolean IsIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1) throw new DAOExceptions("Salary is negative");
String PANNumber = employeeDTO.getPANNumber();
if(PANNumber==null) throw new DAOExceptions("Pan Number is Null");
PANNumber = PANNumber.trim();
if(PANNumber.length()==0) throw new DAOExceptions("length of panNumber is zero");
String AadharCardNumber = employeeDTO.getAadharCardNumber();
if(AadharCardNumber== null) throw new DAOExceptions("Aadhar Card Number is null");
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) throw new DAOExceptions("length of Aadhar Card Number is Zero");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOExceptions("Invalid EmployeeID :"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid EmployeeID :"+employeeId);
}
String fEmployeeId;
String fName;
int fDesignationCode;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
int x;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt=0;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt = randomAccessFile.getFilePointer();
fEmployeeId = randomAccessFile.readLine();
for(x=1;x<=6;x++) randomAccessFile.readLine();
fPanNumber =randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound = true;
}
if(panNumberFound==false && fPanNumber.equalsIgnoreCase(PANNumber))
{
panNumberFound =  true;
panNumberFoundAgainstEmployeeId = fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(AadharCardNumber))
{
aadharCardNumberFound =true;
aadharCardNumberFoundAgainstEmployeeId = fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}//loop ends
if(employeeIdFound==false) 
{
randomAccessFile.close();
throw new DAOExceptions("Invalid EmployeeId :"+employeeId); 
}
boolean panNumberExists=false;
if(panNumberFound==true && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists= true;
}
boolean aadharCardNumberExists = false;
if(aadharCardNumberFound==true && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists = true;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Pan Number ("+PANNumber+") and Aadhar Card Number Exists ("+AadharCardNumber+")");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Pan Number ("+PANNumber+") Exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOExceptions("Aadhar Card Number ("+AadharCardNumber+") Exists");
}
File tmpFile = new  File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++) randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(IsIndian+"\n");
randomAccessFile.writeBytes(basicSalary+"\n");
randomAccessFile.writeBytes(PANNumber+"\n");
randomAccessFile.writeBytes(AadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}// update Ends

//************************************************************

public void delete(String employeeId)throws DAOExceptions
{
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId :"+employeeId);
if(employeeId.length()==0) throw new DAOExceptions("employeeID is of Zero length "+employeeId);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOExceptions("Invalid EmployeeID :"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid EmployeeID :"+employeeId);
}
String fEmployeeId;
int x;
boolean employeeIdFound=false;
long foundAt=0;
randomAccessFile.readLine();
int recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundAt = randomAccessFile.getFilePointer();
fEmployeeId = randomAccessFile.readLine();
for(x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound = true;
break;
}
}//loop ends
if(employeeIdFound==false) 
{
randomAccessFile.close();
throw new DAOExceptions("Invalid EmployeeId :"+employeeId); 
}
File tmpFile = new  File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordsCount--;
String recordsCountString = String.format("%-10d",recordsCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordsCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}// delete ends


//********************************************************


public Set<employeeDTOInterface> getAll() throws DAOExceptions
{
Set<employeeDTOInterface> employees = new TreeSet<>();
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
employeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
char fGender;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine())); 
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//*******************************************************

public Set<employeeDTOInterface> getByDesignationCode(int designationCode) throws DAOExceptions
{
Set<employeeDTOInterface> employees = new TreeSet<>();
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
employeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
String EmployeeId;
String Name;
char fGender;
int fdesignationCode;
int x;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
EmployeeId = randomAccessFile.readLine();
Name = randomAccessFile.readLine();
fdesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(fdesignationCode!=designationCode)
{
for(x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId(EmployeeId);
employeeDTO.setName(Name);
employeeDTO.setDesignationCode(fdesignationCode); 
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender== 'F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//getByDesignationCode

//*****************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOExceptions
{
if(designationCode<=0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int DesignationCode=0;
int x=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(DesignationCode==designationCode) 
{
randomAccessFile.close();
return true;
}
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//*****************************************************

public employeeDTOInterface getByEmployeeId(String employeeId) throws DAOExceptions
{
if(employeeId==null) throw new DAOExceptions("Invalid EmployeeId. :"+employeeId );
employeeId = employeeId.trim();
if(employeeId.length()==0) throw new DAOExceptions("Zero length :"+employeeId);
try
{
File file =  new File(FILE_NAME);
if(file.exists()==false) throw new DAOExceptions("Invalid EmployeeId. :"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOExceptions("Invalid EmployeeId. :"+employeeId);
}
String fEmployeeId;
int x;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeDTOInterface employeeDTO;
employeeDTO= new employeeDTO();
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
employeeDTO.setGender((randomAccessFile.readLine().charAt(0)=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOExceptions("Invalid employeeId. :"+employeeId);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}


}//getByEmployeeId ends;

//*****************************************************


public employeeDTOInterface getByPANNumber(String panNumber) throws DAOExceptions
{
if(panNumber==null) throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
panNumber = panNumber.trim();
if(panNumber.length()==0) throw new DAOExceptions("Zero length");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth = null;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName       = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());

try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPanNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
employeeDTOInterface employeeDTO;
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//*********************************************************

public employeeDTOInterface getByAadharCardNumber(String AadharCardNumber) throws DAOExceptions
{
if(AadharCardNumber==null) throw new DAOExceptions("Invalid Aadhar Card Number. :"+AadharCardNumber);
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) throw new DAOExceptions("Zero length");
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOExceptions("Invalid Aadhar Card Number. :"+AadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Aadhar Card Number. :"+AadharCardNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth = null;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName       = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPanNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(AadharCardNumber))
{
employeeDTOInterface employeeDTO;
employeeDTO = new employeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOExceptions("Invalid Aadhar Card Number. :"+AadharCardNumber);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//****************************************************

public boolean employeeIdExists(String employeeId) throws DAOExceptions
{
if(employeeId==null) return false;
employeeId = employeeId.trim();
if(employeeId.length()==0) return false;
try
{
File file =  new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return false;
}
String fEmployeeId;
int x;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//employeeIdExists ends

//**********************************************

public boolean PANNumberExists(String panNumber) throws DAOExceptions
{
if(panNumber==null) return false;
panNumber = panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid PAN Number. :"+panNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth = null;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName       = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());

try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPanNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//***********************************************************
public boolean AadharCardNumberExists(String AadharCardNumber) throws DAOExceptions
{
if(AadharCardNumber==null) return false;
AadharCardNumber = AadharCardNumber.trim();
if(AadharCardNumber.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth = null;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName       = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
throw new DAOExceptions(parseException.getMessage());
}
fGender = randomAccessFile.readLine().charAt(0);
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPanNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(AadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//***********************************************************
public int getCount() throws DAOExceptions
{
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int RecordCount = Integer.parseInt(randomAccessFile.readLine().trim());
return RecordCount;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}

//**************************************************

public int getCountByDesignation(int designationCode) throws DAOExceptions
{
if(designationCode<=0) return 0;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int Count=0;
int DesignationCode=0;
int x=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(DesignationCode==designationCode) Count++;
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return Count;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//getCountByDesignation ends
}