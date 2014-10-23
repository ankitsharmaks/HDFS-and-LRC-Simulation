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


public class failNodes 
{
	
	
		static	int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
	//static String newfilname ; 
	
	public static void deletetheFolder(File fol) 
		  
	{    
		
	    File[] filesinfolder = fol.listFiles();
	    if(filesinfolder!=null)
	     { 
	        // takes files and delete the folder
	        // and files inside it recursively
	        for(File f: filesinfolder) 
	        {
	        if(f.isDirectory()) 
	        	{
	                deletetheFolder(f);
	            }
	        else
	        	 {
	                f.delete();
	            }
	        }
	    }    // recursivesteps
    	fol.delete();
	}
	
	public static void failNodes(int nodestoFail)
		
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
        }
	
		
		
		
		int cnt = 0;
		String[] nodesList = new String[NumofNodes];

		try{
		
			File filenodes = new File("Nodelist.txt");
			FileReader nodereader = new FileReader(filenodes);
			BufferedReader nodebuffer = new BufferedReader(nodereader);
			
			//String[] nodesList = new String[NumofNodes];
			
			String nodeline = nodebuffer.readLine();
			
			//int cnt=0;
			while(nodeline != null && cnt < NumofNodes )
			{
				nodesList[cnt]=nodeline;
				//System.out.println(nodeline);
				cnt++;
				nodeline= nodebuffer.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
			
	//	Random rndm = new Random();
	//	int numofFailNodes =1 + rndm.nextInt(replicationFactor - 1);
		//System.out.println(numofFailNodes);
		
		int numofFailNodes = nodestoFail ;
		 
		for(cnt = 0 ; cnt<numofFailNodes ; cnt++)
		{
			Random randm = new Random();
			int indexofFailNodes = randm.nextInt(NumofNodes);
			System.out.println(indexofFailNodes);
			
			String failstr;
			failstr = nodesList[indexofFailNodes];
			
			File tempFile = new File(failstr);
			if(tempFile.isDirectory()) 
			{
				deletetheFolder(tempFile);
			}
			else 
			{
				cnt--;
			}
			
		}
		
		
			
		
		 
	}
	
	/*
	catch(Exception e)
    	{
    		System.out.print("A  "+e);
    	 }
	*/
	
	public static void main(String[] args)
	{
	failNodes(8);
	}
	
	
	
}