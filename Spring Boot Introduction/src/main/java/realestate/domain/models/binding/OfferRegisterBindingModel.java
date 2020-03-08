package realestate.domain.models.binding;

import java.math.BigDecimal;

public class OfferRegisterBindingModel {
    private BigDecimal apartmentRate;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public OfferRegisterBindingModel() {
    }

    public BigDecimal getApartmentRate() {
        return apartmentRate;
    }

    public void setApartmentRate(BigDecimal apartmentRate) {
        this.apartmentRate = apartmentRate;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public BigDecimal getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
}