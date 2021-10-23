package com.kristjan.demo.repository;

import com.kristjan.demo.model.DateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<DateModel, Long> {
}
