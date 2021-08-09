package com.example.springboot_security403;

import com.nimbusds.oauth2.sdk.id.Actor;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
