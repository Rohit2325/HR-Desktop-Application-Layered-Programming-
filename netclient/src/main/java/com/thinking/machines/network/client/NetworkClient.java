package com.thinking.machines.network.client;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.net.*;
import java.io.*;
public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte objectBytes[] = baos.toByteArray();
long requestLength = objectBytes.length;
byte header[]= new byte[1024];
long x;
x=requestLength;
int i=1023;
while(x>0)
{
header[i] = (byte)(x%10);
x=x/10;
i--;
}
Socket socket = new Socket("localhost",5500);
OutputStream os = socket.getOutputStream();
InputStream is = socket.getInputStream();
os.write(header,0,1024);
os.flush();
int bytesReadCount;
byte ack[] = new byte[1];
while(true)
{
bytesReadCount = is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
long bytesToSend = requestLength;
int chunkSize = 4096;
int j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize = (int)bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
}catch(Exception exception)
{
System.out.println(exception);
}
return null;

}//sendMethod ends
}//class ends