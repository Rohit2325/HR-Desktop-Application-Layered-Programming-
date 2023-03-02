package com.thinking.machines.hr.dl.interfaces.dto;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.io.*;
public interface employeeDTOInterface extends Comparable<employeeDTOInterface>,java.io.Serializable
{
public void setEmployeeId(String employeeId);
public String getEmployeeId();
public void setName(String name);
public String getName();
public void setDesignationCode(int designationCode);
public int getDesignationCode();
public void setDateOfBirth(Date DateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public  void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();
public void setPANNumber(String PanNumber);
public String getPANNumber();
public void setAadharCardNumber(String AadharCardNumber);
public String getAadharCardNumber(); 
}