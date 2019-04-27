package basic_client;

import common.ConnectionSettings;
import common.FilesHandler;
import java.io.*;
import java.net.Socket;

import static common.FilesHandler.fileName;
import static common.FilesHandler.filePathDest;

public class EasyClient {

    public static void main(String[] args) {

        try  {
            Socket socket = new Socket(ConnectionSettings.getIP_ADDRESS(), ConnectionSettings.getPORT());
            DataInputStream clientIn = new DataInputStream(socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(FilesHandler.fullFileName.apply(filePathDest,fileName));

            Thread clientInThread = new Thread(() -> {
                try {
                    FilesHandler.getFile(clientIn, fos);
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
