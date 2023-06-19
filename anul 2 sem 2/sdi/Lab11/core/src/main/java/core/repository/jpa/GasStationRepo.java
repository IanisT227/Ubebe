package core.repository.jpa;

import core.domain.GasStation;

public interface GasStationRepo extends org.springframework.data.jpa.repository.JpaRepository<GasStation, Integer> {
    GasStation findByName(String name);
}
