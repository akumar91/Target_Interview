package myRetail.com.service;

import myRetail.com.dao.ProductPriceRepository;
import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.dao.ProductDetailsDao;
import myRetail.com.model.CurrentPriceModel;
import myRetail.com.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductDetailsDao productDetailsDao;

    public ProductModel findProductDetailsByProductId(int productId) throws ProductDetailsNotFoundException {
        // get currency details from the database
        CurrentPriceModel currentPriceModel = productPriceRepository.findByProductId(productId);

        // get product name from redsky target api
        ProductModel productModel = productDetailsDao.findProductNameById(productId);

        // if there is currency details for the product id
        if (currentPriceModel != null && !productModel.getName().isEmpty()) {
            productModel.setCurrentPrice(currentPriceModel);
            return productModel;
        }
        throw new ProductDetailsNotFoundException("Product details not found with product id " + productId);
    }

    public void updatePriceByProductId(ProductModel productModel) {
        // get currency details from the database
        CurrentPriceModel currentPriceModel = productPriceRepository.findByProductId(productModel.getProductId());

        if (currentPriceModel != null) {
            currentPriceModel.setValue(productModel.getCurrentPrice().getValue());
            currentPriceModel.setCurrencyCode(productModel.getCurrentPrice().getCurrencyCode());
            productPriceRepository.save(currentPriceModel);
        } else {
            throw new ProductDetailsNotFoundException("Product price details not found with product id {"
                    + productModel.getProductId() + "} to update the record");
        }
    }

}
