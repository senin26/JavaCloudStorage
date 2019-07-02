package basic_client;

import common.ConnectionSettings;
import common.FilesHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EasyClient {
    public static void main(String[] args) {
        Socket socket;
        String fileName = "/ServerTXTdoc.txt"; //this is a filename
        String filePathSrc = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_server/serverstorage";
        String filePathDest = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_client/clientstorage";
        Thread clientInThread = new Thread(() -> {
            try {
                FilesHandler.getFile(fileName, filePathSrc, filePathDest);
                System.out.println("Server sent the file: ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientInThread.start();
    }
}
