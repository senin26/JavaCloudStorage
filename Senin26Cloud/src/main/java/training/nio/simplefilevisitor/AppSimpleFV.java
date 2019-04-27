package training.nio.simplefilevisitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class AppSimpleFV {


    public static void main(String[] args) throws IOException {
         /*   Path path = Paths.get("1.txt");
            path = path.toAbsolutePath();
        System.out.println(path.toString());
        System.out.println(path.getName(2));
        System.out.println(path.getNameCount());

        Files.copy(path, Paths.get("2.txt"), StandardCopyOption.REPLACE_EXISTING);*/

        Path path = Paths.get("11");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
