/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;



//package outer ;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class fileRegenerate 

{
	static	int NumofNodes;
	static	int BlockSize;
	static	int replicationFactor;
	//static String newfilname ;   
	static    ArrayList<File> alistfile;
	static String newfilename;   
	   
	public static File regenerateFilefromNodes(String dirpath)//, String strnewfile)
	{
	
	alistfile = getFilesfromdiffNodes();
	newfilename=null ;
	//super.newFileName = strnewfile ; 
	
	
	 Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
            String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		newfilename = prop.getProperty("newFileName");
    ///////		System.out.println(newfilename); ///////////////24-4
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	
	
	
	
	String newFilename;
	newFilename = newfilename;
	System.out.println(newFilename);
	File regeneratedFile;
	regeneratedFile = new File(newFilename);
	
	if(regeneratedFile.exists())
        {
        regeneratedFile.delete();
        regeneratedFile = new File(newFilename);
        }  
        
        
	FileOutputStream foutputstream;
	FileInputStream finputstream;
	byte[] filesBytes;
	int bytsRead = 0;
	
	File[] allblocks;
	
	int arraylistsize = alistfile.size();
/////	System.out.println(arraylistsize);/////24-4
	
	allblocks = new File[arraylistsize];
	
	int i=0;
	while(!(alistfile.isEmpty()) && i<arraylistsize)
	{
		allblocks[i] = alistfile.get(i);
		i++;
	}
	
	
	try 
		
	{ 

    foutputstream = new FileOutputStream(regeneratedFile,true);             
    for (File file : allblocks) 
    	{
    	
        finputstream = new FileInputStream(file);
        filesBytes = new byte[(int) file.length()];
        bytsRead = finputstream.read(filesBytes, 0,(int)  file.length());
        assert(bytsRead == filesBytes.length);
        assert(bytsRead == (int) file.length());
        foutputstream.write(filesBytes);
        foutputstream.flush();
        filesBytes = null;
        finputstream.close();
        finputstream = null;
   	////	System.out.println(file);/////24-4
    
    
    	}
    
   	 	foutputstream.close();
    
    	foutputstream = null;
		}  //end of try
	
		catch(Exception e)
			{
		System.out.println("kl"+e);
			}	  
	
	return regeneratedFile;
		
	} // end of  regenerateFilefromNodes
	
	
	
	
	   
	    
public static ArrayList<File>  getFilesfromdiffNodes()
	{
	
ArrayList<File> filesretained = new ArrayList<File>();	


Properties prop = new Properties();
 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
 
               //get the property value and print it out
            String numnod = prop.getProperty("NumberofNodes");
    		String blksize = prop.getProperty("BlockSize");
    		String replic =prop.getProperty("replicationFactor");
    		newfilename = prop.getProperty("newFileName");
    		System.out.println(newfilename);
    		NumofNodes = Integer.parseInt(numnod);
    		BlockSize = Integer.parseInt(blksize);
    		replicationFactor = Integer.parseInt(replic);
    		
    		
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	


	
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
        
        stoken = null;

        while (line != null) 
        	{
            //System.out.println(line);
      	
            
         	 	  //line = br.readLine();
                 if(line == null)
                 {
                 break;
                 }	
                 	
                 stoken = new StringTokenizer(line);
                 
                 
           		 if(stoken.hasMoreTokens())
            		{
            		name=stoken.nextToken();
            		//System.out.println(name);
            		}
            
            	//System.out.println(stoken.countTokens());
            
   	         
    	   		int i=0;
           		String s2="";
          		while( stoken.hasMoreTokens() && i < replicfactor)
            	{
          			 s2 =stoken.nextToken();
           		 	//System.out.println(s2);
            		location[i]=s2;//stoken.nextToken();
            		i++;
            	}
            	
            File fretained;
            int atemp=location.length;
            fretained=getFile(location,atemp);
            
            filesretained.add(fretained);	
            
            
            line = br.readLine();
        
        //System.out.println(location[0]);	
        	
        	
        
    	
       		}
    	 
    	br.close();
	}
	catch(Exception e)
    	{
    		System.out.print("A  "+e);
    	 }
    	 
   
    return filesretained;	 
    //	finally {
      //  br.close();
    	//}
	}   // end of function regenerate
	
	
	
	

public static File getFile(String[] locs,int lengths)    
{
	// getFile function returns a file which extists.. here locs is all locations
	// of that file and out of those this function  checks at  every location
	// for that file and returns valid file  
	
	//File retFile ; // return file
	boolean b=true;
	int i=0;
	//locs=new String[lengths];
	
	File ff=null;
	//ff=new File(locs[i]);
	

    while( i<lengths && b==true )
	{
		//File ff;
		try {
		
		ff=new File(locs[i]);
		
		if(ff.isFile()==true)
				{
			//return ff;
			b=false;
			//return ff;
				}
		} 
		catch (Exception e)
		{
			System.out.println(" inside fileRegenerate . getFile() :"+e);
		}	
		i++;
		//ff=new 
	}
	
	return ff;
	
		
	
	
	
}	

	
	
/*public static void main(String[] args)
	{
ArrayList<File> asd=new ArrayList<File>();
	asd=getFilesfromdiffNodes();
	File fff;
	fff= regenerateFilefromNodes();
	
	System.out.print(fff);
	} */

}    // end of class