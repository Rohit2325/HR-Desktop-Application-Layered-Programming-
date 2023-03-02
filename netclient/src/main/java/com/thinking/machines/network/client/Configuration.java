package com.thinking.machines.network.client;
import org.xml.sax.*;
import javax.xml.xpath.*;
import java.io.*;
public class Configuration
{
private static String host = "";
private static int port = -1;
private static boolean malformed = false;
static
{
try
{
File file = new File("server.xml");
if(file.exists())
{
InputSource inputSource = new InputSource("server.xml");
XPath xpath = XPathFactory.newInstance().newXPath();
String host = xpath.evaluate("//server/@host",inputSource);
String port = xpath.evaluate("//server/@port",inputSource);
Configuration.host = host;
Configuration.port = Integer.parseInt(port);
}
}catch(Exception exception)
{
malformed = true;
}
}
public static String getHost()
{
return host;
}
public static int getPort()
{
return port;
}
}