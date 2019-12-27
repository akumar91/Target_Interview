package myRetail.com.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import myRetail.com.exception.InputFormatException;
import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.exception.ProductIdMismatchException;
import myRetail.com.model.ProductModel;
import myRetail.com.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    // GET request to fetch the product details of the product based on product id
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductModel> findProductById(@PathVariable("id") int productId)
            throws ProductDetailsNotFoundException {
        ProductModel productInfo = productDetailsService.findProductDetailsByProductId(productId);
        //productInfo.add(linkTo(methodOn(ProductsController.class).findProductById(productId)).
                //withSelfRel().withType("GET"));
        return new ResponseEntity<>(productInfo, new HttpHeaders(), HttpStatus.OK);
    }

    // PUT request to update the price of the product based on product id
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> SaveProductById(@Valid @RequestBody ProductModel productModel,
                                                BindingResult validationResult,
                                                @PathVariable("id") int productId) throws InvalidFormatException {
        if (validationResult.hasErrors()) {
            StringBuilder fields = new StringBuilder();
            for (FieldError error: validationResult.getFieldErrors()) {
                if (error.getDefaultMessage() != null && !error.getDefaultMessage().isEmpty()) {
                    fields.append(error.getField()).append(" because ")
                            .append(error.getDefaultMessage()).append(" , ");
                } else {
                    fields.append(error.getField()).append(" , ");
                }

            }
            throw new InputFormatException("Invalid request fields: " + fields.toString());
        }

        if (productId != productModel.getProductId()) {
            throw new ProductIdMismatchException("Product Id mismatch from URI and body");
        }

        productDetailsService.updatePriceByProductId(productModel);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

}
