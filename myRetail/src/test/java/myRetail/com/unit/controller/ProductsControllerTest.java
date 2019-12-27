package myRetail.com.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myRetail.com.controller.ProductsController;
import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.exception.ProductIdMismatchException;
import myRetail.com.model.CurrentPriceModel;
import myRetail.com.model.ProductModel;
import myRetail.com.service.ProductDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {
    private static final String PRODUCT_URL = "/products/1360429";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductDetailsService productDetailsService;

    @Autowired
    ProductsController productsController;

    @Captor
    ArgumentCaptor<ProductModel> productModelCaptor;

    @Test
    public void getProductDetails_Returns404() throws Exception {
        doThrow(new ProductDetailsNotFoundException("Product details not found with product id 1360429")).
                when(productDetailsService).findProductDetailsByProductId(1360429);
        mockMvc.perform(get(PRODUCT_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage", is("Product details not found with product id 1360429")));

    }

    @Test
    public void getProductDetails_Returns200() throws Exception {
        CurrentPriceModel currentPriceModel = new CurrentPriceModel();
        currentPriceModel.setCurrencyCode("USD");
        currentPriceModel.setValue(29.76);

        ProductModel productModel = new ProductModel();
        productModel.setProductId(1360429);
        productModel.setName("FightClub");
        productModel.setCurrentPrice(currentPriceModel);

        when(productDetailsService.findProductDetailsByProductId(any(Integer.class))).thenReturn(productModel);

        mockMvc.perform(get(PRODUCT_URL).accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1360429)))
                .andExpect(jsonPath("$.name", is("FightClub")))
                .andExpect(jsonPath("$.current_price.currency_code", is("USD")))
                .andExpect(jsonPath("$.current_price.value", is(29.76)));
                //.andExpect(jsonPath("$._links.self.href", endsWith(PRODUCT_URL)));

        verify(this.productDetailsService, times(1)).
                findProductDetailsByProductId(eq(1360429));
    }

    @Test
    public void updateProductDetails_Returns204() throws Exception {
        CurrentPriceModel currentPriceModel = new CurrentPriceModel();
        currentPriceModel.setCurrencyCode("USD");
        currentPriceModel.setValue(26.00);

        ProductModel productModel = new ProductModel();
        productModel.setProductId(1360429);
        productModel.setName("FightClub");
        productModel.setCurrentPrice(currentPriceModel);

        ObjectMapper objectMapper = new ObjectMapper();
        String productModelPayload = objectMapper.writeValueAsString(productModel);

        doNothing().when(productDetailsService).updatePriceByProductId(any(ProductModel.class));

        mockMvc.perform(put(PRODUCT_URL).contentType("application/json").content(productModelPayload))
                .andExpect(status().isNoContent());

        verify(this.productDetailsService, times(1)).
                updatePriceByProductId(productModelCaptor.capture());

        ProductModel createdProductModel = productModelCaptor.getValue();

        assertEquals(productModel.getCurrentPrice().getValue(),
                createdProductModel.getCurrentPrice().getValue(), 0);
    }

    @Test
    public void updateProductDetails_Returns400() throws Exception {
        CurrentPriceModel currentPriceModel = new CurrentPriceModel();
        currentPriceModel.setCurrencyCode("USD");
        currentPriceModel.setValue(26.00);

        ProductModel productModel = new ProductModel();
        productModel.setProductId(13);
        productModel.setName("FightClub");
        productModel.setCurrentPrice(currentPriceModel);

        ObjectMapper objectMapper = new ObjectMapper();
        String productModelPayload = objectMapper.writeValueAsString(productModel);

        doThrow(new ProductIdMismatchException("Product Id mismatch from URI and body")).
                when(productDetailsService).updatePriceByProductId(any(ProductModel.class));

        mockMvc.perform(put(PRODUCT_URL).contentType("application/json").content(productModelPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", is("Product Id mismatch from URI and body")));

    }
}