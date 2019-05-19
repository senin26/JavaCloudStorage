package nettySerialization.message;

import nettySerialization.common.Chunk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class FileMessage extends AbstractMessage {

    private long size;

    private byte[] bytesChunk;

    private transient BytesHandler bh;

    private SenderType senderType;

    public FileMessage(String fileName, SenderType senderType) {
        super(fileName);
        this.senderType = senderType;
        this.size = getSize();
        bh = new BytesHandler(size);
    }

    public long getSize() {
        String pathName = null;
        try {
            switch (senderType) {
                case CLIENT:
                    pathName = "clientstorage/";
                    break;
                case SERVER:
                    pathName = "serverstorage/";
                    break;
            }
           size = new FileInputStream(new File(pathName.concat(fileName))).available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public boolean isDone() {
        return bh.isDone;
    }

    public byte[] getBytesChunk() {
        return bytesChunk;
    }

    public void setBytesChunk() {
        bytesChunk = bh.getNextBytesChunk(fileName, senderType);
    }


    public SenderType getSenderType() {
        return senderType;
    }

     static class BytesHandler {
        private int finishChunkSize;
        private int chunkSize;
        private byte[] bytesChunk;
        private byte[] finishBytesChunk;
        private int chunkNumber;
        private int chunkMaxNumber;
        private boolean isDone;

        public BytesHandler(long size) {
            if (size > Chunk.CHUNK_SIZE.getSizeBytes()) {
                chunkSize = Chunk.CHUNK_SIZE.getSizeBytes();
                chunkMaxNumber = ((int) size) / chunkSize + 1;
            }
            else {
                chunkSize = (int) size;
                chunkMaxNumber = 1;
            }
            if (size % chunkSize != 0) {
                finishChunkSize = (int) size % chunkSize;
            }
            else finishChunkSize = 0;

            bytesChunk = new byte[(int) chunkSize];
            finishBytesChunk = new byte[finishChunkSize];
            chunkNumber = 0;
        }

        public byte[] getNextBytesChunk(String fileName, SenderType senderType) {
            FileInputStream fin;
            String pathName = null;
            try {
                switch (senderType) {
                    case CLIENT:
                        pathName = "clientstorage/";
                        break;
                    case SERVER:
                        pathName = "serverstorage/";
                        break;
                }
                fin = new FileInputStream(new File(pathName.concat(fileName)));
                fin.skip(chunkSize*chunkNumber);
                if (chunkNumber != (chunkMaxNumber-1)) {
                    fin.read(bytesChunk, 0, chunkSize);
                    chunkNumber++;
                    System.out.println("chunkNumber is " + chunkNumber);
                }
                else {
                    if (finishChunkSize != 0) {
                        bytesChunk = finishBytesChunk;
                        chunkSize = finishChunkSize;
                    }
                    fin.read(bytesChunk, 0, chunkSize);
                    isDone = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytesChunk;
        }
    }
}
