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

public final class ReedSolomonDecoder {

  private final GF256 field;
  static int i = 0 ;

  public ReedSolomonDecoder(GF256 field) {
    this.field = field;
  }

  /**
   * @author Ankit
   */
  public void decode(int[] received, int twoS, String filepath, String dirpath, 
          int file_index) throws ReedSolomonException, FileNotFoundException, IOException {
      
      
        i++;
      File OriginalFile = new File(filepath);
      
       String Random_file_name;
       Random_file_name=String.format("_part%10d",file_index);
      //corrupted file.
      File corrupteddir = new File(dirpath + "\\raid\\Node_0\\Corrupted\\");
      corrupteddir.mkdir();
      
    File f1 =new File(corrupteddir, "corrupted"+OriginalFile.getName() + Random_file_name);
     System.out.println("Corrupted name:"+f1.getName());
   
    FileOutputStream finstream1 = new FileOutputStream(f1,true);
     
    
    //File f2 =new File("test2");
    
  
    
    
       // encoded file. It's parity is recieved under "recieved"..
    
    
    File f2 =new File(dirpath + "\\raid\\Node_0\\" + OriginalFile.getName() + Random_file_name);
     System.out.println("Corrected name:"+f2.getName());
   
    FileInputStream finstream2 = new FileInputStream(f2);
    int aaa = (int) (f2.length()-16);
    byte[] cont = new byte[aaa];
    
   if((i%64)==0){
    finstream2.read(cont, 0, 16);
    finstream2.read(cont, 0, aaa);
    }
    
    
    byte bb[] = new byte[26];
 //   finstream1.read(bb, 0, 16);
    
      
      
      
    GF256Poly poly = new GF256Poly(field, received);
    System.out.println("inverse: "+field.inverse(4));
    int[] syndromeCoefficients = new int[twoS];
    boolean dataMatrix = field.equals(GF256.DATA_MATRIX_FIELD);
    boolean noError = true;
    for (int i = 0; i < twoS; i++) {
      // Thanks to sanfordsquires for this fix:
      int eval = poly.evaluateAt(field.exp(dataMatrix ? i + 1 : i));
      syndromeCoefficients[syndromeCoefficients.length - 1 - i] = eval;
      if (eval != 0) {
        noError = false;
      }
    }
    
    
    
    if (noError) {
      return;
    }
    GF256Poly syndrome = new GF256Poly(field, syndromeCoefficients);
    GF256Poly[] sigmaOmega =
        runEuclideanAlgorithm(field.buildMonomial(twoS, 1), syndrome, twoS);
    GF256Poly sigma = sigmaOmega[0];
    GF256Poly omega = sigmaOmega[1];
    int[] errorLocations = findErrorLocations(sigma);
    
    for(int i = 0 ; i<errorLocations.length ; i++ ){
    
  //  System.out.println("Error locations : "+errorLocations[i]);
    
    
    }
    int[] errorMagnitudes = findErrorMagnitudes(omega, errorLocations, dataMatrix);
     for (int i = 0; i < errorMagnitudes.length; i++) {
   
       System.out.println("Magnitude:"+errorMagnitudes[i]);
         
         
     }
     for (int i = 0; i < received.length; i++) {
     
       System.out.println("----------------- hey it's unchanged"+received[i]);
     
       bb[i] = (byte) received[i] ;
     
     }
     
     if((i % 64) == 0){
       System.out.println("Writing the data");  
     finstream1.write(bb, 0, 16);
     finstream1.write(cont, 0,aaa);
     
     }
     
    
    for (int i = 0; i < errorLocations.length; i++) {
      int position = received.length - 1 - field.log(errorLocations[i]);
      
      /// System.out.println("positions : " + position);
      if (position < 0) {
        throw new ReedSolomonException("Bad error location");
      }
      received[position] = GF256.addOrSubtract(received[position], errorMagnitudes[i]);
      
  //     System.out.println("The bit location : "+received[position]);
    }
    
    byte haha[] = new byte[26];
    for (int i = 0; i < received.length; i++) {
    
        System.out.println(received[i]);
        haha[i] = (byte) received[i];
    }
    
    File correcteddir = new File(dirpath + "\\raid\\Node_0\\Corrected\\");
    correcteddir.mkdir();
    
    File fff= new File( correcteddir ,"Corrected_"+OriginalFile.getName() + Random_file_name);
    System.out.println("Corrected name:"+fff.getName());
    
    FileOutputStream fout = new FileOutputStream(fff,true);
  
     if((i %64) == 0){
    fout.write(haha, 0, 16);
    fout.write(cont, 0, aaa);    
  }
    
    
   finstream1.close();
   fout.close();
   finstream2.close();
     
  }

  private GF256Poly[] runEuclideanAlgorithm(GF256Poly a, GF256Poly b, int R)
      throws ReedSolomonException {
    // Assume a's degree is >= b's
    if (a.getDegree() < b.getDegree()) {
      GF256Poly temp = a;
      a = b;
      b = temp;
    }

    
    GF256Poly rLast = a;
    GF256Poly r = b;
    GF256Poly sLast = field.getOne();
    GF256Poly s = field.getZero();
    GF256Poly tLast = field.getZero();
    GF256Poly t = field.getOne();
       System.out.println("A:"+a+"r mat b:"+r+"slast:"+sLast+"s:"+t+"tlast:"+tLast+"t:"+t);
    
    
    // Run Euclidean algorithm until r's degree is less than R/2
    while (r.getDegree() >= R / 2) {
      GF256Poly rLastLast = rLast;
      GF256Poly sLastLast = sLast;
      GF256Poly tLastLast = tLast;
      rLast = r;
      sLast = s;
      tLast = t;

      // Divide rLastLast by rLast, with quotient in q and remainder in r
      if (rLast.isZero()) {
        // Oops, Euclidean algorithm already terminated?
        throw new ReedSolomonException("r_{i-1} was zero");
      }
      r = rLastLast;
      GF256Poly q = field.getZero();
      int denominatorLeadingTerm = rLast.getCoefficient(rLast.getDegree());
 //     System.out.println("denominatorLeadingTerm:"+denominatorLeadingTerm);
      int dltInverse = field.inverse(denominatorLeadingTerm);
   //   System.out.println("dltInverse:"+dltInverse);
      
      while (r.getDegree() >= rLast.getDegree() && !r.isZero()) {   
        int degreeDiff = r.getDegree() - rLast.getDegree();
        int scale = field.multiply(r.getCoefficient(r.getDegree()), dltInverse);
        q = q.addOrSubtract(field.buildMonomial(degreeDiff, scale));
        r = r.addOrSubtract(rLast.multiplyByMonomial(degreeDiff, scale));
     
        System.out.println("Quotient:"+q +"\n"+"Reminder"+r);
      
      }

      s = q.multiply(sLast).addOrSubtract(sLastLast);
      t = q.multiply(tLast).addOrSubtract(tLastLast);
    }

    int sigmaTildeAtZero = t.getCoefficient(0);
    if (sigmaTildeAtZero == 0) {
      throw new ReedSolomonException("sigmaTilde(0) was zero");
    }

    int inverse = field.inverse(sigmaTildeAtZero);
    GF256Poly sigma = t.multiply(inverse);
    GF256Poly omega = r.multiply(inverse);
    
       System.out.println("sigma:"+sigma);
          System.out.println("omega:"+ omega);
     
     
    return new GF256Poly[]{sigma, omega};
  }

  private int[] findErrorLocations(GF256Poly errorLocator) throws ReedSolomonException {
    // This is a direct application of Chien's search
    int numErrors = errorLocator.getDegree();
    if (numErrors == 1) { // shortcut
      return new int[] { errorLocator.getCoefficient(1) };
    }
    int[] result = new int[numErrors];
    int e = 0;
    for (int i = 1; i < 256 && e < numErrors; i++) {
      if (errorLocator.evaluateAt(i) == 0) {
        result[e] = field.inverse(i);
        e++;
      }
    }
    if (e != numErrors) {
//      throw new ReedSolomonException("Error locator degree does not match number of roots");
    }
    return result;
  }

  private int[] findErrorMagnitudes(GF256Poly errorEvaluator, int[] errorLocations, boolean dataMatrix) {
    // This is directly applying Forney's Formula
    int s = errorLocations.length;
    int[] result = new int[s];
    for (int i = 0; i < s; i++) {
      int xiInverse = field.inverse(errorLocations[i]);
      int denominator = 1;
      for (int j = 0; j < s; j++) {
        if (i != j) {
          denominator = field.multiply(denominator,
              GF256.addOrSubtract(1, field.multiply(errorLocations[j], xiInverse)));
        }
      }
      result[i] = field.multiply(errorEvaluator.evaluateAt(xiInverse),
          field.inverse(denominator));
      // Thanks to sanfordsquires for this fix:
      if (dataMatrix) {
        result[i] = field.multiply(result[i], xiInverse);
      }
    }
    return result;
  }

}