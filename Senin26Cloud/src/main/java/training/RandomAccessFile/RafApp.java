package training.RandomAccessFile;

import java.nio.ByteBuffer;

public class RafApp {

    public static void main(String[] args) {

        /*training.RandomAccessFile raf = new training.RandomAccessFile("1.txt", "rw");
        FileChannel src = raf.getChannel();

        training.RandomAccessFile rafTo = new training.RandomAccessFile("7.txt", "rw");
        FileChannel toFC = rafTo.getChannel();

        long position = 0;
        long size = raf.length();

        toFC.transferFrom(src, position, size);
        src.transferTo(position, size, toFC);*/


       /* try(training.RandomAccessFile raf = new training.RandomAccessFile("1.txt", "rw")) {
            FileChannel channel = raf.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(4);
            int readBytes = channel.read(buffer);

            while (readBytes != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear();
                readBytes = channel.read(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        ByteBuffer bBuffer = ByteBuffer.allocate(4);
        bBuffer.put((byte) 1);
        bBuffer.put((byte) 2);
        bBuffer.put((byte) 3);
        bBuffer.flip();
        System.out.println(bBuffer.get());
        System.out.println(bBuffer.get());
        System.out.println(bBuffer.get());
        bBuffer.flip();
        bBuffer.put((byte) 4);
        bBuffer.put((byte) 5);
        bBuffer.flip();
        System.out.println(bBuffer.get());
        System.out.println(bBuffer.get());
        //System.out.println(bBuffer.get());
    }

}
