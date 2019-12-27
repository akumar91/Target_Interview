package myRetail.com.service;

import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.model.ProductModel;

public interface ProductDetailsService {

    ProductModel findProductDetailsByProductId(int productId) throws ProductDetailsNotFoundException;

    void updatePriceByProductId(ProductModel productModel);
}
