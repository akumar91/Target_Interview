package myRetail.com.dao;

import myRetail.com.model.ProductModel;

public interface ProductDetailsDao {

    // find product name based on id from the redsky api
    ProductModel findProductNameById(int productId);
}
