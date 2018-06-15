package cdioil.application.bootstrap.domain;

import cdioil.application.utils.CSVCategoriesReader;
import cdioil.domain.Category;
import cdioil.domain.Code;
import cdioil.domain.EAN;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import cdioil.domain.QRCode;
import cdioil.domain.SKU;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Market Structure Bootstrap.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class MarketStructureBootstrap {

    private static final String CAT_FILE = "Market_Structure.csv";

    /**
     * Runs the market struct bootstrap.
     */
    public MarketStructureBootstrap() {
        bootstrapMarketStruct();
    }

    /**
     * Bootstrap of the Market Strucure
     */
    private void bootstrapMarketStruct() {
        MarketStructureRepositoryImpl repo = new MarketStructureRepositoryImpl();
        MarketStructure marketStruct;
        Category cat = new Category("Bootstrap Cat", "10938DC");
        String skuCode = Integer.toString(Integer.MAX_VALUE);
        String qrCode = Integer.toString(Integer.MAX_VALUE - 1);
        String eanCode = Integer.toString(Integer.MAX_VALUE - 1);
        SKU sku = new SKU(skuCode);
        Code qr = new QRCode(qrCode);
        Code ean = new EAN(eanCode);
        String quantity = "1 L";
        Product prod = new Product("Bootstrap Product", sku,quantity,
                qr, ean);
        marketStruct = repo.findMarketStructure();
        if (marketStruct == null) {
            File catFile = findCSVFile();
            //Convert file to use a UTF-8 compliant path string
            try {
                catFile = new File(URLDecoder.decode(catFile.getAbsolutePath(), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MarketStructureBootstrap.class.getName()).log(Level.SEVERE, null, ex);
            }
            CSVCategoriesReader reader = new CSVCategoriesReader(catFile);
            marketStruct = reader.readCategories();
            cat.addProduct(prod);
            cat.addProduct(createDummyProduct());
            marketStruct.addCategory(cat);
            repo.add(marketStruct);
        }
    }

    /**
     * Gets category file from resources
     * @return category file from resources
     */
    private File findCSVFile() {
        ClassLoader cl = getClass().getClassLoader();
        return new File(cl.getResource(CAT_FILE).getFile());
    }
    /**
     * Creates a dummy product for usage of the scan functionality
     * @return Product with a dummy product to be used on the scan functionality
     */
    private Product createDummyProduct(){
        return new Product("Agua Mineral Natural",new SKU("S76900"),"20L",new EAN("8480017377760")
                ,new QRCode("Agua Mineral Natural"));
    }

}
