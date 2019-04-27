package common;

import java.io.*;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class FilesHandler {

    static BiFunction<String, String, String> str1 = (s1, s2) -> s1.concat(s2);

    static Function<String, String> str2 = s -> {return Stream.of(s).
            map(p->p.replaceAll(p.substring(p.indexOf(".")),
                    "2user".concat(p.substring(p.indexOf(".")))))
            .findFirst().get();
    };

    public static void getFile(String fileName, String filePathSrc, String filePathDest) throws Exception {
        String fullFileNameSrc = str1.apply(filePathSrc,fileName);
        String fullFileNameDest = str1.apply(filePathDest,fileName);
        FileInputStream fis = new FileInputStream(str1.apply(filePathSrc,fileName));
        FileOutputStream fos = new FileOutputStream(str1.apply(filePathDest,fileName));
        int chunk;
        while ((chunk = fis.read())!=-1) {
            fos.write(chunk);
        }
        //inputStream.read();
    }

    public static void readFile(String fileName) throws Exception {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            int chunk;
            while ((chunk = fis.read())!=-1) {
                System.out.print((char) chunk);;
            }
        }
    };

    public static void sendFile(OutputStream outputStream) throws Exception {
        outputStream.write(Byte.MAX_VALUE);
    }

    // todo consider replace of this for stream
    private static  String fileInputNameConverter(String filePathSrc, String fileName) {
        return filePathSrc.concat(fileName);
    }

    private static String fileNameConverter(String initFileName) {
        /*String fileFormat = initFileName.substring(initFileName.indexOf(".")+1, initFileName.length());
        return (initFileName.substring(0,initFileName.indexOf("."))+"2user").concat(".").concat(fileFormat);*/

        return Stream.of(initFileName).
                map(p->p.replaceAll(p.substring(p.indexOf(".")),
                        "2user".concat(p.substring(p.indexOf(".")))))
                .findFirst().get();
    }

}
