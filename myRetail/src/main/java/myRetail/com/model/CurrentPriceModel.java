package myRetail.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "details")
public class CurrentPriceModel {

    @NotEmpty(message = "currency_code of the product cannot be null or empty")
    @JsonProperty("currency_code")
    private String currencyCode;

    @NotNull
    @Min(value = 0, message = "Price could not be less than 0")
    @JsonProperty("value")
    private double value;

    @JsonIgnore
    private Integer productId;

    @Id
    @JsonIgnore
    private String id;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
