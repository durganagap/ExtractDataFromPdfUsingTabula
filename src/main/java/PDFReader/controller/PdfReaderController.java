package PDFReader.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.Rectangle;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.detectors.DetectionAlgorithm;
import technology.tabula.detectors.NurminenDetectionAlgorithm;
import technology.tabula.extractors.BasicExtractionAlgorithm;

public class PdfReaderController {
	 protected static ObjectExtractor extractor;
	 protected static List<Integer> pages;
	 private static BasicExtractionAlgorithm basicExtractor = new BasicExtractionAlgorithm();

	 @SuppressWarnings("rawtypes")
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		PDDocument document = PDDocument.load(new File("test.pdf"));
		extractor = new ObjectExtractor(document);
	    PageIterator iterator = (pages == null) ? extractor.extract() : extractor.extract(pages);
		while(iterator.hasNext()) {
			Page page = iterator.next();
			DetectionAlgorithm detector = new NurminenDetectionAlgorithm();
		    List<Rectangle> guesses = detector.detect(page);
		    List<Table> tables = new ArrayList<Table>();
	        for (Rectangle guessRect : guesses) {
	          Page guess = page.getArea(guessRect);
	          tables.addAll(basicExtractor.extract(guess));
	        }
	        for(Table currentTable: tables) {
	        	List<List<RectangularTextContainer>> rows = currentTable.getRows();
	        	 for (int i = 0; i < rows.size(); i++) {
	        	      List<RectangularTextContainer> row = rows.get(i);
	        	      for (RectangularTextContainer cell : row) {
	        	    	  System.out.println(cell.getText().trim());
	        	      }
	        	 }
	        }
			 }
		}
}
