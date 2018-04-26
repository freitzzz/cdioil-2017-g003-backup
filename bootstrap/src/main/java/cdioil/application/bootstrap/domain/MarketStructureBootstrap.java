package cdioil.application.bootstrap.domain;

import cdioil.application.utils.CSVCategoriesReader;
import cdioil.domain.Category;
import cdioil.domain.Code;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import cdioil.domain.QRCode;
import cdioil.domain.SKU;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

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
        SKU sku = new SKU(skuCode);
        Code qr = new QRCode(qrCode);
        String quantity = "1 L";
        Product prod = new Product("Bootstrap Product", sku,quantity,
                qr);
        marketStruct = repo.findMarketStructure();
        if (marketStruct == null) {
            CSVCategoriesReader reader = new CSVCategoriesReader(CAT_FILE);
            marketStruct = reader.readCategories();
            cat.addProduct(prod);
            marketStruct.addCategory(cat);
            repo.add(marketStruct);
        }
    }

}
