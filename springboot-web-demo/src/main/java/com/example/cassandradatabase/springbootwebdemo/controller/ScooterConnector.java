package com.example.cassandradatabase.springbootwebdemo.controller;


import com.example.cassandradatabase.springbootwebdemo.ResouceNotFoundException;
import com.example.cassandradatabase.springbootwebdemo.ScooterRepoNetwork.ScooterRepo;
import com.example.cassandradatabase.springbootwebdemo.model.Scooter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScooterConnector {


    @Autowired
    ScooterRepo scooterRepo;

    @PostMapping("/scooters")
    public Scooter addScooter(@RequestBody Scooter scooter){
        scooterRepo.save(scooter);
        return scooter;

    }

    @GetMapping("/scooters/{id}")
    public ResponseEntity<Scooter> findById(@PathVariable("id") Integer scooterId){
        Scooter scooter=scooterRepo.findById(scooterId).orElseThrow(
                () -> new ResouceNotFoundException("Scooter not found" + scooterId));
        return ResponseEntity.ok().body(scooter);
    }



    @GetMapping("/scooters")
    public List<Scooter> getScooters(){

        return scooterRepo.findAll();
    }

    @PutMapping("scooters/{id}") public ResponseEntity<Scooter> updateScooter(@PathVariable(value = "id") Integer scooterId,
    @RequestBody Scooter scooterDetails) {
        Scooter scooter = scooterRepo.findById(scooterId)
                .orElseThrow(() -> new ResouceNotFoundException("Scooter not found for this id :: " + scooterId));
        scooter.setStatus(scooterDetails.getStatus());
        final Scooter updatedScooter = scooterRepo.save(scooter);
        return ResponseEntity.ok(updatedScooter);

    }

    @DeleteMapping("scooters/{id}")
    public ResponseEntity<Void> deleteScooter(@PathVariable(value = "id") Integer scooterId) {
        Scooter scooter = scooterRepo.findById(scooterId).orElseThrow(
                () -> new ResouceNotFoundException("Scooter not found::: " + scooterId));
        scooterRepo.delete(scooter);
        return ResponseEntity.ok().build();
    }
}
