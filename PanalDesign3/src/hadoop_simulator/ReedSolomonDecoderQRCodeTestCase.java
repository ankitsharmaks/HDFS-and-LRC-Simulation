/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ankit
 */
public final class ReedSolomonDecoderQRCodeTestCase extends AbstractReedSolomonTestCase {

  /** See ISO 18004, Appendix I, from which this example is taken. */
  private static final int[] QR_CODE_TEST =
      { 0x10, 0x20, 0x0C, 0x56, 0x61, 0x80, 0xEC, 0x11, 0xEC,
        0x11, 0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11, 0xA5 };     
  private static final int[] QR_CODE_TEST_WITH_EC =
      { 0x10, 0x20, 0x0C, 0x56, 0x61, 0x80, 0xEC, 0x11, 0xEC,
        0x11, 0xEC, 0x11, 0xEC, 0x11, 0xEC, 0x11, 0xA5, 0x24,
        0xD4, 0xC1, 0xED, 0x36, 0xC7, 0x87, 0x2C, 0x55 };
  private static final int QR_CODE_ECC_BYTES = QR_CODE_TEST_WITH_EC.length - QR_CODE_TEST.length;
  private static final int QR_CODE_CORRECTABLE = QR_CODE_ECC_BYTES / 2;

  private final ReedSolomonDecoder qrRSDecoder = new ReedSolomonDecoder(GF256.QR_CODE_FIELD);

  public void testNoError(String filepath, String dirpath, int random_file_index) throws ReedSolomonException, FileNotFoundException, IOException {
    
      
    int[] received = new int[26];

    byte[] bytes= new byte[26];
    
    File f =new File("test1");
    FileInputStream finstream = new FileInputStream(f);
     
    File f1 =new File("javaio-appendfile");
    FileInputStream finstream1 = new FileInputStream(f1);
     
    
    finstream.read(bytes, 0, 16);
    finstream1.read(bytes, 16, 10);
    
    for(int i = 0 ; i< bytes.length ; i++)
        received[i] = bytes[i];
    
  //     received = (int[])bytes ;
    
//    System.arraycopy(bytes, 0, received, 0, received.length);
    
     System.out.println(received.length);
    
    // no errors
   checkQRRSDecode(received, filepath, dirpath, random_file_index);
  }

  public void testOneError(String filepath, String dirpath, int random_file_index) throws ReedSolomonException, FileNotFoundException, IOException {
    int[] received = new int[26];
     byte[] bytes= new byte[26];
    
    File f =new File("black.jpg");
    FileInputStream finstream = new FileInputStream(f);
     
    File f1 =new File("java");
    FileInputStream finstream1 = new FileInputStream(f1);
     
    
    finstream.read(bytes, 0, 16);
    finstream1.read(bytes, 16, 10);
    
    
  //  finstream.read(bytes, 0, 16);
    //finstream1.read(bytes, 16, 10);
    
     for(int i1 = 0 ; i1< bytes.length ; i1++){
         received[i1] = bytes[i1] & 0xFF ;
         System.out.println("The values: "+ received[i1]);
     
       }
   
   
    Random random = getRandom();
    for (int i = 0; i < received.length; i++) { 
        
    //  System.arraycopy(bytes, 0, received, 0, received.length);
      
      int bb= random.nextInt(256);

      System.out.println("Randomly selected:" + bb+"The place is :"+i);
      received[i] = bb;
      checkQRRSDecode(received, filepath, dirpath, random_file_index);     
      
      
      
    }
  }
  
  

  public void testMaxErrors(String filepath, String dirpath, int no_of_failed_nodes) throws ReedSolomonException, FileNotFoundException, IOException {
  //  int[] received = new int[QR_CODE_TEST_WITH_EC.length];
    Random random_number = new Random();
    Random random = getRandom();
    
    ///       the code need to correct. 
    
    ArrayList rand_num = new ArrayList();
    
    for(int i=0; i<no_of_failed_nodes ; i++)
    {    
            int random_file_index = random_number.nextInt(16);
         
            while(rand_num.contains(random_file_index)){
            
               random_file_index = random_number.nextInt(16);
         
            
            }
            
            rand_num.add(random_file_index);
            
            System.out.println("Random file index:"+random_file_index);
            
            int[] received = new int[26];
            byte[] bytes= new byte[26];


            File OriginalFile = new File(filepath);
            
            String Random_file_name;
            Random_file_name=String.format("_part%10d",random_file_index);
            
            System.out.println("File name" + OriginalFile.getName() + Random_file_name);
            File f =new File(dirpath + "\\raid\\Node_0\\" + OriginalFile.getName() + Random_file_name);
            FileInputStream finstream = new FileInputStream(f);     

            File f1 =new File(dirpath+"\\raid\\Node_0\\Node_Parity\\"+"Parity_"+OriginalFile.getName() + Random_file_name);
            FileInputStream finstream1 = new FileInputStream(f1);


     
            finstream.read(bytes, 0, 16);
            finstream1.read(bytes, 16, 10);

             for(int i1 = 0 ; i1< bytes.length ; i1++){
                 received[i1] = bytes[i1] & 0xFF ;
                 System.out.println("The values: "+ received[i1]);

               }

           for(int i1= 0 ;i1<64 ; i1++){
              corrupt(received, 5, random);
              checkQRRSDecode(received, filepath, dirpath, random_file_index);
              }
  /*    byte bb[] = new byte[26];
      int rec[] = new int[26];
      finstream.read(bb, 0, 16);
      finstream1.read(bb, 16, 10);
    
      for(int i1 = 0 ; i1< bytes.length ; i1++){
         rec[i1] = bb[i1] & 0xFF ;
         System.out.println("The values: "+ rec[i1]);
     
       }
   
    
      corrupt(rec, 5, random);
      checkQRRSDecode(rec);
    
    */
   
   
      
      
      
    
    
 //   }   

    }
  }

  public void testTooManyErrors(String filepath, String dirpath, int random_file_index) throws FileNotFoundException, IOException {
    int[] received = new int[QR_CODE_TEST_WITH_EC.length];
    System.arraycopy(QR_CODE_TEST_WITH_EC, 0, received, 0, received.length);
    Random random = getRandom();
    corrupt(received, QR_CODE_CORRECTABLE + 1, random);
    try {
      checkQRRSDecode(received, filepath, dirpath, random_file_index);
      fail("Should not have decoded");
    } catch (ReedSolomonException rse) {
      // good
    }
  }

  private void checkQRRSDecode(int[] received, String filepath, String dirpath, int file_index) throws ReedSolomonException, FileNotFoundException, IOException {
    qrRSDecoder.decode(received, 10, filepath, dirpath, file_index);
    for (int i = 0; i < QR_CODE_TEST.length; i++) {
//      assertEquals(received[i], QR_CODE_TEST[i]);
    }
  }

}