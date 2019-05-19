package nettySerialization.network.repo;



import nettySerialization.message.AbstractMessage;
import nettySerialization.message.FileMessage;
import nettySerialization.message.Handshake;
import nettySerialization.message.RequestMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NetworkClientRepo {

    public boolean sendRequest(ObjectOutput out, RequestMessage msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendFile(ObjectOutput out, ObjectInput in, FileMessage fm) {
        Thread thread = new Thread(() -> {
            try {
                Handshake hs; // will be deleted
                while (!fm.isDone()) {
                    fm.setBytesChunk();
                    out.writeObject(fm);
                    System.out.println(fm.isDone());

                    // replace this for checkServerReceipt(ObjectInput in)
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof Handshake) {
                            hs = (Handshake) obj;
                            if (hs.equals(Handshake.OK))
                                System.out.println("chunk was received");
                            break;
                        }
                    }
                    //
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

    private void checkServerReceipt(ObjectInput in) throws ClassNotFoundException, IOException {
        while (true) {
            Object obj = in.readObject();
            if (obj instanceof Handshake) {
                if (obj.equals(Handshake.OK))
                    System.out.println("chunk was received");
                obj = null;
                break;
            }
        }
    }

    public boolean getFile(ObjectInput in, ObjectOutput out) {
        Thread thread = new Thread(() -> {
            FileMessage fm = null;
            try {
                // replace this for checkFileReceipt()
                while (true) {
                    Object obj;
                    obj = in.readObject();
                    if (obj instanceof FileMessage) {
                        fm = (FileMessage) obj;
                        System.out.println("ready to break");
                        break;
                    }
                }
                //

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String pathName = "clientstorage/".concat(fm.getFileName());
            System.out.println("ready to get " + fm.getFileName()); // delete this, it's just a service message
            try {
                Path file = Paths.get(pathName);
                Files.createFile(file);
                int fileSize = getFileSize(file);
                while (fileSize != fm.getSize()) {
                    Files.write(file, fm.getBytesChunk(), StandardOpenOption.APPEND);
                    out.writeObject(Handshake.OK);

                    // replace this for checkFileReceipt()
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof FileMessage) {
                            fm = (FileMessage) obj;
                            break;
                        }
                    }
                    //

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

    private void checkFileReceipt(ObjectInput in, FileMessage fm) throws ClassNotFoundException, IOException {
        while (true) {
            Object obj;
            obj = in.readObject();
            if (obj instanceof FileMessage) {
                fm = (FileMessage) obj;
                System.out.println("ready to break");
                obj = null; // this seems to be useless...
                break;
            }
        }
    }

    private int getFileSize(Path path) {
        try {
            return (int) Files.newByteChannel(path).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


}