package basic_client;

import common.ConnectionSettings;
import common.FilesPreparationUtility;

import java.io.*;
import java.net.Socket;

import static common.FilesPreparationUtility.fileName;
import static common.FilesPreparationUtility.filePathDest;

public class EasyClient {

    public static void main(String[] args) {

        try  {
            Socket socket = new Socket(ConnectionSettings.getIP_ADDRESS(), ConnectionSettings.getPORT());
            DataInputStream clientIn = new DataInputStream(socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(FilesPreparationUtility.fullFileName.apply(filePathDest,fileName));

            Thread clientInThread = new Thread(() -> {
                try {
                    ClientFilesHandler.sendFile(fos);
                    //ClientFilesHandler.getFile(clientIn, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            clientInThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
