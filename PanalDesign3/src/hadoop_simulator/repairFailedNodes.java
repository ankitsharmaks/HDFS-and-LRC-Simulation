/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

/**
 *
 * @author Ankit
 */

import java.awt.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class repairFailedNodes 
{
	
	static	int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
	
	static boolean repairedSuccessfully = true;
	
        
        
	public static boolean repairFailedNodes()
		{
		
		
		Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
            String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		//newfilename = prop.getProperty("newFileName");
    		//System.out.println(newfilename);
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		System.out.println("part 1 " +ex);
        }
		
		
		
			String[] failedNodes;
			failedNodes = getFailedNodelist.getFailedNodelist();
			//get the list of failed nodes
			
			System.out.println("getfailednodelist successfully done");
			
			for(int tmp=0; tmp< failedNodes.length ; tmp++)
			{
				String tmpStr = failedNodes[tmp];
				System.out.println("failed node "+tmpStr);
				File tmpfile = new File(tmpStr);
				if(!tmpfile.exists())
				{
					tmpfile.mkdirs();
				}
			}
			
		System.out.println("no. of failed nodes  "+failedNodes.length);	
			
		for (int tmpr =0 ; tmpr < failedNodes.length && repairedSuccessfully ==true ; tmpr++)
		{
				
				
				
	//	if(repairedSuccessfully == true)
		//{
							
			String tempcurrentRepair;
			tempcurrentRepair = failedNodes[tmpr];
			
			//String[] tokens = tempcurrentRepair.split("/");
			//System.out.println(tokens[1]);
			
                        
                        int indx = tempcurrentRepair.indexOf("hdfs\\node");
			System.out.println("index .hdfs node "+indx);
			String currentRepair ;//= tokens[1];
			currentRepair = tempcurrentRepair.substring(indx);
			System.out.println("current repair "+currentRepair);
			
			//String currentRepair = tokens[1];
			
			//System.out.println("current repair "+currentRepair);
			
				try
				{
					
					
				
				
				int replicfactor=replicationFactor;	 // from super class
				//String fnm="dataNode.txt";	
				File file1 = new File("nameNode.txt");
				FileReader freadr = new FileReader(file1);
				BufferedReader br = new BufferedReader(freadr);
			 
			 	StringTokenizer stoken; 
				
				String name ="";
				String[] location = new String[replicfactor];
				//BufferedReader br = new BufferedReader(new FileReader(file1));
			    
			     
			        //StringBuilder sb = new StringBuilder();
			     
			     
			        String line = br.readLine();
			        //System.out.println("tmpr "+tmpr);
			        
			        stoken = null;
			    	boolean check=true;
			        //while (line.contains(currentRepair)) 
                            while(check) 
                            {



                             if( line != null && line.contains(currentRepair) )    
                                  {
                                       // System.out.println();


                                                      //line = br.readLine();
                                             if(line == null)
                                             {
                                             break;
                                             }	

                                             stoken = new StringTokenizer(line);


                                                     if(stoken.hasMoreTokens())
                                                    {
                                                    name=stoken.nextToken();
                                                    System.out.println(" in line of block : "+name);
                                                    }

                                            //System.out.println(stoken.countTokens());


                                                    int i=0;
                                                    String s2="";
                                                    while( stoken.hasMoreTokens() && i < replicfactor)
                                            {
                                                             s2 =stoken.nextToken();

                                                    location[i]=s2;//stoken.nextToken();

                                                    System.out.println(location[i]);
                                                    i++;
                                            }
                                       System.out.println("above locationas stored in array");



                                            File deletedFile=null;
                                            String deletedFileString;
                                            File existingFile=null;
                                            String existingFileString;

                                            boolean delfile=true;
                                            boolean existfile=true;

                                            for(i=0;i<replicfactor ; i++)
                                            {
                                                    if(location[i].contains(currentRepair) &&  (delfile==true) )
                                                    {
                                                            deletedFileString = location[i];
                                                            deletedFile = new File(deletedFileString);
                                                            System.out.println("deleted file  "+deletedFile);
                                                            //break;
                                                            delfile=false;
                                                    }
                                            }

                                            i=0;
                                            while( i<replicfactor  && existfile==true)
                                            {   
                                                    System.out.println(" inside loop ");
                                                    File temp = new File(location[i]);
                                                    if(temp.exists())
                                                    {
                                                            existingFileString = location[i];
                                                            //System.out.println("exist file location  "+existingFileString);
                                                            existingFile = new File(existingFileString) ;
                                                            System.out.println("exist file  "+existingFile);
                                                            //break;
                                                            existfile=false;
                                                            break;
                                                    }
                                                    i++;
                                            }

                                            if(existfile == true)
                                            {
                                                    repairedSuccessfully = false;
                                            }	


                                   if(existfile == false) 
                                   {

                                    try{

                                       FileInputStream finpt;
                                       FileOutputStream foutpt;
                                       finpt = new FileInputStream(existingFile);
                                       foutpt = new FileOutputStream(deletedFile);
                                       byte[] fileBytes;
                                               int bytRead = 0;
                                               //bytRead = finstream.read(fileBytes, 0,(int)  existingFile.length());

                                            fileBytes = new byte[(int) existingFile.length()];
                                            bytRead = finpt.read(fileBytes, 0,(int)  existingFile.length());
                                            assert(bytRead == fileBytes.length);
                                            assert(bytRead == (int) existingFile.length());
                                            foutpt.write(fileBytes);
                                            foutpt.flush();
                                            fileBytes = null;
                                            finpt.close();
                                            finpt = null;
                                            foutpt.close();   

                                           System.out.println("delted file stored  :"+deletedFile); 	 
                                    }
                                    catch(Exception e)
                                    {
                                            System.out.println("after existfile :"+e);
                                    }	
                                   }	
                                    line = br.readLine();   	 
                                  }


                                        /*File frestored;

                                        int atemp=location.length;
                                        fretained=getFile(location,atemp);

                                        filesretained.add(fretained);	
                                        */
                                  else{

                                        line = br.readLine();
                                        if(line == null)
                                        {
                                            check = false;
                                        }
                                  }


                                    //System.out.println(location[0]);	




                    }
			    	 
			    	br.close();
				}
				catch(Exception e)
				{
					System.out.println("inside method repairFailednodes"+e);
				}
}			
			
			
			
			
			
			
			return repairedSuccessfully;
			}
	  // end of function repairFailedNodes	
		
		
		
		
		
		
		
		
		
		
	
		
public static void main(String[] args)
{
	
	long startTime = System.currentTimeMillis();
boolean b= 	repairFailedNodes();
long endTime   = System.currentTimeMillis();
			long regenerateTime = endTime - startTime ; 
			System.out.println("T. tm.   "+regenerateTime);	 
			

System.out.println("repair "+ b);
}		
		
		
	
}