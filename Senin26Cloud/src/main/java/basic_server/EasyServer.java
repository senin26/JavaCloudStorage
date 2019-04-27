package basic_server;

import common.ConnectionSettings;
import common.FilesHandler;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static common.FilesHandler.fileName;
import static common.FilesHandler.filePathSrc;

public class EasyServer {

    public static void main(String[] args) {

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(ConnectionSettings.getPORT());
            System.out.println("Server started!");
            socket = server.accept();
            System.out.println("Client connected!");
            DataOutputStream serverOut = new DataOutputStream(socket.getOutputStream());

            Thread serverOutThread = new Thread(() -> {
                    try {
                        serverOut.write(Files.readAllBytes(Paths.get(FilesHandler.fullFileName.apply(filePathSrc,fileName))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            serverOutThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
