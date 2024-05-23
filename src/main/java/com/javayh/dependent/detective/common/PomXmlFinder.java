package com.javayh.dependent.detective.common;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author HaiJiYang
 */
public class PomXmlFinder {

    public static List<String> finder(String directoryPath) throws IOException {
        List<String> filePath = new LinkedList<>();
        Path startPath = Paths.get(directoryPath);
        EnumSet<FileVisitOption> opts = EnumSet.noneOf(FileVisitOption.class);
        FindPomXmlVisitor visitor = new FindPomXmlVisitor(filePath);
        Files.walkFileTree(startPath, opts, Integer.MAX_VALUE, visitor);
        return filePath;
    }

    private static class FindPomXmlVisitor extends SimpleFileVisitor<Path> {
        List<String> filePath;

        public FindPomXmlVisitor(List<String> filePath) {
            this.filePath = filePath;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if ("pom.xml".equals(file.getFileName().toString())) {
                Path path = file.toAbsolutePath();
                filePath.add(path.toString());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }
    }
}
