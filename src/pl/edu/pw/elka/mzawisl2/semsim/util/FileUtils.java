package pl.edu.pw.elka.mzawisl2.semsim.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

	public static String readFile(String path) throws IOException {
		return readFile(path, Charset.defaultCharset());
	}

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
