package cdioil.application;

import cdioil.domain.Product;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.ProductRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Survey controller that controls surveys
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SurveyController {

    /**
     * Method that returns all existing surveys
     *
     * @return List with all existing Survey
     */
    public List<Survey> getAllSurveys() {
        List<Survey> allSurveys = new ArrayList<>();
        Iterable<Survey> iterableAllSurveys = new SurveyRepositoryImpl().findAll();
        iterableAllSurveys.forEach(nextSurvey -> allSurveys.add(nextSurvey));
        return allSurveys;
    }

    /**
     * Method that gets the surveys that a certain user can answer, based on a
     * certain pagination ID
     *
     * @param user RegisteredUser with the registered user getting his surveys
     * that he can answer
     * @param paginationID Short with the pagination ID
     * @return List with the surveys that a certain user can answer based on a
     * certain pagination ID
     */
    public List<Survey> getUserSurveys(RegisteredUser user, short paginationID) {
        return new SurveyRepositoryImpl().getAllUserSurveys(user, paginationID);
    }

    /**
     * Returns list of products that contains a certain code
     *
     * @param code product's code
     * @return list of products that contains the code
     */
    public List<Product> getProductsByCode(String code) {
        List<Product> products = new ProductRepositoryImpl().getProductsByCode(code);
        return products;
    }

    /**
     * Returns a list of surveys about a product
     *
     * @param products list of products to filter by
     * @param registeredUser user
     * @return list of surveys
     */
    public List<Survey> getSurveysByProduct(List<Product> products, RegisteredUser registeredUser) {
        List<Survey> surveyList = new ArrayList<>();
        SurveyRepositoryImpl surveyRepo = new SurveyRepositoryImpl();
        for (Product p : products) {
            List<Survey> productSurveys = surveyRepo.getActiveSurveysByProductAndRegisteredUser(p, registeredUser);
            if (productSurveys != null) {
                for (Survey s : productSurveys) {
                    if (!surveyList.contains(s)) {
                        surveyList.add(s);
                    }
                }
            }
        }
        return surveyList;
    }
}
