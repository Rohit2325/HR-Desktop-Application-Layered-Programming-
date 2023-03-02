package com.thinking.machines.hr.dl.dao;
import java.sql.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection()
{
Connection connection = null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return connection;
}
}