package BytesHandling;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.channels.FileChannel;

 public class Client {

     public static void getFileName(Path path) {
         try {
             Files.list(path).forEach(System.out::println);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public static void putFileToBuf(String fileName/*, Path src, Path dest*/) {

        int endOfData, endOfBuf;

        try (FileChannel fChan =
                     (FileChannel) Files.newByteChannel(Paths.get("clientstorage/".concat(fileName)))) {
        int bufSize = (int) fChan.size() + fileName.getBytes().length;

            ByteBuffer bb = ByteBuffer.allocate(bufSize);
            fChan.read(bb);
            endOfData = bb.position();
            bb.put(fileName.getBytes());
            bb.flip();
            char cur;
            for (int i = 0; i < endOfData; i++) {
                cur= (char) bb.get();
                System.out.print(cur);
                //System.out.print((char) bb.get());
            }
            System.out.println();
            for (int i = endOfData; i < bufSize; i++) {
                cur= (char) bb.get();
                System.out.print(cur);
                //System.out.print((char) bb.get());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //getFileName(Paths.get("clientstorage"));
        putFileToBuf("myFile.txt");
    }


}
