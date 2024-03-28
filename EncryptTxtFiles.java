package group5;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptTxtFiles {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private SecretKey secretKey;

    public EncryptTxtFiles(String key) throws NoSuchAlgorithmException {
        this.secretKey = generateKey(key);
    }

    private SecretKey generateKey(String key) throws NoSuchAlgorithmException {
        byte[] keyBytes = Arrays.copyOf(key.getBytes(), 16); // Pad or truncate key to 16 bytes
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public void encryptFile(File inputFile, File outputFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    public void decryptFile(File inputFile, File outputFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    private void doCrypto(int cipherMode, File inputFile, File outputFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}


