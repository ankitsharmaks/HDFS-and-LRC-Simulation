/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ankit
 */
public class Galois1 {
    
    Galois1(){
        System.out.println("Galois constructor called..");
         GF256 field = new GF256(285); 
    }

   
  static void callEncoder(String filepath, String dirpath) {
       
    
        // TODO code application logic here
    
    
        Calendar lCDateTime = Calendar.getInstance();
        System.out.println("Calender - Time in milliseconds :" + lCDateTime.getTimeInMillis());
 
       
         
       
          
          ReedSolomonEncoderQRCodeTestCase v = new ReedSolomonEncoderQRCodeTestCase();
          
          v.testExample(filepath,dirpath);
      
            //v.testISO18004Example(filepath,dirpath);
          
          
         
            
        Calendar lCDateTime1 = Calendar.getInstance();
        System.out.println("Calender - Time in milliseconds :" + (lCDateTime1.getTimeInMillis()-lCDateTime.getTimeInMillis()));


          
      // System.out.println(field.exp(252));
        
        
    }

  
  
    
  static void callDecoder(String filepath, String dirpath, int no_of_failed_nodes) {
       
    
       
    
        Calendar lCDateTime = Calendar.getInstance();
        System.out.println("Calender - Time in milliseconds :" + lCDateTime.getTimeInMillis());
 
       
          
           
         ReedSolomonDecoderQRCodeTestCase v = new ReedSolomonDecoderQRCodeTestCase();
         
         
            try 
            {
                v.testMaxErrors(filepath,dirpath, no_of_failed_nodes); 
            } 
            catch (ReedSolomonException ex) {
                Logger.getLogger(Galois1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            catch (FileNotFoundException ex) {
                Logger.getLogger(Galois1.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            catch (IOException ex) {
                Logger.getLogger(Galois1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        Calendar lCDateTime1 = Calendar.getInstance();
        System.out.println("Calender - Time in milliseconds :" + (lCDateTime1.getTimeInMillis()-lCDateTime.getTimeInMillis()));


          
      // System.out.println(field.exp(252));
        
        
    }

  
}
