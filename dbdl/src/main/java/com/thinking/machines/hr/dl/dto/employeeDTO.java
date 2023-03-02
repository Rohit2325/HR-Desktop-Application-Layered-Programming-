package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;
public class employeeDTO implements employeeDTOInterface
{
private String employeeId;
private String name;
private int designationCode;
private Date dateOfBirth;
private char gender;
private boolean IsIndian;
private BigDecimal basicSalary;
private String PANNumber;
private String AadharCardNumber;
public employeeDTO()
{
this.employeeId="";
this.name="";
this.designationCode=0;
this.dateOfBirth=null;
this.gender=' ';
this.IsIndian=false;
this.basicSalary=null;
this.PANNumber="";
this.AadharCardNumber="";
}
public void setEmployeeId(java.lang.String employeeId)
{
this.employeeId=employeeId;
}
public java.lang.String getEmployeeId()
{
return this.employeeId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender= 'M';
else this.gender= 'F';
}
public char getGender()
{
return this.gender;
}
public void setIsIndian(boolean IsIndian)
{
this.IsIndian=IsIndian;
}
public boolean getIsIndian()
{
return this.IsIndian;
}
public void setBasicSalary(java.math.BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public java.math.BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setPANNumber(java.lang.String PANNumber)
{
this.PANNumber=PANNumber;
}
public java.lang.String getPANNumber()
{
return this.PANNumber;
}
public void setAadharCardNumber(java.lang.String AadharCardNumber)
{
this.AadharCardNumber=AadharCardNumber;
}
public java.lang.String getAadharCardNumber()
{
return this.AadharCardNumber;
}
public boolean equals(Object other)
{
if(!(other instanceof employeeDTOInterface)) return false;
employeeDTOInterface employeeDTO;
employeeDTO = (employeeDTO)other;
return this.employeeId.equalsIgnoreCase(employeeDTO.getEmployeeId());
}
public int compareTo(employeeDTOInterface other)
{
return this.employeeId.compareToIgnoreCase(other.getEmployeeId());
}
public int hashCode()
{
return this.employeeId.toUpperCase().hashCode();
}
}