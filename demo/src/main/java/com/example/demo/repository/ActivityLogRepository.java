package com.example.demo.repository;

import com.example.demo.model.ActivityLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends CrudRepository<ActivityLog,Long> {

    ActivityLog findByEmail(String email);
}
