package core.repository.jpa;

import core.domain.CompositeKey;
import core.domain.Fueling;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelingRepo extends JpaRepository<Fueling, CompositeKey> {
}
