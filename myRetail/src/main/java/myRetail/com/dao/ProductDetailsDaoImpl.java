package myRetail.com.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import myRetail.com.exception.JsonParsingException;
import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ProductDetailsDaoImpl implements ProductDetailsDao {

    @Autowired
    RestTemplate restTemplate;


    public ProductModel findProductNameById(int productId) {

        ProductModel productModel = new ProductModel();

        // Build URL
        String url = "https://redsky.target.com/v1/pdp/tcin/" + productId
                + "/?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews," +
                "rating_and_review_statistics,question_answer_statistics";

        // Call service
        String result;
        try {
            result = restTemplate.getForObject(url, String.class);
        } catch (Exception ex) {
            throw new ProductDetailsNotFoundException("Product details not found with product id "
                    + productId + " from redsky.target.com API");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(result);
            JsonNode productDescriptionNode = node.findValue("product_description");
            String productName = productDescriptionNode.path("title").asText();
            productModel.setName(productName);
            productModel.setProductId(productId);
        } catch (Exception e) {
            throw new JsonParsingException("Could not fetch title for product id " + productId +
                    " from redsky.target.com API");
        }

        return productModel;
    }

}
