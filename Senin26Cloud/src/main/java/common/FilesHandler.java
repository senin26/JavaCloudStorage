package common;

import java.io.*;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class FilesHandler {

    //public static final String fileName = "/ServerTXTdoc.txt";
    public static final String fileName = "/o5Snd_h2CO4.jpg";
    public static String filePathSrc = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_server/serverstorage";
    public static String filePathDest = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_client/clientstorage";

    public static BiFunction<String, String, String> fullFileName = (s1, s2) -> s1.concat(s2);

    public static Function<String, String> str2 = s -> {return Stream.of(s).
            map(p->p.replaceAll(p.substring(p.indexOf(".")),
                    "2user".concat(p.substring(p.indexOf(".")))))
            .findFirst().get();
    };

    public static void getFile(InputStream in, OutputStream out) throws Exception {
        int chunk;
        while ((chunk = in.read())!=-1) {
            out.write(chunk);
        }
    }

}
