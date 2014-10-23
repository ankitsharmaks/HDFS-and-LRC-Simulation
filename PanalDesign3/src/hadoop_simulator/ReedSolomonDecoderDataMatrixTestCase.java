/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoop_simulator;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * @author Ankit
 */
public final class ReedSolomonDecoderDataMatrixTestCase extends AbstractReedSolomonTestCase {

  private static final int[] DM_CODE_TEST = { 142, 164, 186 };
  private static final int[] DM_CODE_TEST_WITH_EC = { 142, 164, 186, 114, 25, 5, 88, 102 };
  private static final int DM_CODE_ECC_BYTES = DM_CODE_TEST_WITH_EC.length - DM_CODE_TEST.length;
  private static final int DM_CODE_CORRECTABLE = DM_CODE_ECC_BYTES / 2;

  private final ReedSolomonDecoder dmRSDecoder = new ReedSolomonDecoder(GF256.DATA_MATRIX_FIELD);

  public void testNoError(String filepath, String dirpath) throws ReedSolomonException, FileNotFoundException, IOException {
    int[] received = new int[DM_CODE_TEST_WITH_EC.length];
    System.arraycopy(DM_CODE_TEST_WITH_EC, 0, received, 0, received.length);
    // no errors
    checkQRRSDecode(received, filepath, dirpath);
  }

  public void testOneError(String filepath, String dirpath) throws ReedSolomonException, FileNotFoundException, IOException {
    int[] received = new int[DM_CODE_TEST_WITH_EC.length];
    Random random = getRandom();
    for (int i = 0; i < received.length; i++) {
      System.arraycopy(DM_CODE_TEST_WITH_EC, 0, received, 0, received.length);
      received[i] = random.nextInt(256);
      checkQRRSDecode(received, filepath, dirpath);
    }
  }

  public void testMaxErrors(String filepath, String dirpath) throws ReedSolomonException, FileNotFoundException, IOException {
    int[] received = new int[DM_CODE_TEST_WITH_EC.length];
    Random random = getRandom();
    for (int i = 0; i < DM_CODE_TEST.length; i++) { // # iterations is kind of arbitrary
      System.arraycopy(DM_CODE_TEST_WITH_EC, 0, received, 0, received.length);
      corrupt(received, DM_CODE_CORRECTABLE, random);
      checkQRRSDecode(received, filepath, dirpath);
    }
  }

  public void testTooManyErrors(String filepath, String dirpath) throws FileNotFoundException, IOException {
    int[] received = new int[DM_CODE_TEST_WITH_EC.length];
    System.arraycopy(DM_CODE_TEST_WITH_EC, 0, received, 0, received.length);
    Random random = getRandom();
    corrupt(received, DM_CODE_CORRECTABLE + 1, random);
    try {
      checkQRRSDecode(received, filepath, dirpath);
      fail("Should not have decoded");
    } catch (ReedSolomonException rse) {
      // good
    }
  }

  private void checkQRRSDecode(int[] received, String filepath, String dirpath) throws ReedSolomonException, FileNotFoundException, IOException {
      int neverused=0;
      dmRSDecoder.decode(received, DM_CODE_ECC_BYTES, filepath, dirpath, neverused);
    for (int i = 0; i < DM_CODE_TEST.length; i++) {
      assertEquals(received[i], DM_CODE_TEST[i]);
    }
  }

}