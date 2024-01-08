package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RepositoryRestResource(path = "vehicles")
public interface CarRepository extends JpaRepository<Car, Long> {
	List<Car> findByColor(@Param("color") String color);
	
	@Query("select c from Car c Where modelYear>=?1 Order By c.modelYear")
	List<Car> findByACurOrAfterModleYearOrderByIt(@Param("modelYear") int modelYear);
}
