package basic_client.network.repo;

import message.AbstractMessage;
import message.FileMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NetworkClientRepo {

    public boolean sendMsg(ObjectOutput out, AbstractMessage msg) {
        try {
            out.writeObject(msg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getMessage(ObjectInput in) {
        try {
            if (in != null) {
                AbstractMessage am = (AbstractMessage) in.readObject();
                if (am instanceof FileMessage) {
                    FileMessage fm = (FileMessage) am;
                    System.out.println("Ready to get the file!");
                    Files.write(Paths.get("clientstorage/" + fm.getFileName()),
                            fm.getFileBytes(),
                            StandardOpenOption.CREATE);
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}