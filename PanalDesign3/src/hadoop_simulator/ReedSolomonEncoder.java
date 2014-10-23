/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;

import java.util.Vector;

/**
 * @author Ankit
 */
public final class ReedSolomonEncoder {

  private final GF256 field;
  private final Vector cachedGenerators;

  public ReedSolomonEncoder(GF256 field) {
    
    this.field = field;
    this.cachedGenerators = new Vector();
    cachedGenerators.addElement(new GF256Poly(field, new int[] { 1 }));
  }

  private GF256Poly buildGenerator(int degree) {
    if (degree >= cachedGenerators.size()) {
      GF256Poly lastGenerator = (GF256Poly) cachedGenerators.elementAt(cachedGenerators.size() - 1);
      for (int d = cachedGenerators.size(); d <= degree; d++) {
        GF256Poly nextGenerator = lastGenerator.multiply(new GF256Poly(field, new int[] { 1, field.exp(d - 1) }));
        cachedGenerators.addElement(nextGenerator);
        lastGenerator = nextGenerator;
      }
    }
    return (GF256Poly) cachedGenerators.elementAt(degree);    
  }

  public void encode(int[] toEncode, int ecBytes) {
    if (ecBytes == 0) {
      throw new IllegalArgumentException("No error correction bytes");
    }
    int dataBytes = toEncode.length - ecBytes;
    if (dataBytes <= 0) {
      throw new IllegalArgumentException("No data bytes provided");
    }
    GF256Poly generator = buildGenerator(ecBytes);
    
 //   System.out.println("This is generator: "+generator);
    int[] infoCoefficients = new int[dataBytes];
    System.arraycopy(toEncode, 0, infoCoefficients, 0, dataBytes);
    GF256Poly info = new GF256Poly(field, infoCoefficients);
   // System.out.println("Before info."+info);
    
    info = info.multiplyByMonomial(ecBytes, 1);
    
   // System.out.println("After info."+info);
    
    
    GF256Poly remainder = info.divide(generator)[1];
    
    
   // System.out.println("See the Reminder ." + remainder);
    
    
    int[] coefficients = remainder.getCoefficients();
    int numZeroCoefficients = ecBytes - coefficients.length;
    for (int i = 0; i < numZeroCoefficients; i++) {
      toEncode[dataBytes + i] = 0;
    }
    System.arraycopy(coefficients, 0, toEncode, dataBytes + numZeroCoefficients, coefficients.length);
  }

}