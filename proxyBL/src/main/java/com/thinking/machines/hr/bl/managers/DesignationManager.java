package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import com.thinking.machines.network.client.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private static DesignationManagerInterface designationManager=null;
private  DesignationManager() throws BLException
{
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
if(blException.hasException())
{
throw blException;
}
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
if(response.hasException())
{
blException = (BLException)response.getException();
throw blException;
}
DesignationInterface d;
d = (DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
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
throw blException;
}
}

Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
if(response.hasException())
{
blException = (BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
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

Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION));
request.setArguments(code);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
if(response.hasException())
{
blException = (BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}// removeMethoad ends

//**************************************************************

public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException;
blException = new BLException();
if(code<=0) 
{
blException.addException("code","invalid Code");
throw blException;
}
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE));
request.setArguments(code);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
if(response.hasException())
{
blException = (BLException)response.getException();
throw blException;
}
DesignationInterface designation;
designation = (DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}//getDesignationByCode


//***********************************************************



public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException;
blException = new BLException();
if(title==null) 
{
blException.addException("title","invalid title");
throw blException;
}
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE));
request.setArguments(title);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
if(response.hasException())
{
blException = (BLException)response.getException();
throw blException;
}
DesignationInterface designation;
designation = (DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}//getDesignationByTitle ends

//***********************************************************



public int getDesignationCount() 
{
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
Integer designationCount = (Integer)response.getResult();
return designationCount;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}//getDesignationCount ends

//***********************************************************

public boolean designationCodeExists(int code) 
{
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));
request.setArguments(code);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
Boolean designationCodeExists = (Boolean)response.getResult();
return designationCodeExists;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}//designationCodeExists ends

//***********************************************************

public boolean designationTitleExists(String title) 
{
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));
request.setArguments(title);
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
Boolean designationTitleExists = (Boolean)response.getResult();
return designationTitleExists;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}//designationTitleExists ends

//***********************************************************

public Set<DesignationInterface> designations()
{
Request request = new Request();
request.setManager(Managers.getType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS));
NetworkClient networkClient = new NetworkClient();
try
{
Response response = networkClient.send(request);
Set<DesignationInterface> designations;
designations = (Set<DesignationInterface>)response.getResult();
return designations;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}//designations ends
}//class ends

