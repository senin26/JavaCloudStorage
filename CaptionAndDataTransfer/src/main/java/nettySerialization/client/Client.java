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
            } catch (IOException e) {
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
        start();
    }
}
