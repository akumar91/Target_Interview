package myRetail.com.unit.dao;

import myRetail.com.dao.ProductDetailsDaoImpl;
import myRetail.com.exception.ProductDetailsNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsDaoTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private ProductDetailsDaoImpl productDetailsDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findProductNameById() throws IOException {
        String result = "{ \"product\": {\"product_description\": {\"title\": \"The Big Lebowski (Blu-ray)\"}}}";

        when(this.restTemplateMock.getForObject(any(String.class), eq(String.class))).thenReturn(result);

        this.productDetailsDao.findProductNameById(13860429);

        verify(this.restTemplateMock, times(1))
                .getForObject(any(String.class), eq(String.class));
    }

    @Test(expected = ProductDetailsNotFoundException.class)
    public void findProductNameById_ThrowsProductDetailsNotFoundException() {
        doThrow(new ProductDetailsNotFoundException("Product details not found with product id")).
                when(this.restTemplateMock).getForObject(any(String.class), eq(String.class));

        this.productDetailsDao.findProductNameById(13860429);
    }


}
