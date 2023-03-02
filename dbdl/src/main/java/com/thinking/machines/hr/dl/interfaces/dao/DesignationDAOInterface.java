package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface DesignationDTO) throws DAOExceptions;
public void update(DesignationDTOInterface DesignationDTO)throws DAOExceptions;
public void delete(int code) throws DAOExceptions;
public Set<DesignationDTOInterface> getAll() throws DAOExceptions;
public DesignationDTOInterface getByCode(int code) throws DAOExceptions;
public DesignationDTOInterface getByTitle(String title) throws DAOExceptions;
public boolean codeExists(int code) throws DAOExceptions; 
public boolean titleExists(String title) throws DAOExceptions;
public int getCount() throws DAOExceptions;
}