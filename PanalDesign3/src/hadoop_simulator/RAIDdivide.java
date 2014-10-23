/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JTextField;

/**
 *
 * @author Ankit
 */
class RAIDdivide {
    
    public String toBinary(int a){
	return Integer.toBinaryString(a);
    }

    public RAIDdivide(String File_Path, String Project_Path) {
       
        int Block_Size=1000*1024;	 // size of the block
                                    // which can be entered by user
        //String fname="test.mp4";

        File ifile = new File(File_Path); 
        FileInputStream fis;
        String newName;


        FileOutputStream chnk;
        int fileSize = (int) ifile.length();
        int nChunks = 0;
        int read = 0; 
        int readLength = Block_Size;
        byte[] byteChunk;
        int start = 0 ;

        try {
           fis = new FileInputStream(ifile);
               //System.out.println("filesize :  " + fileSize);
            //StupidTest.size = (int)ifile.length();
              // System.out.println("fp:" + File_Path +"\n pf:" + Project_Path);
            
                //String ppp = dirc.getPath();
                //System.out.println("pppp:" + ppp);
                int no_of_parts = fileSize / Block_Size;
                
                int no_of_nodes = no_of_parts/16;
                if(no_of_nodes%16!=0)
                        no_of_nodes++;
                
                
                System.out.println("part:" + no_of_parts + "\t Nodes:" + no_of_nodes);
          
            for(int i=0;i<no_of_nodes;i++)
            {   System.out.println("\nNode:"+i);
                for(int j=0;j<16;j++)
                {
                    System.out.println("\nBlock:"+j);
                    if (fileSize > 0)
                    {
                       if (fileSize <= Block_Size) 
                       {
                           readLength = fileSize;
                       }
                       
                       File dirc = new File(Project_Path + "\\raid\\Node_"+i+"\\");
                       dirc.mkdirs();
                
                
                       byteChunk = new byte[readLength];
                       read = fis.read(byteChunk,0, readLength);

                       System.out.println(read);

                       fileSize -= read;


                       assert(read==byteChunk.length);

                       nChunks++;

                       String temp=ifile.getName();
                       int tempint= nChunks -1;
                       newName=String.format("%s_part%10d",temp,tempint);
                       //String newpath="C:\Users\hp\Desktop\media\" ;


                       //File permfile = new File(dirc.getPath()+"\\node"+i+"\\"+newName);
                       
                        File permfile = new File(dirc, newName);
                       permfile.createNewFile();

                       chnk = new FileOutputStream(permfile);
                       chnk.write(byteChunk);

                       chnk.flush();
                       chnk.close();

                       byteChunk = null;
                       chnk = null;    
                 }
          
              }
                
          }

                fis.close();
                fis = null;


        }

        catch(Exception e)
        {
                System.out.print(e);
        }

        finally
        {
         //fis.close();
            //	fis = null;		
        }

        
    }
    
}
