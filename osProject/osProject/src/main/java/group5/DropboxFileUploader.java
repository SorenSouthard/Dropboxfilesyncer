package group5;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class DropboxFileUploader {

    // Define the uploadFile method inside the DropboxFileUploader class
    public static void uploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
        try (InputStream in = new FileInputStream(localFile)) {
            FileMetadata metadata = dbxClient.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.ADD)
                    .withClientModified(new Date(localFile.lastModified()))
                    .uploadAndFinish(in);

            System.out.println(metadata.toStringMultiline());
        } catch (DbxException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error reading from file \"" + localFile.getAbsolutePath() + "\": " + ex.getMessage());
            System.exit(1);
        }
    }

}
