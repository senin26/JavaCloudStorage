package training.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class App {

    public void test() throws IOException, Exception {
    }


    public static void main(String[] args) throws IOException {
         /*   Path path = Paths.get("1.txt");
            path = path.toAbsolutePath();
        System.out.println(path.toString());
        System.out.println(path.getName(2));
        System.out.println(path.getNameCount());

        Files.copy(path, Paths.get("2.txt"), StandardCopyOption.REPLACE_EXISTING);*/

        Path path = Paths.get("11");
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName().toString());
                if (file.getFileName().toString().equals("5.txt")) {
                    System.out.println("Requested file is found");
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
