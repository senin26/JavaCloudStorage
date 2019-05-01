package common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class FilesPreparationUtility {

    //this is a demo for sending the file connected with the 'fileName'   

    public static String fileName = "/photo2.jpg";
    public static String filePathDest = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/netty_server/serverstorage";
    public static String filePathSrc = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_client/clientstorage";

    public static BiFunction<String, String, String> fullFileName = (s1, s2) -> s1.concat(s2);

    public static Function<String, String> str2 = s -> {return Stream.of(s).
            map(p->p.replaceAll(p.substring(p.indexOf(".")),
                    "2user".concat(p.substring(p.indexOf(".")))))
            .findFirst().get();
    };

    public static byte[] prepareFile() throws Exception {
        return Files.readAllBytes(Paths.get(fullFileName.apply(filePathSrc,fileName)));
    }

}
