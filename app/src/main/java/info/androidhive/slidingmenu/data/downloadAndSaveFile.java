package info.androidhive.slidingmenu.data;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Juan on 22/03/2017.
 */

public class downloadAndSaveFile extends AsyncTask<Boolean, Boolean, Boolean> {
    FTPClient ftp = null;

    private String server;
    private int portNumber;
    private String user;
    private String password;
    boolean success = false;


    public downloadAndSaveFile(String server, int portNumber, String user, String password) {
        this.server = server;
        this.portNumber = portNumber;
        this.user = user;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Boolean... booleen) {
        try {
            ftp = new FTPClient();
            ftp.connect(server, portNumber);
            Log.d("", "Connected. Reply: " + ftp.getReplyString());

            ftp.login(user, password);
//            Log.d("", "Logged in");
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d("", "Downloading");
            ftp.enterLocalPassiveMode();

            OutputStream outputStream = null;
            String workingDir = ftp.printWorkingDirectory();
            FTPFile[] files = ftp.listFiles();


            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
            if (!filePath.exists()) {
                filePath.mkdirs();
            } else {
                File file = null;
                for (int i = 0; i < filePath.listFiles().length; i++) {
                    file = filePath.listFiles()[i];
                    file.delete();
                }
            }

            try {
                for (FTPFile file : files) {

                    outputStream = new BufferedOutputStream(new FileOutputStream(filePath.getPath() + "/" + file.getName()));
                    success = ftp.retrieveFile(file.getName(), outputStream);
                    outputStream.close();
                }
                if (ftp != null) {
                    try {
                        ftp.logout();
                        ftp.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e){

            }


        } catch (IOException e){

        }
        return success;
    }
}
