import nettySerialization.common.PathUtil;
import nettySerialization.network.repo.NetworkClientRepo;
import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StorageUnitSpec {

   /* @Before
    public void init0() {
        System.out.println(new File("").getAbsolutePath());
    }*/

    /*@Before
    public void init() {
        String s = PathUtil.getAbsoluteSubpath();
        String fileFullName = s + "/serverStorage/" + "bigMovieC.avi";
        Path file = Paths.get(fileFullName);

        try {
            long len1 = Files.newByteChannel(file).size();
            long len2 = new FileInputStream(new File(fileFullName)).available();
            System.out.println(Files.exists(Paths.get(fileFullName)));
            System.out.printf("length of l1 is %s, length of l2 is %s",len1, len2); //serverStorage/bigMovieC.avi

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void test() {
        //String pathName = "serverStorage/".concat("bigMovieC.avi");
        String s1 = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorageGit/cloudStorage";
        String s2 = PathUtil.getAbsoluteSubpath();
        assert(s1.equals(s2));
    }

    /*@Test
    public void shortFileNameTest() {
        NetworkClientRepo repo = new NetworkClientRepo();
        String res = repo.getShortFileName("/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorageGit/cloudStorage/storage/serverStorage/bigMovieC.avi");
        //String pathName = "serverStorage/".concat("bigMovieC.avi");
        *//*String s1 = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorageGit/cloudStorage";
        String s2 = PathUtil.getAbsoluteSubpath();*//*
        System.out.println(res);
        assert(res.equals("bigMovieC.avi"));
    }*/

    //
}