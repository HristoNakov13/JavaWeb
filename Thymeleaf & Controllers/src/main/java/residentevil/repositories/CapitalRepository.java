package residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import residentevil.domain.entities.Capital;

import java.util.List;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {

    @Query("SELECT c FROM Capital c ORDER BY c.name")
    List<Capital> findAllAndOrderByName();
}
