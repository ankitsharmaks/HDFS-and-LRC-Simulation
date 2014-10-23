/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

/**
 *
 * @author Ankit
 */
//import getfreeNodes.java;
import java.awt.*;
import java.io.*;

import java.util.*;


public class divide  
{


	static	int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
	
public static void main(String[] args)
	
{
//divide testr=new divide();
//Scanner in= new Scanner(System.in);
//dividefileintoblocks("C:\\Users\\hp\\Desktop\\media\\codes\\final\\Arziyan.mp3");//,1024*1024,10,3);


}
//	System.out.println("enter file name or full file path");
public static void dividefileintoblocks(String filepath, String dirpath) throws IOException//,int blksize ,int numNodes, int replication )

{
	
    File originalfile = new File(filepath);
    
	String s = filepath;
String file=null;
if(s.contains("/"))
{

file = s.substring(s.lastIndexOf("/"));
System.out.println("/");
}
else if(s.contains("\\"))
{
	file = s.substring(s.lastIndexOf("\\"));
System.out.println("\\");
	
}
else{ System.out.print("file extension not found");}
String extension = file.substring(file.indexOf(".")); // .tar.gz
	

String newFileName=dirpath+"\\HDFSReg_"+originalfile.getName();

System.out.println(newFileName);	

String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
 String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
    	
    	Properties ps = new Properties();
    	
    	//File conffile;
    	//FileOutputStream conf;
 	try
 		{
 			
    	//conffile = new File("config.properties");
    	 //conf =new FileOutputStream(conffile);
    	ps.load(new FileInputStream("config.properties"));
		//System.out.println(ps.getProperty("NumberofNodes"));
		String numnod;
		String blksiz;
		String repfactr;
		
		numnod = ps.getProperty("NumberofNodes");
		NumofNodes =Integer.parseInt(numnod );
		
		repfactr = ps.getProperty("replicationFactor");
		replicationFactor =Integer.parseInt(repfactr );
		
		blksiz = ps.getProperty("BlockSize");
		BlockSize = Integer.parseInt(blksiz);
		System.out.println(NumofNodes); 
		ps.setProperty("NumberofNodes", numnod);	
		ps.setProperty("replicationFactor", repfactr);	
		ps.setProperty("BlockSize", blksiz);		
		ps.setProperty("newFileName", newFileName);	
			
    	 
		
		ps.store(new FileOutputStream("config.properties"), null);
			}
			catch(Exception e)
			{System.out.println(e);
			}
			finally{
			System.out.println("finally propts loaded!!");
			System.out.println("numofnodes = "+NumofNodes);
			System.out.println("repfactor = "+replicationFactor);
			System.out.println("blocksize = "+BlockSize);
		//	conf.close();
			}

	
	
	
    	

		
	

	String fname;
	fname=filepath;			
	int Block_Size=BlockSize;	 // size of the block
                            // which can be entered by user
//String fname="test.mp4";

  	
  	

	File ifile = new File(fname); 
	FileInputStream fis;
	String newName;


	FileOutputStream chnk;
	int fileSize = (int) ifile.length();
	int nChunks = 0;
	int read = 0; 
	
	int readLength =Block_Size;
	byte[] byteChunk;
	int start = 0 ;

	int chnkcount;
	int numofNodes=NumofNodes;
	
	int replicfactor=replicationFactor;
	chnkcount= 1 + (fileSize / Block_Size);
	System.out.println("chnkcount=  "+chnkcount);

try 
	{
   		fis = new FileInputStream(ifile);
       	System.out.println("filesize :  " + fileSize);
    //int size = (int)ifile.length();
    
    
    
    Writer wrt = null;
    
    File fil = new File("nameNode.txt");                    // name node file
    wrt = new BufferedWriter(new FileWriter(fil)); // to store which block is stored where
    
    
    String dir =dirpath+"/hdfs/";
    File ff = new File(dir);

	if(ff.exists())
	{	
		failNodes.deletetheFolder(ff);
		ff.mkdirs();
	}
	else
	{
		ff.mkdir();
	}
	
	String[] nodepaths = new String[numofNodes];
	
	for(int j=0; j < numofNodes ; j++)
	{
			
			//dir = null;
			String temp = null;
			String nod = dirpath+"\\hdfs\\node00";
			temp = dirpath+"\\hdfs\\node0"+j;
			File saveDir = new File(temp);
			
			
			if(!saveDir.exists())
				{	
				saveDir.mkdirs();
				}
			nodepaths[j]=temp;
	}
	
	File nodelist = new File("Nodelist.txt");
	nodelist.createNewFile();
	BufferedWriter writeNodes;
	//FileOutputStream nodeliststrm;
	//nodeliststrm = new FileOutputStream(nodelist);
	
	writeNodes = new BufferedWriter(new FileWriter(nodelist));
	
	for(int p=0; p < nodepaths.length ; p++)
	{
		//nodeliststrm.write(nodepaths[p]);
		writeNodes.write(nodepaths[p]);
       	writeNodes.write("\n");
       	//System.out.println(p);
	}
	
	
	
    writeNodes.close();
  /*  String currentdir= System.getProperty("user.dir");
       String[] nodepaths = new String[numofNodes];
       nodepaths[0]="hdfs/node00/";
       nodepaths[1]="hdfs/node01/";
       nodepaths[2]="hdfs/node02/";
       nodepaths[3]="hdfs/node03/";
       nodepaths[4]="hdfs/node04/";
       nodepaths[5]="hdfs/node05/";
       nodepaths[6]="hdfs/node06/";
       nodepaths[7]="hdfs/node07/";
       nodepaths[8]="hdfs/node08/";
       nodepaths[9]="hdfs/node09/";
       
    */
    
    while (fileSize > 0)
    	 {
        
        if (fileSize <= Block_Size) 
       	{
            readLength = fileSize;
        }
        
        byteChunk = new byte[readLength];
        read = fis.read(byteChunk,0, readLength);
        
        //System.out.println(read);
      
        fileSize -= read;
        
        
        assert(read==byteChunk.length);
        
        nChunks++;
        
       
       
       String[] storepaths=new String[replicfactor];
       getfreeNodes div = new  getfreeNodes();
       storepaths = div.getfreenodes(nodepaths);//,replicfactor);
       
       
        String temp="test";
        int tempint= nChunks -1;
        newName=String.format("%s_part%d",temp,tempint);
       // String nwpath="C:\Users\hp\Desktop\media" ;
       
       	wrt.write(newName);
       	wrt.write("    ");
       
       for(int k=0;k<replicfactor;k++)
       
       {
       
        File dirc = new File(storepaths[k]);
		dirc.mkdirs();
		
		
		File permfile = new File(dirc, newName);
		permfile.createNewFile();
        chnk = new FileOutputStream(permfile);
        chnk.write(byteChunk);
        
        wrt.write(permfile.getAbsolutePath());
        wrt.write("    ");
       // System.out.println(permfile);
        chnk.flush();
        chnk.close();
        
        
       }
       
        wrt.write("\n");
        
        byteChunk = null;
        chnk = null;    
        
   			
        	
       
  }
  
 		 
  
        fis.close();
    	fis = null;
    	
    	wrt.close();
    
    
}

catch(Exception e)
{
	System.out.print(e);
}

finally
{
 /*try {
               // if (fis != null)  {
                    fis.close();
                    fis = null;
               // }
        } 
        catch (IOException e)
         {
                e.printStackTrace();
          }	   	*/
}

System.out.println("divided and stored successfully");

}  //end of main

	public String toBinary(int a)
	{
	
		return Integer.toBinaryString(a);
	}



}