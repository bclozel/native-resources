package org.example;

import java.io.IOException;
import java.lang.System.Logger.Level;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
	static System.Logger logger = System.getLogger("main");
	
	public static void main(String[] args) {
		String scheme = args[0];
		String root = args[1];
		logger.log(Level.INFO, "scheme: {0}, root: {1} ", scheme, root);

		try (FileSystem fileSystem = FileSystems.newFileSystem(URI.create(scheme), Map.of())) {
			fileSystem.getRootDirectories().forEach(rootDir -> logger.log(Level.INFO, "root directory: {0}", rootDir));
			Path rootPath = fileSystem.getPath(root);
			try (Stream<Path> files = Files.walk(rootPath)) {
				files.forEach(filePath -> logger.log(Level.INFO, "|- " + filePath));
			}
		}
		catch (IOException exc) {
			logger.log(Level.ERROR, exc);
		}
	}

}