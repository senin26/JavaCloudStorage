package nettySerialization;

import nettySerialization.common.BytesToFileUtil;

import java.io.InputStream;
import java.nio.file.Paths;

import static nettySerialization.common.BytesToFileUtil.writeByChunks;

public class CloudApp {

    public static void main(String[] args) {
       // Client.start();
        //System.out.println(Integer.MAX_VALUE>>3);
        try {
            InputStream in = BytesToFileUtil.getInputStream(Paths.get("clientstorage/bigMovieC.avi"));
            BytesToFileUtil.writeByChunks(Paths.get("serverstorage/bigMovieC.avi"), in);
            //writeByChunks(Paths.get("clientstorage/bigMovieC.avi"), "serverstorage/bigMovieC.avi");
            //writeByChunks(Paths.get("clientstorage/myClientFile.txt"), "serverstorage/myClientFile.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
