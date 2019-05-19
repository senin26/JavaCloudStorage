package nettySerialization.message;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class MessageHandler {

    private MessageHandler() {
    }

    public static boolean sendFile(ObjectOutput out, ObjectInput in, FileMessage fm) {
        Thread thread = new Thread(() -> {
            try {
                Handshake hs;
                while (!fm.isDone()) {
                        fm.setBytesChunk();
                        out.writeObject(fm);
                        System.out.println(fm.isDone());
                        while (true) {
                            Object obj = in.readObject();
                            if (obj instanceof Handshake) {
                                hs = (Handshake) obj;
                                if (hs.equals(Handshake.OK))
                                System.out.println("chunk was received");
                                break;
                            }
                        }
                }
                out.writeObject(Handshake.DONE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean sendRequest(ObjectOutput out, RequestMessage msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean getFile(ObjectInput in, ObjectOutput out) {

        Thread thread = new Thread(() -> {
            FileMessage fm = null;
            try {
                while (true) {
                    Object obj;
                    obj = in.readObject();
                    if (obj instanceof FileMessage) {
                        fm = (FileMessage) obj;
                        System.out.println("ready to break");
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(fm == null);
            String pathName = "clientstorage/".concat(fm.getFileName());
            System.out.println("ready to get " + pathName);
            try {
                Path file = Paths.get(pathName);
                Files.createFile(file);
                int fileSize = getFileSize(file);
                while (fileSize != fm.getSize()) {
                    Files.write(file, fm.getBytesChunk(), StandardOpenOption.APPEND);
                    out.writeObject(Handshake.OK);
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof FileMessage) {
                            fm = (FileMessage) obj;
                            break;
                        }
                    }
                    System.out.println("new chunk 5th byte is " + fm.getBytesChunk()[5]);
                    fileSize = getFileSize(file);
                    System.out.println("fileSize is " + fileSize + " target size is " + fm.getSize());
                }
                out.writeObject(Handshake.DONE);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int getFileSize(Path path) {
        try {
            return (int) Files.newByteChannel(path).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
