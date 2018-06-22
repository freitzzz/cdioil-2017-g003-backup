package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.files.InputSchemaFiles;
import cdioil.files.ValidatorXML;
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
 * Imports Categories from XML files.
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
    private final List<Category> listCategories;
    /**
     * Schema file (XSD) used for validating the input file.
     */
    private static final File SCHEMA_FILE = new File(InputSchemaFiles.LOCALIZATION_SCHEMA_PATH_CATEGORY);
    /**
     * String with the identifier to category.
     */
    private static final String CATEGORY = "categoria";
    /**
     * String with the identifier to list of the scats.
     */
    private static final String LIST_SCAT = "lista_scat";
    /**
     * String with the identifier to lists of the ubs.
     */
    private static final String LIST_UB = "lista_ub";
    /**
     * String with the identifier to lists of the categories.
     */
    private static final String LIST_CATEGORIES = "lista_categorias";
    /**
     * String with the CAT identifier.
     */
    private static final String DEC_CAT = "descritivo_cat";
    /**
     * String with the SCAT identifier.
     */
    private static final String DEC_SCAT = "descritivo_scat";
    /**
     * String with the ID identifier.
     */
    private static final String ID = "id";
    /**
     * String with the DC identifier.
     */
    private static final String DC = "DC";
    /**
     * String with the UN identifier.
     */
    private static final String UN = "UN";
    /**
     * String with the CAT identifier.
     */
    private static final String CAT = "CAT";
    /**
     * String with the SCAT identifier.
     */
    private static final String SCAT = "SCAT";
    /**
     * String with the UB identifier.
     */
    private static final String UB = "UB";

    /**
     * Creates an instance of CSVCategoriesReader, receiving the name of the file to read.
     *
     * @param file File to read
     */
    public XMLCategoriesReader(String file) {
        this.file = new File(file);
        listCategories = new LinkedList<>();
    }

    /**
     * Imports Categories from a XML file.
     *
     * @return MarketStructur with the Categories that were read. Null if the file is not valid
     */
    @Override
    public MarketStructure readCategories() {
        if (!ValidatorXML.validateFile(SCHEMA_FILE, file)) {
            return null;
        }
        MarketStructure me = new MarketStructure();
        try {
            File xmlFile = file;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            Element lista_categorias = doc.getDocumentElement();
            NodeList categorias = lista_categorias.getElementsByTagName(CATEGORY);
            read(categorias, me);
        } catch (Exception e) {
            ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
        }
        return me;
    }

    /**
     * Reads the information os the XMl file
     *
     * @param categorias NodeList of the categories
     * @param me MArketStructur
     */
    public void read(NodeList categorias, MarketStructure me) {

        for (int i = 0; i < categorias.getLength(); i++) {
            Node node = categorias.item(i);
            Element categoria = (Element) node;
            //<DC>
            Node DCNode = categoria.getElementsByTagName(DC.toLowerCase()).item(0);
            Element elementDC = (Element) DCNode;
            String dcID = elementDC.getAttribute(ID);
            String descritivoDC = DCNode.getTextContent();

            String path = dcID + DC;
            Category cat = new Category(descritivoDC, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }
            //<UN>
            Node UNNode = categoria.getElementsByTagName(UN.toLowerCase()).item(0);
            Element elementUN = (Element) UNNode;
            String unID = elementUN.getAttribute(ID);
            String descritivoUN = UNNode.getTextContent();

            path = dcID + DC + "-" + unID + UN;
            cat = new Category(descritivoUN, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }
            //<CAT>
            Node CATNode = categoria.getElementsByTagName(CAT.toLowerCase()).item(0);
            Element elementCAT = (Element) CATNode;
            String catID = elementCAT.getAttribute(ID);

            //<descritivo_cat>
            Node node_descritivo_cat = elementCAT.getElementsByTagName(DEC_CAT).item(0);
            String dec_cat = node_descritivo_cat.getTextContent();
            //<lista_SCAT>
            Node node_lista_scat = elementCAT.getElementsByTagName(LIST_SCAT).item(0);
            Element elementListScat = (Element) node_lista_scat;

            path = dcID + DC + "-" + unID + UN + "-" + catID + CAT;
            cat = new Category(dec_cat, path.trim());
            if (me.addCategory(cat)) {
                listCategories.add(cat);
            }

            if (node_lista_scat != null) {
                //<SCAT>
                NodeList lista_scats = elementListScat.getElementsByTagName(SCAT.toLowerCase());
                for (int b = 0; b < lista_scats.getLength(); b++) {
                    Node scats = lista_scats.item(b);
                    Element elementSCAT = (Element) scats;
                    String scatID = elementSCAT.getAttribute(ID);
                    Node node_descritivo_scat = elementSCAT.getElementsByTagName(DEC_SCAT).item(0);
                    String dec_scat = node_descritivo_scat.getTextContent();
                    //<lista_ub>
                    Node node_lista_ub = elementSCAT.getElementsByTagName(LIST_UB).item(0);
                    Element elementListUb = (Element) node_lista_ub;

                    path = dcID + DC + "-" + unID + UN + "-" + catID + CAT + "-" + scatID + SCAT;
                    cat = new Category(dec_scat, path.trim());
                    if (me.addCategory(cat)) {
                        listCategories.add(cat);
                    }
                    if (node_lista_ub != null) {
                        //<UB>
                        NodeList lista_ubs = elementListUb.getElementsByTagName(UB.toLowerCase());
                        for (int c = 0; c < lista_ubs.getLength(); c++) {
                            Node ibs = lista_ubs.item(c);
                            Element elementUB = (Element) ibs;
                            int ubID = Integer.parseInt(elementUB.getAttribute(ID));
                            String dec_ub = elementUB.getTextContent();
                            path = dcID + DC + "-" + unID + UN + "-" + catID + CAT + "-" + scatID + SCAT + "-" + ubID + UB;
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

    /**
     * Returns the number of Categories in the list of Categories.
     *
     * @return the number of Categories that were read
     */
    @Override
    public int getNumberOfCategoriesRead() {
        return listCategories.size();
    }

}
