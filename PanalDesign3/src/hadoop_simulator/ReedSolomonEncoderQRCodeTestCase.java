/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ReedSolomonEncoderQRCodeTestCase extends AbstractReedSolomonTestCase {

    int Block_Size=1000*1024;
  
  public void testExample(String filepath, String dirpath) {
      
        
      
      
        FileInputStream finstream = null;
        try {
            File OriginalFile = new File(filepath);
            File f = new File(dirpath + "\\raid\\Node_0\\" + OriginalFile.getName() + "_part         0" );
            int[] expectedECBytes = new int[] {
            0xA5, 0x24, 0xD4, 0xC1, 0xED, 0x36, 0xC7, 0x87,
            0x2C, 0x55 };
            finstream = new FileInputStream(f);
            int[] data = new int[16];
            int length = (int) f.length();
            int  i = 0 ;
            while(length > 0 ){
             
             byte[] bytes = new byte[1];
             int Read = finstream.read(bytes, 0,1);
        
            
             int a = bytes[0];
                   
             
             
            // int a = bytes[0] ;
             length = length - Read ;
             
             if(i < 16 ){
             
                 data[i] = a & 0xFF;
                 i ++ ;
             
             
             
             }else{
             
                 doTestQRCodeEncoding(data, expectedECBytes,f.getPath(), dirpath);
        
                 i =0 ;      
             
             
             }
               
             }

      //    return value;
            int[] dataBytes = new int[] {
              0x10, 0x20, 0x0C, 0x56, 0x61, 0x80, 0xEC, 0x11,
              0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11 };
        } catch (IOException ex) {
            Logger.getLogger(ReedSolomonEncoderQRCodeTestCase.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            try {
                finstream.close();
            } catch (IOException ex) {
                Logger.getLogger(ReedSolomonEncoderQRCodeTestCase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
  }


  // Need more tests I am sure
  
  
  
  //Encode all the blocks in a Node

    void testallExample(String filepath, String dirpath) {
      
   
      
      
        FileInputStream finstream = null;
        
        
        File originalfile = new File(filepath);
    
        System.out.println("originalfile:"+originalfile.getPath());

        int filesize = (int) originalfile.length();

        int no_of_parts = filesize / Block_Size;

        int no_of_nodes = no_of_parts/16;
        if(no_of_nodes%16!=0)
                no_of_nodes++;


        System.out.println("part:" + no_of_parts + "\t Nodes:" + no_of_nodes);
        
        
        int blockcounter = 0;
        for(int k = 0 ; k< no_of_nodes ; k++)
        {
        try {
          for(int j =k*16 ;j<(k*16)+16 ;j++)
          {if(blockcounter<=no_of_parts)
           {  
            blockcounter++;   
            File OriginalFile = new File(filepath);
            
            String newName=String.format("_part%10d",j);
            
            File f = new File(dirpath + "\\raid\\Node_"+k+"\\" + OriginalFile.getName() + newName );
            int[] expectedECBytes = new int[] {
            0xA5, 0x24, 0xD4, 0xC1, 0xED, 0x36, 0xC7, 0x87,
            0x2C, 0x55 };
            finstream = new FileInputStream(f);
            int[] data = new int[16];
            int length = (int) f.length();
            int  i = 0 ;
            while(length > 0 ){
             
             byte[] bytes = new byte[1];
             int Read = finstream.read(bytes, 0,1);
        
            
             int a = bytes[0];
             
             
             
             
            // int a = bytes[0] ;
             length = length - Read ;
             
             if(i < 16 ){
             
                 data[i] = a & 0xFF;
                 i ++ ;
             
             
             
             }else{
             
                 doTestQRCodeEncoding(data, expectedECBytes,f.getPath(), dirpath);
        
                 i =0 ;      
             
             
             }
               
             }

      //    return value;
            int[] dataBytes = new int[] {
              0x10, 0x20, 0x0C, 0x56, 0x61, 0x80, 0xEC, 0x11,
              0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11 };
          }
        }    
        } catch (IOException ex) {
            Logger.getLogger(ReedSolomonEncoderQRCodeTestCase.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        
        
        finally {
            try {
                finstream.close();
            } catch (IOException ex) {
                Logger.getLogger(ReedSolomonEncoderQRCodeTestCase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    /*
      public void testQRCodeVersusDecoder() throws Exception {
          
    Random random = getRandom();
    ReedSolomonEncoder encoder = new ReedSolomonEncoder(GF256.QR_CODE_FIELD);
    ReedSolomonDecoder decoder = new ReedSolomonDecoder(GF256.QR_CODE_FIELD);
    
    for (int i = 0; i < 100; i++) {
      int size = random.nextInt(1000);
      int[] toEncode = new int[size];
      int ecBytes = 1 + random.nextInt(2 * (1 + size / 8));
      int dataBytes = size - ecBytes;
      
      for (int j = 0; j < dataBytes; j++) {
        toEncode[j] = random.nextInt(256);
      }
      
      int[] original = new int[dataBytes];
      System.arraycopy(toEncode, 0, original, 0, dataBytes);
      encoder.encode(toEncode, ecBytes);
      decoder.decode(toEncode, ecBytes, filepath, dirpath);
      assertArraysEqual(original, 0, toEncode, 0, dataBytes);
    }
    
  }*/

}
