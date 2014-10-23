/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

/**
 *
 * @author Ankit
 */
class LRCDecode {

    public LRCDecode(String filepath, String dirpath, int no_of_blocks) {
        
        File dir=new File(dirpath + "\\raid\\Node_0\\");

        String[] dirlist = dir.list(new LRCDecode.DirectoryFileFilter());

        System.out.println("\nDirlist:" + dirlist.length);

        File files[] =  dir.listFiles(new LRCDecode.DirectoryFileFilter()) ;

        System.out.println(files.length);
        
        int Random_failed_node; 
                                 
        Random randomGenerator = new Random();

        Random_failed_node = randomGenerator.nextInt(15);
        
        
        
        LRCcorrectlostfile correctfile = new LRCcorrectlostfile(files,Random_failed_node); // '0' is the position of error

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
