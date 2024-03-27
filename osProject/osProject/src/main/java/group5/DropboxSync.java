package group5;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import com.dropbox.core.v2.files.WriteMode;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DropboxSync {
    private List<String> synchronizedFiles;
    private DbxClientV2 client;

    // Constructor
    public DropboxSync(DbxClientV2 client) {
        this.client = client;
        this.synchronizedFiles = new ArrayList<>();
    }

    // Method to manually sync a file
    public void syncManually(String localFilePath, String dropboxFilePath) throws IOException, DbxException {
        File localFile = new File(localFilePath);
        uploadFile(localFile, dropboxFilePath);
        synchronizedFiles.add(localFilePath);
    }

    // Method to automatically sync all files in a folder
    public void syncAutomatically(String localFolderPath, String dropboxFolderPath) throws IOException, DbxException {
        File localFolder = new File(localFolderPath);
        File[] files = localFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && !synchronizedFiles.contains(file.getAbsolutePath())) {
                    String relativePath = localFolder.toPath().relativize(file.toPath()).toString();
                    String dropboxFilePath = dropboxFolderPath + "/" + relativePath;
                    uploadFile(file, dropboxFilePath);
                    synchronizedFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    // Helper method to upload a file to Dropbox
    private void uploadFile(File localFile, String dropboxFilePath) throws IOException, DbxException {
        try (InputStream in = new FileInputStream(localFile)) {
            client.files().uploadBuilder(dropboxFilePath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        }
    }
}


