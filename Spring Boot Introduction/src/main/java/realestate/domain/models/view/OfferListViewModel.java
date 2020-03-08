package realestate.domain.models.view;

import java.math.BigDecimal;

public class OfferListViewModel {
    private BigDecimal apartmentRate;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public OfferListViewModel() {
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
