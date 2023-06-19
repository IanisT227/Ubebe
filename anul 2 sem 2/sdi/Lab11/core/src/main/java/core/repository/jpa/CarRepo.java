package core.repository.jpa;

import core.domain.Car;

public interface CarRepo extends org.springframework.data.jpa.repository.JpaRepository<Car, Integer> {
}
