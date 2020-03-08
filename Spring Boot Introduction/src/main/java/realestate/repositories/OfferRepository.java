package realestate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realestate.domain.entities.Offer;

import java.math.BigDecimal;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    @Query(value = "SELECT o " +
            "From Offer o " +
            "WHERE o.apartmentType = :familyApartmentType " +
            "  AND o.apartmentRate + (o.apartmentRate * o.agencyCommission / 100) <= :familyBudget")
    Offer findByApartmentTypeAndApartmentRateLessThanEqual(@Param(value = "familyApartmentType") String familyApartmentType, @Param(value = "familyBudget") BigDecimal familyBudget);
}