package common.paths;

public final class PathsHandler {

    private PathsHandler() {
    }

   /* public static final String commonPathPart = "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/";
    "/home/serj/Java/GeekBrains/GEEKBRAINS/JavaCloudStorage/Senin26Cloud/src/main/java/basic_client/clientstorage/UserTXTdoc.txt"
*/
    public static final String pathsConcatenator(String subPath1, String subPath2) {
        return subPath1.concat(subPath2);
    }

}
