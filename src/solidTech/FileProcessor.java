package solidTech;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FileProcessor {
	
	public static String inputFileName;

	public static Document getFileReader(String pathString)
			throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
		String pathRegex = "^\\w+:";
		Pattern pattern = Pattern.compile(pathRegex);
		Matcher matcher = pattern.matcher(pathString);
		URL url = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		if (matcher.find()) {
			url = new URL(pathString);
			inputFileName = url.getFile();
			doc = db.parse(url.openStream());
		} else {
			Path path = Paths.get(pathString);
			if (Files.exists(path)) {
				InputStream in = new FileInputStream(path.toFile());
				inputFileName = path.getFileName().toString();
				doc = db.parse(in);
			} else {
				throw new FileNotFoundException();
			}
		}
		return doc;
	}

	public static FileWriter getOutputFileWriter(String filePath) throws IOException {
		String outputFileRegex = "/(\\w+).xml$";
		Pattern pattern = Pattern.compile(outputFileRegex);
		Matcher matcher = pattern.matcher(filePath);
		StringBuilder filename = new StringBuilder();
		if (matcher.find()) {
			filename.append(matcher.group(1));
			filename.append(".output");
		}
		Path outputPath = Paths.get("resources", filename.toString());
		if(!Files.exists(outputPath)) {
			outputPath.toFile().createNewFile();
		}
		return new FileWriter(outputPath.toFile());
	}
	
	public static void closeWriter(FileWriter writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("Could not close file");
			}
		}
	}

}
