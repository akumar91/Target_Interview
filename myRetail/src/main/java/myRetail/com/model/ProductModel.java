package myRetail.com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductModel {

    @NotNull
    @JsonProperty("id")
    private Integer productId;

    @NotEmpty(message = "name of the product cannot be null or empty")
    @JsonProperty("name")
    private String name;

    @Valid
    @JsonProperty("current_price")
    private CurrentPriceModel currentPrice;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public CurrentPriceModel getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(CurrentPriceModel currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setName(String name) {

        this.name = name;
    }
}
