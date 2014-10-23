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

public class getFailedNodelist 

{
	
		static	int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
	//static String newfilname ; 
		
		public static String[] getFailedNodelist()
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
    	//	System.out.println(newfilename);
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	
			
			
				int cnt = 0;
				String[] nodesList = new String[NumofNodes];
				String[] FailedNodesList;
				
				ArrayList<String> failednodes = new ArrayList<String>();
				
				
				try{
				
					File filenodes = new File("Nodelist.txt");
					FileReader nodereader = new FileReader(filenodes);
					BufferedReader nodebuffer = new BufferedReader(nodereader);
					
					
					
					String nodeline = nodebuffer.readLine();
					
					
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
					System.out.println("exception in getFailednodelist"+e);
				}	
					
				for(cnt=0;cnt<NumofNodes ; cnt++)
				{
					String s = nodesList[cnt];
					File tempfile = new File(s);
					if(!tempfile.exists())
						{	
						failednodes.add(s);
						}
				}
				FailedNodesList = new String[failednodes.size()];
				
				for(cnt = 0; cnt< failednodes.size() ; cnt++ )
				{
					FailedNodesList[cnt]=failednodes.get(cnt);
				}
				
		return FailedNodesList;			
	
		}
		
			public static void main(String[] args)
				{
					//String[] str;
					//str = getFailedNodelist();
					//System.out.print(str[2]);
				}
		
}		