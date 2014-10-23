/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ankit
 */
class LRCMakeParity {

    public LRCMakeParity(File[] files) {
        
        
         File paritydir = new File(files[0].getParent()+"\\LRC_Parity\\");
                            paritydir.mkdir();
                            
         System.out.println("parity dir:" + paritydir.getPath());
                            
        int morethan=0;   
        
        int filecount = files.length;
        
        if(filecount >=8)
              {
                  morethan = 8;
                   //File f[] = new File[5];
                            FileInputStream[] finstream = new FileInputStream[8];
                             FileInputStream finstream1 ;
                           // String filename;
                            byte[][] fileBytes = new byte[8][1024000];
                            byte[] temp = new byte[1024000];
                            int[] Read = new int[8];
                            
                            
                  for(int i=0;i<8;i++)
                  {
                           
                                 //filename = files[i].getPath();
                                 //f[i] = new File(filename);

                                 System.out.println(files[i].getName());
                                 try {
                                     
                                      finstream[i] = new FileInputStream(files[i]);
                                  } 
                                    catch (FileNotFoundException ex) {
                                      Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                                    }



                                 System.out.println( files[i].length());
                                 
                                try {
                                    
                                      Read[i] = finstream[i].read(fileBytes[i], 0,(int)  files[i].length());
                                } 
                                   catch (IOException ex) {
                                      Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                 System.out.println(Read[i]);

                         
                  }


                            byte ak[] = new byte[fileBytes[0].length] ;
                            for(int i=0;i<fileBytes[0].length;i++)
                                 ak[i] = (byte)0;

                            for(int j=0;j<8;j++){

                                 for(int i = 0 ;i < fileBytes[0].length ; i++){

                                     ak[i] = (byte) (ak[i] ^ fileBytes[j][i]) ;
                                 }
                            }


                            
                           
                            
                            File parityfile = new File(paritydir,"Parity_0");

                           FileOutputStream	fop = null;
                            try {
                                fop = new FileOutputStream(parityfile);
                            } 
                            catch (FileNotFoundException ex) {
                                Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                            try {
                                fop.write(ak);
                            } catch (IOException ex) {
                                Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                            }

                    filecount = files.length - 8;    
                      System.out.println("\n First 8 Done!!!");  
                      
                  }
        
        
        if(filecount > 0)
        {
            
             System.out.println("\n Next 8 started!!!"); 
                    FileInputStream[] finstream = new FileInputStream[filecount];
                            FileInputStream finstream1 ;
                           // String filename;
                            byte[][] fileBytes = new byte[filecount][1024000];
                            byte[] temp = new byte[1024000];
                            int[] Read = new int[filecount];
                            
                            
                            for(int i=0; i<filecount; i++)
                            {
                           
                                 //filename = files[i].getPath();
                                 //f[i] = new File(filename);

                                 System.out.println(files[i+morethan].getName());
                                 try {
                                     
                                      finstream[i] = new FileInputStream(files[i+morethan]);
                                  } 
                                    catch (FileNotFoundException ex) {
                                      Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                                    }



                                 System.out.println( files[i+morethan].length());
                                 
                                try {
                                    
                                      Read[i] = finstream[i].read(fileBytes[i], 0,(int)  files[i+morethan].length());
                                } 
                                   catch (IOException ex) {
                                      Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                 System.out.println(Read[i]);

                           
                            }
                            
                             byte ak[] = new byte[fileBytes[0].length] ;
                            for(int i=0;i<fileBytes[0].length;i++)
                                 ak[i] = (byte)0;

                            for(int j=0;j<filecount;j++){

                                 for(int i = 0 ;i < fileBytes[0].length ; i++){

                                     ak[i] = (byte) (ak[i] ^ fileBytes[j][i]) ;
                                 }
                            }


                            String parityfilename;
                           if( files.length >= 8)
                           {   
                                parityfilename = "Parity_1";
                           }
                           
                           else
                           {                              
                                parityfilename = "Parity_0";
                           }
                           
                           File parityfile = new File(paritydir,parityfilename);
                           
                           FileOutputStream	fop = null;
                            try {
                                fop = new FileOutputStream(parityfile);
                            } 
                            catch (FileNotFoundException ex) {
                                Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                            try {
                                fop.write(ak);
                            } catch (IOException ex) {
                                Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            
              System.out.println("\n Next 8 done!!!");               
        }    
                  
                  
                      
    } 
        
        
}