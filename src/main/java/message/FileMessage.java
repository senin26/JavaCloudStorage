package message;

import common.BytesToFileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMessage extends AbstractMessage {

    private byte[] fileBytes;

    private SenderType senderType;

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public FileMessage(String fileName, SenderType senderType) {
        super(fileName);
        this.senderType = senderType;
        try {
            switch (senderType) {
                case SERVER:
                    this.fileBytes = Files.readAllBytes(Paths.get("serverstorage/".concat(fileName)));
                    break;
                case CLIENT:
                    this.fileBytes = Files.readAllBytes(Paths.get("clientstorage/".concat(fileName)));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}