package nettySerialization.server;

import nettySerialization.common.PathUtil;
import nettySerialization.message.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

public class ServerMessageHandler {

    private static final Logger LOG = Logger.getLogger(ServerMessageHandler.class.getName());

    private String getAbsFileName(String fileName) {
        System.out.println("Full fileName is " + PathUtil.getAbsoluteSubpath()+"/serverStorage/"+fileName);
        return PathUtil.getAbsoluteSubpath()+"/serverStorage/"+fileName;
    }

    public void download(ObjectInput in, ObjectOutput out, RequestMessage requestMessage) {
        Thread threadDwld = new Thread(() -> {
            RequestMessage rm;
            try {
                rm = requestMessage;
               /* while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof RequestMessage) {
                        rm = (RequestMessage) obj;
                        break;
                    }
                }*/
                System.out.println("requested file " + rm.getFileName()); // todo it's ok, can delete this
                String fileName = getAbsFileName(rm.getFileName());
                Handshake hs = null;
                FileMessage fm = new FileMessage(fileName, SenderType.SERVER);
                while (true) {
                    System.out.println("in the loop");
                    if ((rm != null) || hs.equals(Handshake.OK)) {
                        fm.setBytesChunk();
                        out.writeObject(fm);
                        //out.writeObject(Handshake.OK);
                        System.out.println("Bytes chunk was sent");
                        if (rm != null) {
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
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        threadDwld.start();
        try {
            threadDwld.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getShortFileName(String absFilename) {
        String[] fileNameSplitted = absFilename.split("/");
        int len = fileNameSplitted.length;
        return fileNameSplitted[len-1];
    }

    public void upload(ObjectInput in, ObjectOutput out, FileMessage fileMessage) {
        Thread threadUpld = new Thread(() -> {
            Object obj;
            FileMessage fm = fileMessage;
            String pathName;
            Path file;
            Handshake hs = Handshake.OK;
            try {
                //obj = in.readObject();
                pathName = PathUtil.getAbsoluteSubpath()+"/serverStorage/"+getShortFileName(fm.getFileName());
                file = Paths.get(pathName);
                Files.createFile(file);
                /*if (obj instanceof FileMessage) {
                    fm = (FileMessage) obj;
                }*/
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
                        } else if (obj instanceof Handshake) {
                            hs = (Handshake) obj;
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        threadUpld.start();
        try {
            threadUpld.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
