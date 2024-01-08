package com.example.demo.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Car;
import com.example.demo.domain.CarRepository;

import lombok.RequiredArgsConstructor;


//@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
	private final CarRepository carRepository;
	
	@GetMapping
	public List<Car> getCars() {
		return carRepository.findAll();
	}
	
	@GetMapping("/curOrAfter/{year}")
	public List<Car> getCarsByModelDate(@PathVariable("year") int year) {
		return carRepository.findByACurOrAfterModleYearOrderByIt(year);
	}
}
