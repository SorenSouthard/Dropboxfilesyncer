package group5;
import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import java.io.File;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class encryptionMain {

    private static final String ACCESS_TOKEN = "sl.ByRDBBsBPgr2BIRZPpXxMbfqa4c1g0fZ_AYVCx66L-m-UUC27UtcpvF0Mrn5lposbbPT42AQuf8IdymBvwog8ZaZ4YQ7la46MmIH2bqcb3_j3oSlV9JYAuPkjgeXa5wlqaouEgUm8sCbcORuUCk9NTA";

    public static void main(String[] args) {
        System.out.println("Hi");

        try {
            // Set up Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Operating-Systems-Project-Spring-2024").build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

            // Get user account info
            FullAccount account = client.users().getCurrentAccount();
            System.out.println("Logged in as: " + account.getName().getDisplayName());

            // Specify local file path and Dropbox folder path
            String localFilePath = "";
            String dropboxFolderPath = "";

            // Encrypt the file
            String encryptionKey = ""; // Change this to your own secret key
            File encryptedFile = new File(localFilePath + ".enc");
            EncryptTxtFiles encryptor = new EncryptTxtFiles(encryptionKey);
            encryptor.encryptFile(new File(localFilePath), encryptedFile);

            // Upload the encrypted file
            DropboxFileUploader.uploadFile(client, encryptedFile.getAbsolutePath(), dropboxFolderPath);

            // Delete the encrypted file after uploading
            if (encryptedFile.exists()) {
                encryptedFile.delete();
            }

            System.out.println("File uploaded successfully.");

            // Download the encrypted file from Dropbox
            String downloadedFilePath = "downloaded_file.enc";
            DropboxFileDownloader.downloadFile(client, dropboxFolderPath, downloadedFilePath);

            // Decrypt the downloaded file
            File decryptedFile = new File(localFilePath + "_decrypted.txt");
            encryptor.decryptFile(new File(downloadedFilePath), decryptedFile);

            System.out.println("File decrypted successfully.");

            // Delete the downloaded encrypted file from local storage
            File downloadedFile = new File(downloadedFilePath);
            if (downloadedFile.exists()) {
                downloadedFile.delete();
            }

            // Optionally, delete the encrypted file from Dropbox
            client.files().deleteV2(dropboxFolderPath);

            System.out.println("Encrypted file deleted from Dropbox.");

        } catch (IOException | DbxException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }
}
