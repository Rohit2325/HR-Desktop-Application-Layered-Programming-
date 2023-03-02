package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManagerInterface designationManager=null;
private  DesignationManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.codeWiseDesignationsMap = new HashMap<>();
this.titleWiseDesignationsMap = new HashMap<>();
this.designationsSet = new TreeSet<>();
try
{
DesignationInterface designation;
Set<DesignationDTOInterface> dlDesignations;
dlDesignations = new DesignationDAO().getAll();
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation = new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(designation.getCode(),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOExceptions daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager = new DesignationManager();
return designationManager;
}


//********************************************************

public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException;
blException = new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code!=0)
{
blException.addException("code","code should be zero");
}
if(title==null)
{
blException.addException("title","title rquired");
title = "";
}
else
{
title = title.trim();
if(title.length()==0)
{
blException.addException("title","title required");
}
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation :"+title+" exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
designationDAO.add(designationDTO);
designation.setCode(designationDTO.getCode());
code= designationDTO.getCode();
DesignationInterface designationds;
designationds = new Designation();
designationds.setCode(code);
designationds.setTitle(title);
this.codeWiseDesignationsMap.put(designationds.getCode(),designationds);
this.titleWiseDesignationsMap.put(designationds.getTitle().toUpperCase(),designationds);
this.designationsSet.add(designationds);
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//AddMethoad Ends;

//**************************************************************

public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException;
blException = new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code<=0)
{
blException.addException("code","invalid Code");
throw blException;
}
if(title==null)
{
blException.addException("title","title rquired");
title = "";
}
else
{
title = title.trim();
if(title.length()==0)
{
blException.addException("title","title required");
}
}
if(code>0)
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","invalid Code");
throw blException;
}
}
if(title.length()>0)
{
DesignationInterface d  = this.titleWiseDesignationsMap.get(title);
if(d!=null && code==d.getCode())
{
blException.addException("title","Designation :"+title+" exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
DesignationInterface dsDesignation = this.codeWiseDesignationsMap.get(code);
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setTitle(title);
designationDTO.setCode(code);
new DesignationDAO().update(designationDTO);
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle());
this.designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(code,dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}//updateMethoad ends

//**************************************************************

public void removeDesignation(int code) throws BLException
{
BLException blException;
blException = new BLException();
if(code<=0)
{
blException.addException("code","invalid Code");
throw blException;
}
if(code>0)
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","invalid Code");
throw blException;
}
}
try
{
DesignationInterface dsDesignation = this.codeWiseDesignationsMap.get(code);
new DesignationDAO().delete(code);
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
}catch(DAOExceptions daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}// removeMethoad ends

//**************************************************************

public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation;
designation = this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException = new BLException();;
blException.addException("code","invalid code"+code);
throw blException;
}
DesignationInterface d = new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}


//***********************************************************

DesignationInterface getDSDesignationByCode(int code) throws BLException
{
DesignationInterface designation;
designation = this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException = new BLException();;
blException.addException("code","invalid code"+code);
throw blException;
}
return designation;
}

//**************************************************************


public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation;
designation = this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException = new BLException();
blException.addException("title","invalid title"+title);
throw blException;
}
DesignationInterface d = new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

//***********************************************************
public DesignationInterface getDSDesignationByTitle(String title) throws BLException
{
DesignationInterface designation;
designation = this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException = new BLException();
blException.addException("title","invalid title"+title);
throw blException;
}
return designation;
}



//***********************************************************

public int getDesignationCount() 
{
return this.designationsSet.size();
}

public boolean designationCodeExists(int code) 
{
return this.codeWiseDesignationsMap.containsKey(code);
}

public boolean designationTitleExists(String title) 
{
return this.titleWiseDesignationsMap.containsKey(title);
}

public Set<DesignationInterface> designations()
{
Set<DesignationInterface> designations = new TreeSet<>();
this.designationsSet.forEach((designation)->{
DesignationInterface d = new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}
}

