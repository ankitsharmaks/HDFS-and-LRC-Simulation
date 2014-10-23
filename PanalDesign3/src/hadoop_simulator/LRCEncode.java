/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Ankit
 */
class LRCEncode {

    LRCEncode(String filepath, String dirpath, int no_of_blocks) {
        
         int no_of_nodes = no_of_blocks/16;
         if(no_of_nodes%16!=0)
                 no_of_nodes++;
         
  
         
         for(int i=0;i<no_of_nodes;i++) {   
             
            
             System.out.println("\nNode:"+i);
             
             File dir=new File(dirpath + "\\raid\\Node_"+i+"\\");

             String[] dirlist = dir.list(new DirectoryFileFilter());
             
             System.out.println("\nDirlist:" + dirlist.length);

             File files[] =  dir.listFiles(new DirectoryFileFilter()) ;
             
             System.out.println(files.length);
           
             
             //call this to make parity within the Node folder
             
             LRCMakeParity makeparity = new LRCMakeParity(files);
    
            }
        
    }
    
    
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
