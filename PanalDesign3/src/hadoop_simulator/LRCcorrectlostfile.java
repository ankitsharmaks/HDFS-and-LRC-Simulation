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
class LRCcorrectlostfile {

    public LRCcorrectlostfile(File[] files, int position) {
        int offset = 0;
        
        if(position >= 8)
                offset = 8;
        
        FileInputStream[] finstream = new FileInputStream[8];
        FileInputStream finstream1 ;
        byte[][] fileBytes = new byte[8][1024000];
        byte[] temp = new byte[1024000];
        int[] Read = new int[8];
        
        for(int i=offset; i<offset+8; i++){
            int j = i - offset;
            if(i!=position)
            {
                
                    System.out.println(files[i].getName());
                    try {
                        finstream[j] = new FileInputStream(files[i]);
                    } 
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(LRCcorrectlostfile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    
                    System.out.println( files[i].length());
                    
                    
                    try {
                        Read[j] = finstream[j].read(fileBytes[j], 0,(int)  files[i].length());
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(LRCcorrectlostfile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    System.out.println(Read[j]);
                
            }   
            
            
        }
        int parity_no = 0;
        
        if(position >= 8)
            parity_no = 1;
        
        File parityfile = new File (files[0].getParent() + "\\LRC_Parity\\" + "Parity_" + parity_no);
        
        
        System.out.println(parityfile.getName());
        try {
            finstream[position-offset] = new FileInputStream(parityfile);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(LRCcorrectlostfile.class.getName()).log(Level.SEVERE, null, ex);
        }


        System.out.println( parityfile.length());


        try {
            Read[position-offset] = finstream[position-offset].read(fileBytes[position-offset], 0,(int)  parityfile.length());
        } 
        catch (IOException ex) {
            Logger.getLogger(LRCcorrectlostfile.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(Read[position-offset]);
        
        
         byte ak[] = new byte[fileBytes[0].length] ;
        for(int i=0;i<fileBytes[0].length;i++)
             ak[i] = (byte)0;

        for(int j=0;j<8;j++){

             for(int i = 0 ;i < fileBytes[0].length ; i++){

                 ak[i] = (byte) (ak[i] ^ fileBytes[j][i]) ;
             }
        }



        File LRCCorrectedDir = new File(files[0].getParent()+"\\LRCcorrected\\");
        LRCCorrectedDir.mkdir();

        File newcorrectedfile = new File(LRCCorrectedDir,"LRCCorrected_"+position);

        FileOutputStream	fop = null;
        try {
            fop = new FileOutputStream(newcorrectedfile);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            fop.write(ak);
        } catch (IOException ex) {
            Logger.getLogger(LRCMakeParity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
