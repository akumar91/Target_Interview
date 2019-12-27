package myRetail.com.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ProductsRestControllerExceptionHandler {

    // exception thrown when there is no information for the productId in the database
    @ExceptionHandler(ProductDetailsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ProductsRestControllerExceptionResponse ProductDetailsNotFound (ProductDetailsNotFoundException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

    // exception thrown when there is Mismatch in the input
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProductsRestControllerExceptionResponse NumberFormatException (NumberFormatException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage("Invalid input format, " + ex.getMessage() + ". It should be an integer");
        return response;
    }

    // exception thrown for invalid format
    @ExceptionHandler(InputFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProductsRestControllerExceptionResponse InputFormatException (InputFormatException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

    // exception thrown when wrong data format is provided in json body for put request
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProductsRestControllerExceptionResponse InvalidFormatException (InvalidFormatException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage("Please check the datatype of supplied body.");
        return response;
    }

    // exception thrown when there is Mismatch in the input
    @ExceptionHandler(ProductIdMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProductsRestControllerExceptionResponse ProductIdMismatchException (ProductIdMismatchException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

    //exception thrown when redskytarget give 4** exceptions
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProductsRestControllerExceptionResponse HttpClientError (HttpClientErrorException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

    //exception thrown when we can't fetch title from redskytarget.com
    @ExceptionHandler(JsonParsingException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ProductsRestControllerExceptionResponse JsonParsingException (JsonParsingException ex){
        ProductsRestControllerExceptionResponse response = new ProductsRestControllerExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

}
