package nettySerialization.common;

import sun.nio.ch.ChannelInputStream;
import sun.nio.ch.FileChannelImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class BytesToFileUtil {

    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;
    private static final int BUFFER_SIZE = 8192;



    public static InputStream getInputStream(Path path) throws IOException {
        InputStream in = null;
        SeekableByteChannel sbc = null;
        try {
            sbc = Files.newByteChannel(path);
            in = Channels.newInputStream(sbc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static void writeByChunks(Path path, InputStream in) throws IOException {

        byte[] bytes;
        byte[] tempBytes = null;

            long size = in.available();
            if (size>Integer.MAX_VALUE>>3) {
                bytes = new byte[(int)size>>3];
            }
            else bytes = new byte[(int) size];

            int count = 0;

            if (size%bytes.length!=0) {
                tempBytes = new byte[(int) size % bytes.length];
            }

            while (in.read(bytes) != -1) {
                if (!Files.exists(path)) {
                    Files.write(path, bytes, StandardOpenOption.CREATE_NEW);
                }
                else {
                    if (count == size / bytes.length) {
                        for (int i = 0; i < tempBytes.length; i++) {
                            tempBytes[i] = bytes[i];
                        }
                        bytes = tempBytes;
                    }
                    Files.write(path, bytes, StandardOpenOption.APPEND);
                }
                freeBuff(bytes);
                count++;
            }
    }

    /*public static void writeByChunks(Path path, String relFilePath) throws IOException {

        byte[] bytes;
        byte[] tempBytes = null;
        path.getFileName();
        try (SeekableByteChannel sbc = Files.newByteChannel(path);
             InputStream in = Channels.newInputStream(sbc)) {

            //long size = sbc.size();
            long size = in.available();
            if (size>Integer.MAX_VALUE>>3) {
                bytes = new byte[(int)size>>3];
            }
            else bytes = new byte[(int) size];

            int count = 0;

            if (size%bytes.length!=0) {
                tempBytes = new byte[(int) size % bytes.length];
            }

            while (in.read(bytes) != -1) {
                if (!Files.exists(Paths.get(relFilePath))) {
                    Files.write(Paths.get(relFilePath), bytes, StandardOpenOption.CREATE_NEW);
                }
                else {
                    if (count == size/bytes.length) {
                        for (int i = 0; i < tempBytes.length; i++) {
                            tempBytes[i] = bytes[i];
                        }
                        bytes = tempBytes;
                    }
                    Files.write(Paths.get(relFilePath), bytes, StandardOpenOption.APPEND);
                }
                freeBuff(bytes);
                count++;
            }
        }
    }*/

    public static boolean isFull(byte[] bytes) {

        return false;
    }

    public static void freeBuff(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = 0;
        }
    }

    /*public static byte[] readAllBytes(Path path) throws IOException {
        try (SeekableByteChannel sbc = Files.newByteChannel(path);
            InputStream in = Channels.newInputStream(sbc)) {
            long size = sbc.size();
            System.out.println("Byte channel size is " + size);
            if (size > (long) MAX_BUFFER_SIZE)
                throw new OutOfMemoryError("Required array size too large");
            return read(in, (int)size);
        }
    }

    private static byte[] read(InputStream source, int initialSize) throws IOException {
        int capacity = initialSize;
        if (capacity >= (Integer.MAX_VALUE >> 2)) {
            capacity = (capacity >> 2);
        }
        byte[] buf = new byte[capacity];
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initialSize (eg: file
            // is truncated while we are reading)
            while ((n = source.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if last call to source.read() returned -1, we are done
            // otherwise, try to read one more byte; if that failed we're done too
            if (n < 0 || (n = source.read()) < 0)
                break;

            // one more byte was read; need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = Math.max(capacity << 1, BUFFER_SIZE);
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
            buf[nread++] = (byte)n;
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }*/

}
