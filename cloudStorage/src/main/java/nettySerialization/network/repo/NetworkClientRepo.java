package nettySerialization.network.repo;



import nettySerialization.common.PathUtil;
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
import java.util.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(NetworkClientRepo.class.getName());

    private String getShortFileName(String absFilename) {
        String[] fileNameSplitted = absFilename.split("/");
        int len = fileNameSplitted.length;
        return fileNameSplitted[len-1];
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
                        System.out.println("ready to break, fm is " + fm.getFileName());
                        break;
                    }
                    LOG.info("fm == null?" + (fm==null));
                }
                //

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace(); // /home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorageGit/cloudStorage/resources/serverStorage/bigMovieC.avi
            }
            String pathName = PathUtil.getAbsoluteSubpath()+"/clientStorage/"+getShortFileName(fm.getFileName());
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