package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME = "designation.data"; 
public void add(DesignationDTOInterface DesignationDTO) throws DAOExceptions
{
if(DesignationDTO==null)
{
throw new DAOExceptions("Designation is null");
}
String title= DesignationDTO.getTitle();
if(title==null)
{
throw new DAOExceptions("Designation is null");
}
title=title.trim();
if(title.length()==0)
{ 
throw new DAOExceptions("Designation is Zero");
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation where title =?"); 
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
connection.close();
preparedStatement.close();
resultSet.close();
throw new DAOExceptions("Designation "+title+ "already exists");
}
preparedStatement.close();
resultSet.close();
preparedStatement = connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next(); 
int code = resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
DesignationDTO.setCode(code);
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//add function ends




public void update(DesignationDTOInterface DesignationDTO)throws DAOExceptions
{
if(DesignationDTO==null) throw new DAOExceptions("Invalid Code");
int code = DesignationDTO.getCode();
if(code<=0) throw new DAOExceptions("Invalid Code");
String title = DesignationDTO.getTitle();
if(title==null)  throw new DAOExceptions("Designation is zero");
title = title.trim();
if(title.length()==0)  throw new DAOExceptions("Designation is zero");
Connection connection;
try
{
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("Select code from designation");
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
int fCode;
String fTitle;
boolean found = false;
while(resultSet.next())
{
fCode = resultSet.getInt("code");
if(fCode==code)
{
found =true;
break;
}
}
if(found==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOExceptions("Invalid Code :"+code);
}
resultSet.close();
preparedStatement.close();

preparedStatement = connection.prepareStatement("Select * from designation");
resultSet = preparedStatement.executeQuery();

while(resultSet.next())
{
fCode = resultSet.getInt("code");
fTitle = resultSet.getString("title");
if(fCode!=code && title.equalsIgnoreCase(fTitle)==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOExceptions("Designation Already Exists :"+title);
}
}
resultSet.close();
preparedStatement.close();

preparedStatement = connection.prepareStatement("update designation set title =? where code = ?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}// update ends


//**********************************************************



public void delete(int code) throws DAOExceptions
{
if(code<=0) throw new DAOExceptions("Invalid Code");
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation");
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
int fCode;
String fTitle;
boolean found = false;
while(resultSet.next())
{
fCode = resultSet.getInt("code");
if(fCode==code)
{
found =true;
break;
}
}
if(found==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOExceptions("Invalid Code :"+code);
}
resultSet.close();
preparedStatement.close();

preparedStatement = connection.prepareStatement("delete from designation where code =?"); 
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}// deleteMethoad Ends

//*************************************************************


public Set<DesignationDTOInterface> getAll() throws DAOExceptions
{
Set<DesignationDTOInterface> designations;
designations= new TreeSet<>();
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation"); 
ResultSet resultSet = preparedStatement.executeQuery();
DesignationDTOInterface designationDTO;
while(resultSet.next())
{
designationDTO = new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title"));
designations.add(designationDTO);
}
return designations;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//getAll ends


//*********************************************************************


public DesignationDTOInterface getByCode(int code) throws DAOExceptions
{
if(code<=0)
{
throw new DAOExceptions("Invalid Code :"+code);
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
int fCode;
String fTitle;
while(resultSet.next())
{
fCode = resultSet.getInt("Code");
fTitle = resultSet.getString("title");
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
}
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//getBycode ends




public DesignationDTOInterface getByTitle(String title) throws DAOExceptions
{
if(title==null)
{
throw new DAOExceptions("Designation is null");
}
title.trim();
if(title.length()==0)
{
throw new DAOExceptions("Designation is Zero");
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where title =?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
int fCode;
String fTitle;
while(resultSet.next())
{
fCode = resultSet.getInt("Code");
fTitle = resultSet.getString("title");
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
}
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//getByTitle ends;



public boolean codeExists(int code) throws DAOExceptions
{
if(code<=0)
{
return false;
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()) 
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}// codeExists end; 



public boolean titleExists(String title) throws DAOExceptions
{
if(title==null || title.trim().length()==0) return false;
title=title.trim();
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where title =?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()) 
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}catch(SQLException sqlException)
{
throw new DAOExceptions(sqlException.getMessage());
}
}//title Exists end


//*************************************************************


public int getCount() throws DAOExceptions
{
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select Count(code) As Count  from designation");
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
}//getCount ends
}