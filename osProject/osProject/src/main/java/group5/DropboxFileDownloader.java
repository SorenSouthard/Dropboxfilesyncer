package group5;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DropboxFileDownloader {

    public static void downloadFile(DbxClientV2 client, String dropboxFilePath, String localFilePath) throws DbxException, IOException {
        try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
            FileMetadata metadata = client.files().downloadBuilder(dropboxFilePath)
                    .download(outputStream);
        } catch (DownloadErrorException ex) {
            System.err.println("Error downloading file from Dropbox: " + ex.getMessage());
            throw ex;
        }
    }
}
