package es.neifi.fileService;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import com.sun.star.text.XTextDocument;

import es.neifi.model.News;
import java.text.Normalizer;
import com.sun.star.uno.UnoRuntime;

import com.sun.star.uno.XComponentContext;
import ooo.connector.BootstrapSocketConnector;

public class FileService {

	public static void createTxt(ArrayList<News> news) throws IOException {
		for (News notice : news) {

			String newName = Normalizer.normalize(notice.getTitle(), Normalizer.Form.NFD);
			newName = newName.replaceAll("[\\p{InCombiningDiacriticalMarks}|:|\"|?|¿|/]", "").replace(" ", "_")
					.toLowerCase();
			PrintWriter printWriter = new PrintWriter("files/" + newName + ".txt");

			printWriter.println(notice.getDate());
			printWriter.println(notice.getTitle());
			printWriter.println(notice.getSubtitle());
			printWriter.println(notice.getContent());
			printWriter.println(notice.getUrl());
			printWriter.close();
			// printWriter.println(notice.getAuthor().getName());
			// printWriter.println(notice.getLastUpdate());

		}

	}

	public static void createWriter(ArrayList<News> textArray) {
		// "Bootstrap UNO y obtiene el contexto del componente remoto
		com.sun.star.uno.XComponentContext xContext = null;
		try {
			// get the remote office component context

			xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
			// para el error no office executable found

			com.sun.star.lang.XMultiComponentFactory xMCF = xContext.getServiceManager();

			// create a new instance of the desktop
			Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);

			// get the component laoder from the desktop to create a new
			// text document
			com.sun.star.frame.XComponentLoader xCLoader = UnoRuntime
					.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
			com.sun.star.beans.PropertyValue[] szEmptyArgs = new com.sun.star.beans.PropertyValue[0];
			String strDoc = "private:factory/swriter";

			System.out.println("create new text document");

			com.sun.star.lang.XComponent xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);

			if (xContext != null)
				System.out.println("Connected to a running office ...");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		for (News news : textArray) {

//		System.out.println("Opening an empty Writer document");
//
//		com.sun.star.text.XTextDocument myDoc = openWriter(xContext);
//		
//		//Para insertar texto se obtiene el Text-Object del documento y se crea un cursor
//		com.sun.star.text.XText xText = myDoc.getText();
//		com.sun.star.text.XTextCursor xTCursor = xText.createTextCursor();
//		//Ahora es posible insertar texto en la posición del cursor con el metodo insertString
//		
//		xText.insertString(xTCursor,news.getDate()+"" , false);
//			xText.insertString(xTCursor,news.getTitle()+"\n\n" , false);
//			xText.insertString(xTCursor,news.getSubtitle() , false);
//			xText.insertString(xTCursor,news.getContent() , false);
//			xText.insertString(xTCursor,"_________________________________________________________________________________" , false);
//			
		}

	}

	private static com.sun.star.text.XTextDocument openWriter(com.sun.star.uno.XComponentContext xContext) {
		// define variables
		com.sun.star.frame.XComponentLoader xCLoader;
		com.sun.star.text.XTextDocument xDoc = null;
		com.sun.star.lang.XComponent xComp = null;

		try {
			// get the remote office service manager
			com.sun.star.lang.XMultiComponentFactory xMCF = xContext.getServiceManager();

			Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);

			xCLoader = UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
			com.sun.star.beans.PropertyValue[] szEmptyArgs = new com.sun.star.beans.PropertyValue[0];
			String strDoc = "private:factory/swriter";
			xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);
			xDoc = UnoRuntime.queryInterface(com.sun.star.text.XTextDocument.class, xComp);

		} catch (Exception e) {
			System.err.println(" Exception " + e);
			e.printStackTrace(System.err);
		}
		return xDoc;
	}

	public static void renderToPdf(File file) {

	}

}
