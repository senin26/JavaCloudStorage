package nettySerialization.client;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import nettySerialization.common.Chunk;
import nettySerialization.common.ConnectionSettings;
import nettySerialization.message.*;
import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;
import java.nio.file.*;

public class Client {

        private static Socket socket;
        private static ObjectOutput out;
        private static ObjectInput in;

        public static void start() {
            try {
                socket = new Socket(ConnectionSettings.getIP_ADDRESS(), ConnectionSettings.getPORT());
                out = new ObjectEncoderOutputStream(socket.getOutputStream());
                in = new ObjectDecoderInputStream(socket.getInputStream(), (Chunk.CHUNK_SIZE.getSizeBytes()+1024*1024));
                MessageHandler.sendFile(out, in, new FileMessage("bigMovieC.avi", SenderType.CLIENT)); // UPLOAD  myClientFile.txt    bigMovieC.avi
                //MessageHandler.sendRequest(out, new RequestMessage("bigMovieC.avi")); // DOWNLOAD
                //MessageHandler.getFile(in, out); // DOWNLOAD
              /*try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            } catch (IOException e) {
                //System.out.println("Some fucking error");
                e.printStackTrace();
            }
        }

        public static void stop() {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        /*String pathName = "clientstorage/".concat("myFile.txt");
        String str = "This is a fucking cool file";
        try {
            Files.createFile(Paths.get(pathName));
            Path file = Paths.get(pathName);
            System.out.println("length before " + Files.newByteChannel(Paths.get(pathName)).size());
            Files.write(Paths.get(pathName), str.getBytes(), StandardOpenOption.WRITE);
            System.out.println("length after " + Files.newByteChannel(Paths.get(pathName)).size());
            System.out.println(file.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        start();
    }
}


//todo
/*
* 1) enum Request
* UPLOAD_REQUEST
* DOWNLOAD_REQUEST
*
* */