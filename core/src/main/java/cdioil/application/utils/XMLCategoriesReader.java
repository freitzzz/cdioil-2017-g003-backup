package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class XMLCategoriesReader implements CategoriesReader {

    /**
     * File to read.
     */
    private final File file;
    /**
     * List with the Categories that were read.
     */
    private List<Category> listCategories;

    /**
     * Creates an instance of CSVCategoriesReader, receiving the name of the file to read.
     *
     * @param file File to read
     */
    public XMLCategoriesReader(File file) {
        this.file = file;
        listCategories = new LinkedList<>();
    }

    /**
     * Imports Categories from a .csv file.
     *
     * @return List with the Categories that were read. Null if the file is not valid
     */
    @Override
    public MarketStructure readCategories() {
        MarketStructure me = new MarketStructure();
        try {
            File xmlFile = file;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            //lista_categorias
            Element lista_categorias = doc.getDocumentElement();
            //<categoria>
            NodeList categorias = lista_categorias.getElementsByTagName("categoria");
            read(categorias, me);
        } catch (Exception e) {
            ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
        }
        return me;
    }

    public void read(NodeList categorias, MarketStructure me) {

        for (int i = 0; i < categorias.getLength(); i++) {
            Node node = categorias.item(i);
            Element categoria = (Element) node;
            //<DC>
            Node DC = categoria.getElementsByTagName("DC").item(0);
            Element elementDC = (Element) DC;
            String dcID = elementDC.getAttribute("id");
            String descritivoDC = DC.getTextContent();

            String path = dcID + "DC";
            Category cat = new Category(descritivoDC, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }
            //<UN>
            Node UN = categoria.getElementsByTagName("UN").item(0);
            Element elementUN = (Element) UN;
            String unID = elementUN.getAttribute("id");
            String descritivoUN = UN.getTextContent();

            path = dcID + "DC" + "-" + unID + "UN";
            cat = new Category(descritivoUN, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }
            //<CAT>
            Node CAT = categoria.getElementsByTagName("CAT").item(0);
            Element elementCAT = (Element) CAT;
            String catID = elementCAT.getAttribute("id");

            //<descritivo_cat>
            Node node_descritivo_cat = elementCAT.getElementsByTagName("descritivo_cat").item(0);
            String dec_cat = node_descritivo_cat.getTextContent();
            //<lista_SCAT>
            Node node_lista_scat = elementCAT.getElementsByTagName("lista_scat").item(0);
            Element elementListScat = (Element) node_lista_scat;

            path = dcID + "DC" + "-" + unID + "UN" + "-" + catID + "CAT";
            cat = new Category(dec_cat, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }

            if (node_lista_scat != null) {
                //<SCAT>
                NodeList lista_scats = elementListScat.getElementsByTagName("SCAT");
                for (int b = 0; b < lista_scats.getLength(); b++) {
                    Node scats = lista_scats.item(b);
                    Element elementSCAT = (Element) scats;
                    String scatID = elementSCAT.getAttribute("id");
                    Node node_descritivo_scat = elementSCAT.getElementsByTagName("descritivo_scat").item(0);
                    String dec_scat = node_descritivo_scat.getTextContent();
                    //<lista_ub>
                    Node node_lista_ub = elementSCAT.getElementsByTagName("lista_ub").item(0);
                    Element elementListUb = (Element) node_lista_ub;

                    path = dcID + "DC" + "-" + unID + "UN" + "-" + catID + "CAT" + "-" + scatID + "SCAT";
                    cat = new Category(dec_scat, path.trim());
                    if (me.addCategory(cat)) {
                        listCategories.add(cat);
                    }
                    if (node_lista_ub != null) {
                        //<UB>
                        NodeList lista_ubs = elementListUb.getElementsByTagName("UB");
                        for (int c = 0; c < lista_ubs.getLength(); c++) {
                            Node ibs = lista_ubs.item(c);
                            Element elementUB = (Element) ibs;
                            int ubID = Integer.parseInt(elementUB.getAttribute("id"));
                            String dec_ub = elementUB.getTextContent();
                            path = dcID + "DC" + "-" + unID + "UN" + "-" + catID + "CAT" + "-" + scatID + "SCAT" + "-" + ubID + "UB";
                            cat = new Category(dec_ub, path.trim());
                            if (me.addCategory(cat)) {
                                listCategories.add(cat);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getNumberOfCategoriesRead() {
        return listCategories.size();
    }

}
