package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class DesignationDTO implements DesignationDTOInterface
{
private int code;
private String title;
public DesignationDTO()
{
this.code=0;
this.title="";
} 
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(java.lang.String title)
{
this.title=title;
}
public java.lang.String getTitle()
{
return this.title;
}
public int compareTo(DesignationDTOInterface DesignationDTO)
{
return this.code-DesignationDTO.getCode();
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTOInterface)) return false;
DesignationDTOInterface DesignationDTO;
DesignationDTO = (DesignationDTO)other;
return this.code== DesignationDTO.getCode();
}
public int hashCode()
{
return this.code;
}
}

