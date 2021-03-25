package solidTech;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MainApp {

	public static void main(String[] args) throws MalformedURLException {
		Document doc = null;
		FileWriter writer = null;

		for (String pathString : args) {
			try {
				doc = FileProcessor.getFileReader(pathString);
				writer = FileProcessor.getOutputFileWriter(FileProcessor.inputFileName);

				Recipe recipe = new Recipe();
				List<Ingredient> ingredients = new ArrayList<Ingredient>();
				NodeList nodelist = doc.getElementsByTagName("ingredient");
				recipe.setName(doc.getDocumentElement().getAttribute("name"));
				recipe.setCurrency(doc.getDocumentElement().getAttribute("currency"));

				for (int i = 0; i < nodelist.getLength(); i++) {
					Node n = nodelist.item(i);
					String[] namePrice = n.getTextContent().strip().split("\n");
					ingredients.add(new Ingredient(namePrice[0], namePrice[1].strip()));
				}
				recipe.setIngredients(ingredients);

				StringBuilder output = new StringBuilder();
				output.append("Recipe name: " + recipe.getName() + "\n");
				output.append("Currency: " + recipe.getCurrency() + "\n");
				output.append("List of ingredients \n");
				ingredients.forEach((x) -> {
					output.append(x.getName() + " " + "(" + x.getPrice() + ")" + "\n");
				});
				
				output.append("Total Ingredients Count: " + ingredients.size() +"\n");
				output.append("Total Ingredients Cost: " + ingredients.size() +"\n");
				
				writer.append(output.toString());

			} catch (FileNotFoundException e) {
				System.out.println("Could not find the file!");
			} catch (IOException e) {
				System.out.println("Could not read the file!");
			} catch (ParserConfigurationException e) {
				System.out.println("The file is not a correct xml file!");
			} catch (SAXException e) {
				System.out.println("The file is not a correct xml file!");
			} finally {
				FileProcessor.closeWriter(writer);
			}
		}

	}

}
