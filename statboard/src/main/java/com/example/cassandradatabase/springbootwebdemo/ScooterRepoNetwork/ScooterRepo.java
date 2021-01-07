package com.example.cassandradatabase.springbootwebdemo.ScooterRepoNetwork;

import com.example.cassandradatabase.springbootwebdemo.model.Scooter;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ScooterRepo extends CassandraRepository<Scooter, Integer> {
}
