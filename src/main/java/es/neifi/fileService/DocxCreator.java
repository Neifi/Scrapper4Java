package es.neifi.fileService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import es.neifi.model.News;

public class DocxCreator {

	private static ArrayList<String> getFilesFromFolder(File folder) {
		ArrayList<String> fileList = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getFilesFromFolder(fileEntry);
			} else {

				fileList.add(fileEntry.getName());
			}
		}
		return fileList;
	}

	public static void create(News news) throws Docx4JException {
		Path path = Paths.get("resources/.");
		//ArrayList<String> fileList = getFilesFromFolder(path.toFile());
		
		WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

		String h1 = news.getTitle();

		String paragraph = news.getContent();
		mainDocumentPart.addStyledParagraphOfText("Title", h1);
		h1 = h1.replaceAll("[\\p{InCombiningDiacriticalMarks}|:|\"|?|¿|/]", "").replace(" ", "_").toLowerCase();
		mainDocumentPart.addStyledParagraphOfText("Subtitle", "Date " + news.getDate() + " " + news.getCategory());
		mainDocumentPart.addParagraphOfText(paragraph);

		File exportFile = new File(path+h1 + ".docx");

		wordPackage.save(exportFile);
		// TODO preguntar remplazar si existe
		
	}

}