package group5;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final String ACCESS_TOKEN = "sl.ByO0SgM_m5m4jMT11qM_y1ww0LicvaP9i5TZAViAiJPpUkuUTQL4t1GziArCOjuk507lCC-JKdqmiK00D-1-sJP9kedJxWhcEvLO0ly812XeU1YC96HbQBrOf2NfzpVKABkJeDk8bj3YtjNIa8whgRI";

    public static void main(String args[]) {
        System.out.println("Hi");

        try {
            // Set up Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Operating-Systems-Project-Spring-2024").build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

            // Get user account info
            FullAccount account = client.users().getCurrentAccount();
            System.out.println("Logged in as: " + account.getName().getDisplayName());

            // Specify local file path and Dropbox folder path
            String localFilePath = "C:\\Users\\jmurd\\Documents\\Comp-Org-Spring-2024\\lab-8-Murdock.docx";
            String dropboxFolderPath = "/anotha1/lab-8.docx"; // Modify as needed

            // Upload the file
            DropboxFileUploader.uploadFile(client, new File(localFilePath), dropboxFolderPath);

            System.out.println("File uploaded successfully.");
        } catch (DbxException ex) {
            ex.printStackTrace();
        }
    }
}

