package basic_client.clientstorage;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        /*String filePath = "UserTXTdoc.txt";
        String[] res = filePath.split(".txt");
        for (String s:
             res) {
            System.out.println(s);
        }
        System.out.println(filePath.indexOf("."));
        System.out.println(filePath.substring(0,filePath.indexOf(".")));*/
         //System.out.println(filePath.split("."));

        BiFunction<String, String, String> str = (str1, str2) -> str1.concat(str2);

        Stream<String> strStream = Stream.of("UserTXTdoc.txt");
        Optional<String> reducedOptString = strStream.
                map(p->p.replaceAll(p.substring(p.indexOf(".")),
                        "2user".concat(p.substring(p.indexOf(".")))))
                .findFirst();
        //Optional<String> reducedOptString = strStream.map(p->p.substring(0,p.indexOf("."))).findFirst().map(p->p.concat("2user"));
        //Optional<String> optStringFormat = strStream.map(p->p.substring(p.indexOf("."))).findFirst();
        //reducedOptString.map(p->p.concat(optStringFormat));
        String str2 = reducedOptString.get();
        System.out.println(str2);
        /*Optional<String> reducedOptString = strStream.reduce((p,q)->p.substring(0,p.indexOf(".")));
        System.out.println(reducedOptString.get());*/

        //System.out.println(str.apply("Hi, ", "Tits!"));


    }

    private static String fileNameConverter(String initFileName) {
        String fileFormat = initFileName.substring(initFileName.indexOf(".")+1, initFileName.length());
        return (initFileName.substring(0,initFileName.indexOf("."))+"2user").concat(".").concat(fileFormat);
    }
}
