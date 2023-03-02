package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public interface employeeDAOInterface
{
public void add(employeeDTOInterface employeeDTO)throws DAOExceptions;
public void update(employeeDTOInterface employeeDTO) throws DAOExceptions;
public void delete(String employeeId)throws DAOExceptions;
public Set<employeeDTOInterface> getAll() throws DAOExceptions;
public Set<employeeDTOInterface> getByDesignationCode(int designationCode) throws DAOExceptions;
public boolean isDesignationAlloted(int designationCode) throws DAOExceptions;
public employeeDTOInterface getByEmployeeId(String employeeId) throws DAOExceptions;
public employeeDTOInterface getByPANNumber(String panNumber) throws DAOExceptions;
public employeeDTOInterface getByAadharCardNumber(String AadharCardNumber) throws DAOExceptions;
public boolean employeeIdExists(String employeeId) throws DAOExceptions;
public boolean PANNumberExists(String panNumber) throws DAOExceptions;
public boolean AadharCardNumberExists(String AadharCardNumber) throws DAOExceptions;
public int getCount() throws DAOExceptions;
public int getCountByDesignation(int designationCode) throws DAOExceptions;
}