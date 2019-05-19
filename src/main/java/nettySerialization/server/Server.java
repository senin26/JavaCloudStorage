package nettySerialization.server;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import nettySerialization.common.Chunk;
import nettySerialization.common.ConnectionSettings;
import nettySerialization.message.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server;
    private static Socket socket;
    private static ObjectOutput out;
    private static ObjectInput in;

    public static void start() {
        try {
            server = new ServerSocket(ConnectionSettings.getPORT());
            System.out.println("Server started!");
            socket = server.accept();
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            in = new ObjectDecoderInputStream(socket.getInputStream(), (Chunk.CHUNK_SIZE.getSizeBytes()+1024*1024));

            RequestMessage requestMessage = null;
            FileMessage fileMessage = null;

            //new ServerMessageHandler().download(in, out);
            //new ServerMessageHandler().upload(in, out);

            try {
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof RequestMessage) {
                        requestMessage = (RequestMessage) obj;
                    }
                    else if (obj instanceof FileMessage) {
                        fileMessage = (FileMessage) obj;
                    }
                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (requestMessage != null) {
                System.out.println("Dwnld!");
                new ServerMessageHandler().download(in, out, requestMessage);
            }
            else {
                System.out.println("Upld!");
                new ServerMessageHandler().upload(in, out, fileMessage);
            }



            /*threadDwld = new Thread(() -> {
                try {
                    RequestMessage rm; // new RequestMessage("myServerFile.txt");
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof RequestMessage) {
                            rm = (RequestMessage) obj;
                            break;
                        }
                    }
                    System.out.println("requested file " + rm.getFileName()); // todo it's ok, can delete this
                    String fileName = rm.getFileName();
                    Handshake hs = null;
                    FileMessage fm = new FileMessage(fileName, SenderType.SERVER);
                    while(true) {
                        System.out.println("in the loop");
                        if ((rm != null) || hs.equals(Handshake.OK)) {
                            fm.setBytesChunk();
                            out.writeObject(fm);
                            //out.writeObject(Handshake.OK);
                            System.out.println("Bytes chunk was sent");
                            if (rm!=null) {
                                rm = null;
                            }
                            while (true) {
                                Object obj = in.readObject();
                                if (obj instanceof Handshake) {
                                    hs = (Handshake) obj;
                                    System.out.println("Bytes were accepted");
                                    break;
                                }
                            }
                            if (hs.equals(Handshake.DONE)) {
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });*/
            /*threadDwld.start();
            try {
                threadDwld.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            // Uploading thread
            /*threadUpld = new Thread(() -> {
                Object obj;
                FileMessage fm = null;
                String pathName;
                Path file = null;
                Handshake hs = Handshake.OK;
                try {
                    obj = in.readObject();
                    if (obj instanceof FileMessage) {
                        fm = (FileMessage) obj;
                        pathName = "serverstorage/".concat(fm.getFileName());
                        file = Paths.get(pathName);
                        Files.createFile(file);
                    }
                    int fileSize;
                    while (hs.equals(Handshake.OK)) {
                        Files.write(file, fm.getBytesChunk(), StandardOpenOption.APPEND);
                        fileSize = MessageHandler.getFileSize(file);
                        System.out.println("fileSize is " + fileSize);

                        out.writeObject(Handshake.OK);
                        while (true) {
                            obj = in.readObject();
                            if (obj instanceof FileMessage) {
                                fm = (FileMessage) obj;
                                break;
                            }
                            else if (obj instanceof Handshake) {
                                hs = (Handshake) obj;
                                break;
                            }
                        }
                    }
                } catch(ClassNotFoundException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
            });
            *//*threadUpld.start();
            try {
                threadUpld.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        start();
    }

}
