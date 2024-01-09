package com.example.demo.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Car;
import com.example.demo.domain.CarRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@Slf4j
public class CarController {
	private final CarRepository carRepository;
	
	@GetMapping
	public List<Car> getCars() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("user: {}", authentication.getName());
		return carRepository.findAll();
	}
	
	@GetMapping("/curOrAfter/{year}")
	public List<Car> getCarsByModelDate(@PathVariable("year") int year) {
		return carRepository.findByACurOrAfterModleYearOrderByIt(year);
	}
}
