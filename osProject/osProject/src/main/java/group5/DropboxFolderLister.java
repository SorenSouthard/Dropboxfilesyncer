package group5;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class DropboxFolderLister {

    private DbxClientV2 client;

    public DropboxFolderLister(DbxClientV2 client) {
        this.client = client;
    }

    public void listFolders() {
        try {
            ListFolderResult result = client.files().listFolder("");
            System.out.println("Folders in your Dropbox account:");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    if (metadata instanceof com.dropbox.core.v2.files.FolderMetadata) {
                        System.out.println(metadata.getPathDisplay());
                    }
                }

                if (!result.getHasMore()) {
                    break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException ex) {
            ex.printStackTrace();
        }
    }
}
