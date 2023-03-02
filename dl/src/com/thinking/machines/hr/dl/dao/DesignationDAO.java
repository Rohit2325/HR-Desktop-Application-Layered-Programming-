package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
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
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int RecordsCount=0;
String lastGeneratedCodeString;
String RecordsCountString;
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10)
{
lastGeneratedCodeString+=" ";
}
RecordsCountString="0";
while(RecordsCountString.length()<10)
{
RecordsCountString+=" ";
}
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(RecordsCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString =randomAccessFile.readLine().trim();
RecordsCountString = randomAccessFile.readLine().trim();
lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
RecordsCount= Integer.parseInt(RecordsCountString);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
throw new DAOExceptions(title+" already Exists");
}
}
int code = lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
DesignationDTO.setCode(code);
lastGeneratedCode++;
RecordsCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10)
{
lastGeneratedCodeString+=" ";
}
RecordsCountString= String.valueOf(RecordsCount);
while(RecordsCountString.length()<10)
{
RecordsCountString+=" ";
}
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(RecordsCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//add function ends

public void update(DesignationDTOInterface DesignationDTO)throws DAOExceptions
{
if(DesignationDTO==null) throw new DAOExceptions("Invalid Code");
int code = DesignationDTO.getCode();
if(code<=0) throw new DAOExceptions("Invalid Code");
String title= DesignationDTO.getTitle();
if(title==null)  throw new DAOExceptions("Designation is zero");
title = title.trim();
if(title.length()==0)  throw new DAOExceptions("Designation is zero");
try
{
File file = new File(FILE_NAME);
if(!file.exists())
{
throw new DAOExceptions("Invalid code :"+code);
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
int fCode;
String fTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
boolean found = false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode= Integer.parseInt(randomAccessFile.readLine().trim());
if(fCode==code)
{
found =true;
break;
}
else
{
randomAccessFile.readLine();
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine().trim());
fTitle= randomAccessFile.readLine();
if(fCode!=code && title.equalsIgnoreCase(fTitle)==true)
{
randomAccessFile.close();
throw new DAOExceptions("Designation Already Exists :"+title);
}
}
File tmpFile = new File("tmp.data");
if(file.exists()==true) tmpFile.delete();
RandomAccessFile randomAccessFileTmp;
randomAccessFileTmp = new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
randomAccessFileTmp.writeBytes(randomAccessFile.readLine());
randomAccessFileTmp.writeBytes("\n");
randomAccessFileTmp.writeBytes(randomAccessFile.readLine());
randomAccessFileTmp.writeBytes("\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(code!=fCode)
{
randomAccessFileTmp.writeBytes(String.valueOf(fCode));
randomAccessFileTmp.writeBytes("\n");
randomAccessFileTmp.writeBytes(fTitle);
randomAccessFileTmp.writeBytes("\n");
}
else
{
randomAccessFileTmp.writeBytes(String.valueOf(code));
randomAccessFileTmp.writeBytes("\n");
randomAccessFileTmp.writeBytes(title);
randomAccessFileTmp.writeBytes("\n");
}
}
randomAccessFile.seek(0);
randomAccessFileTmp.seek(0);
while(randomAccessFileTmp.getFilePointer()<randomAccessFileTmp.length())
{
randomAccessFile.writeBytes(randomAccessFileTmp.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(randomAccessFileTmp.length());
randomAccessFileTmp.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}// update ends

//**********************************************************

public void delete(int code) throws DAOExceptions
{
if(code<=0) throw new DAOExceptions("Invalid Code");
try
{
File file = new File(FILE_NAME);
if(!file.exists())
{
throw new DAOExceptions("Invalid code :"+code);
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
int fCode;
String fTitle;
randomAccessFile.readLine();
int RecordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
boolean found = false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode= Integer.parseInt(randomAccessFile.readLine());
if(fCode==code)
{
found =true;
break;
}
else
{
randomAccessFile.readLine();
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
File tmpFile = new File("tmp.data");
if(file.exists()==true) tmpFile.delete();
RandomAccessFile randomAccessFileTmp;
randomAccessFileTmp = new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
randomAccessFileTmp.writeBytes(randomAccessFile.readLine());
randomAccessFileTmp.writeBytes("\n");
randomAccessFileTmp.writeBytes(randomAccessFile.readLine());
randomAccessFileTmp.writeBytes("\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(code!=fCode)
{
randomAccessFileTmp.writeBytes(String.valueOf(fCode));
randomAccessFileTmp.writeBytes("\n");
randomAccessFileTmp.writeBytes(fTitle);
randomAccessFileTmp.writeBytes("\n");
}
}
randomAccessFileTmp.seek(0);
randomAccessFile.seek(0);
while(randomAccessFileTmp.getFilePointer()<randomAccessFileTmp.length())
{
randomAccessFile.writeBytes(randomAccessFileTmp.readLine());
randomAccessFile.writeBytes("\n");
}
RecordsCount--;
String RecordsCountString = String.valueOf(RecordsCount);
while(RecordsCountString.length()<10)
{
RecordsCountString+=" ";
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(RecordsCountString);
randomAccessFile.setLength(randomAccessFileTmp.length());
randomAccessFileTmp.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}// deleteMethoad Ends

//*************************************************************


public Set<DesignationDTOInterface> getAll() throws DAOExceptions
{
Set<DesignationDTOInterface> designations;
designations= new TreeSet<>();
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) return designations;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return designations;
}
randomAccessFile.readLine();
int recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
return designations;
}
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
designationDTO= new DesignationDTO();
int fCode=Integer.parseInt(randomAccessFile.readLine());
String fTitle= randomAccessFile.readLine();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
designations.add(designationDTO);
}
randomAccessFile.close();
return designations;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//getAll ends

public DesignationDTOInterface getByCode(int code) throws DAOExceptions
{
if(code<=0)
{
throw new DAOExceptions("Invalid Code :"+code);
}
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)
{
throw new DAOExceptions("Invalid Code :"+code); 
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
randomAccessFile.readLine();
int recordsCount;
recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOExceptions("Invalid Code :"+code);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
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
File file=new File(FILE_NAME);
if(file.exists()==false)
{
throw new DAOExceptions("Invalid title :"+title); 
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid title :"+title);
}
randomAccessFile.readLine();
int recordsCount;
recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
throw new DAOExceptions("Invalid title :"+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOExceptions("Invalid title :"+title);
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
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
File file = new File(FILE_NAME);
if(file.exists()==false)
{
return false;
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
} 
randomAccessFile.readLine();
int recordsCount =Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
return false;
} 
int fCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode= Integer.parseInt(randomAccessFile.readLine());
if(fCode==code)
{
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}// codeExists end; 

public boolean titleExists(String title) throws DAOExceptions
{
if(title==null || title.trim().length()==0) return false;
title=title.trim();
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
int recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
return false;
}
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle= randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
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
}//title Exists end


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
int recordsCount;
recordsCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordsCount==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.close();
return recordsCount;
}catch(IOException ioException)
{
throw new DAOExceptions(ioException.getMessage());
}
}//getCount ends
}