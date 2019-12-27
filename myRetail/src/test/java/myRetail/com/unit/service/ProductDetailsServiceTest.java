package myRetail.com.unit.service;

import myRetail.com.dao.ProductDetailsDao;
import myRetail.com.dao.ProductPriceRepository;
import myRetail.com.exception.ProductDetailsNotFoundException;
import myRetail.com.model.CurrentPriceModel;
import myRetail.com.model.ProductModel;
import myRetail.com.service.ProductDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsServiceTest {

    @Mock
    private ProductDetailsDao productDetailsDaoMock;

    @Mock
    private ProductPriceRepository productPriceRepositoryMock;

    @Mock
    private CurrentPriceModel currencyPriceModelMock;

    @Mock
    private ProductModel productModelMock;

    @InjectMocks
    private ProductDetailsServiceImpl productDetailsServiceImpl;

    @Captor
    ArgumentCaptor<CurrentPriceModel> currentPriceModelArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findProductDetailsByProductId() {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(1360429);
        productModel.setName("FightClub");

        when(this.productPriceRepositoryMock.findByProductId(13860429)).thenReturn(this.currencyPriceModelMock);
        when(this.productDetailsDaoMock.findProductNameById(13860429)).thenReturn(productModel);

        this.productDetailsServiceImpl.findProductDetailsByProductId(13860429);

        verify(this.productPriceRepositoryMock, times(1)).findByProductId(eq(13860429));
        verify(this.productDetailsDaoMock, times(1)).findProductNameById(eq(13860429));
    }

    @Test(expected = ProductDetailsNotFoundException.class)
    public void findProductDetailsByProductId_ThrowsProductDetailsNotFoundException() {
        CurrentPriceModel currentPriceModel = null;

        when(this.productPriceRepositoryMock.findByProductId(13860429)).thenReturn(currentPriceModel);
        when(this.productDetailsDaoMock.findProductNameById(13860429)).thenReturn(this.productModelMock);

        this.productDetailsServiceImpl.findProductDetailsByProductId(13860429);
    }

    @Test
    public void updatePriceByProductId() {
        CurrentPriceModel currentPriceModel = new CurrentPriceModel();
        currentPriceModel.setCurrencyCode("USD");
        currentPriceModel.setValue(145);

        ProductModel productModel = new ProductModel();
        productModel.setProductId(1360429);
        productModel.setName("FightClub");
        productModel.setCurrentPrice(currentPriceModel);

        when(this.productPriceRepositoryMock.findByProductId(any(Integer.class))).thenReturn(currentPriceModel);

        this.productDetailsServiceImpl.updatePriceByProductId(productModel);

        verify(this.productPriceRepositoryMock, times(1))
                .save(currentPriceModelArgumentCaptor.capture());

        CurrentPriceModel currentPriceModelCaptor = currentPriceModelArgumentCaptor.getValue();

        assertEquals(productModel.getCurrentPrice().getValue(),
                currentPriceModelCaptor.getValue(), 0);
    }

    @Test(expected = ProductDetailsNotFoundException.class)
    public void updatePriceByProductId_ThrowsProductDetailsNotFoundException() {
        when(this.productPriceRepositoryMock.findByProductId(any(Integer.class))).thenReturn(null);

        this.productDetailsServiceImpl.updatePriceByProductId(this.productModelMock);
    }

}
