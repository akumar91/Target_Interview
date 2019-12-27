package myRetail.com.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import myRetail.com.model.CurrentPriceModel;

@Repository
public interface ProductPriceRepository extends MongoRepository<CurrentPriceModel, Integer> {

    // get currency details from the database based on productId
    CurrentPriceModel findByProductId(Integer productId);

}