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

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class RAIDcombiner {

    public RAIDcombiner(String filepath, String dirpath) {
        
    int Block_Size=1000*1024;
    

    
    File originalfile = new File(filepath);
    
    File outputfile = new File(dirpath + "\\raid\\" + originalfile.getName());
    
    if(outputfile.exists())
    {
        outputfile.delete();
        outputfile = new File(dirpath + "\\raid\\" + originalfile.getName());
    }    
    //System.out.println("originalfile:"+originalfile.getPath());
    
    int filesize = (int) originalfile.length();
    
    int no_of_parts = filesize / Block_Size;
                
    int no_of_nodes = no_of_parts/16;
    if(no_of_nodes%16!=0)
            no_of_nodes++;


    //System.out.println("part:" + no_of_parts + "\t Nodes:" + no_of_nodes);
    
    
    FileOutputStream foutstream = null;
    FileInputStream finstream = null;
    byte[] fileBytes;
    int bytRead = 0;
    
    
    try {
        foutstream = new FileOutputStream(outputfile,true);             
    } 
    catch (FileNotFoundException ex) {
        Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
    }

    
    for (int i=0; i<no_of_nodes; i++)
    {
      File dir =new File(dirpath + "\\raid\\Node_"+i+"\\");

      
      
      String[] dirlist = dir.list(new DirectoryFileFilter());
      
      //System.out.println("dirlist length:"+dirlist.length);


       File files[] =  dir.listFiles(new DirectoryFileFilter()) ;

     // System.out.println(files.length);


        

        for (File file : files) {
            
            try {
                finstream = new FileInputStream(file);
            } 
            catch (FileNotFoundException ex) {
                Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            fileBytes = new byte[(int) file.length()];
            
            
            try {
                bytRead = finstream.read(fileBytes, 0,(int)  file.length());
            } 
            catch (IOException ex) {
                Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            assert(bytRead == fileBytes.length);
            assert(bytRead == (int) file.length());
            
            
            try {
                foutstream.write(fileBytes);
            } 
            catch (IOException ex) {
                Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            try {
                foutstream.flush();
            } 
            catch (IOException ex) {
                Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            fileBytes = null;
            
            try {
                finstream.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            finstream = null;
            //System.out.println(file);


        }
        
    }
    
    
        try {
            foutstream.close();
        } catch (IOException ex) {
            Logger.getLogger(RAIDcombiner.class.getName()).log(Level.SEVERE, null, ex);
        }

        foutstream = null;
}  //end of try
    
public class DirectoryFileFilter
    implements FilenameFilter
{
    public boolean accept( final File file, final String name )
    {
       // return new File( file, name ).isDirectory();
        return new File( file, name ).isFile();
    }
}


}
