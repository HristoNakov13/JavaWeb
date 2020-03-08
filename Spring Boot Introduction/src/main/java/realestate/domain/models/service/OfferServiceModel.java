package realestate.domain.models.service;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OfferServiceModel {
    private String id;
    private BigDecimal apartmentRate;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public OfferServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @DecimalMin("0.001")
    public BigDecimal getApartmentRate() {
        return apartmentRate;
    }

    public void setApartmentRate(BigDecimal apartmentRate) {
        this.apartmentRate = apartmentRate;
    }

    @NotNull
    @NotEmpty
    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    @DecimalMin("0")
    @DecimalMax("100")
    public BigDecimal getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
}
