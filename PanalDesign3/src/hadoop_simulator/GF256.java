/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;


public final class GF256 {

    
  public static final GF256 QR_CODE_FIELD = new GF256(0x011D); // x^8 + x^4 + x^3 + x^2 + 1
  public static final GF256 DATA_MATRIX_FIELD = new GF256(0x012D); // x^8 + x^5 + x^3 + x^2 + 1

  
  private final int[] expTable;
  private final int[] logTable;
  private final GF256Poly zero;
  private final GF256Poly one;

  /**
   * @author Ankit
   */
  
  
  public GF256(int primitive) {
    expTable = new int[256];
    logTable = new int[256];
    int x = 1;
    for (int i = 0; i < 256; i++) {
      expTable[i] = x;
      
    //  System.out.println(x);
      
      x <<= 1; // x = x * 2; we're assuming the generator alpha is 2
      if (x >= 0x100) {
        x ^= primitive;
      }
    }
    for (int i = 0; i < 255; i++) {
      logTable[expTable[i]] = i;
    }
    
    
    // logTable[0] == 0 but this should never be used
    zero = new GF256Poly(this, new int[]{0});
    one = new GF256Poly(this, new int[]{1});
  }

  
  
  GF256Poly getZero() {
    return zero;
  }

  GF256Poly getOne() {
    return one;
  }

  
  /**
   * @return the monomial representing coefficient * x^degree
   */
  
  
  GF256Poly buildMonomial(int degree, int coefficient) {
    if (degree < 0) {
      throw new IllegalArgumentException();
    }
    if (coefficient == 0) {
      return zero;
    }
    int[] coefficients = new int[degree + 1];
    coefficients[0] = coefficient;
    return new GF256Poly(this, coefficients);
  }

  
  /**
   * Implements both addition and subtraction -- they are the same in GF(256).
   *
   * @return sum/difference of a and b
   */
  
  
  static int addOrSubtract(int a, int b) {
    return a ^ b;
  }

  /**
   * @return 2 to the power of a in GF(256)
   */
  
  
  int exp(int a) {
    return expTable[a];
  }

  
  
  /**
   * @return base 2 log of a in GF(256)
   */
  
  
  int log(int a) {
    if (a == 0) {
      throw new IllegalArgumentException();
    }
    return logTable[a];
  }

  
  
  
  /**
   * @return multiplicative inverse of a
   */
  
  int inverse(int a) {
    if (a == 0) {
      throw new ArithmeticException();
    }
    return expTable[255 - logTable[a]];
  }

  
  
  /**
   * @param a
   * @param b
   * @return product of a and b in GF(256)
   */
  
  int multiply(int a, int b) {
    if (a == 0 || b == 0) {
      return 0;
    }
    if (a == 1) {
      return b;
    }
    if (b == 1) {
      return a;
    }
    return expTable[(logTable[a] + logTable[b]) % 255];
  }

}