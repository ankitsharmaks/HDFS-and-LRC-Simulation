/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package hadoop_simulator;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;
import junit.framework.TestCase;

/**
 * @author Ankit
 */


abstract class AbstractReedSolomonTestCase extends TestCase {


    static void corrupt(int[] received, int howMany, Random random) {
    BitSet corrupted = new BitSet(received.length);
    
    
    for (int j = 0; j < howMany; j++) {
      int location = random.nextInt(received.length);
      
      System.out.println("Corrupted location:"+ location);
      
      if (corrupted.get(location)) {
        j--;
      } 
      
      else {
        corrupted.set(location);
        received[location] = (received[location] + 1 + random.nextInt(255)) & 0xFF;
      }
      
      
    }
    
    
    
  }

    
    
    
  static void doTestQRCodeEncoding(int[] dataBytes, int[] expectedECBytes, String filepath, String dirpath) throws FileNotFoundException, IOException {
      
       File EncodedFile = new File(filepath);
       
       File paritydirc = new File(EncodedFile.getParent()+"\\Node_Parity\\");
       //File paritydirc = new File(dirpath+"\\raid\\Node_0\\Node_Parity\\");
    
       paritydirc.mkdirs();
       File file =new File(paritydirc,"Parity_"+EncodedFile.getName());
 
    		//if file doesnt exists, then create it
    	
 
    		//true = append file
    		  
      FileOutputStream fos = new FileOutputStream(file, true);
    
      
     /// int bc = ((dataBytes.length %10)+1) * 4 ;
   
      
      
    int[] toEncode = new int[dataBytes.length + expectedECBytes.length];
    System.arraycopy(dataBytes, 0, toEncode, 0, dataBytes.length);
    
  //  int bc = ((dataBytes.length %10)+1) * 4 ;
    
    new ReedSolomonEncoder(GF256.QR_CODE_FIELD).encode(toEncode, expectedECBytes.length);
    
 //   System.out.println(expectedECBytes.length);
    for(int i = 0 ; i<expectedECBytes.length ;i ++ ){
     // System.out.println("parity: " + toEncode[dataBytes.length + i]);
     
     fos.write(toEncode[dataBytes.length + i]);
     
     
    }
    
  //  System.out.println(dataBytes.length) ;
    
    for(int i = 0 ; i<dataBytes.length ;i ++ ){
   //  System.out.println("data: " + toEncode[i]);
      
    }
    
 //    System.out.println(toEncode.length) ;
    
    for(int i = 0 ; i<toEncode.length ;i ++ ){
    // System.out.println("Encoded: " + toEncode[i]);
      
    }
   
    
    
    
    
    
    
    
    assertArraysEqual(dataBytes, 0, toEncode, 0, dataBytes.length);
    assertArraysEqual(expectedECBytes, 0, toEncode, dataBytes.length, expectedECBytes.length);
  }

  static Random getRandom() {
    return new Random(0xDEADBEEF);
  }
  
  
  
  

  static void assertArraysEqual(int[] expected, int expectedOffset, int[] actual, int actualOffset, int length) {
    for (int i = 0; i < length; i++) 
    
    {
      if( expected[expectedOffset + i] !=  actual[actualOffset + i]){
       
          //    System.out.println("BC");
            //  System.out.println("Expect:"+expected[expectedOffset + i]+"Actual:"+actual[actualOffset + i]);
              
      }
    }
  }

}