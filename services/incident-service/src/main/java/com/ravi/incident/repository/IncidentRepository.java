package com.ravi.incident.repository;

import com.ravi.incident.domain.Incident;
import com.ravi.incident.domain.IncidentSeverity;
import com.ravi.incident.domain.IncidentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    List<Incident> findByStatus(IncidentStatus status);

    List<Incident> findBySeverity(IncidentSeverity severity);

    List<Incident> findByServiceName(String serviceName);

    boolean existsByIncidentNumber(String incidentNumber);

    @Query("""
            SELECT i
            FROM Incident i
            WHERE (:status IS NULL OR i.status = :status)
              AND (:severity IS NULL OR i.severity = :severity)
              AND (
                  :serviceName IS NULL
                  OR LOWER(i.serviceName) = LOWER(:serviceName)
              )
            """)
    Page<Incident> searchIncidents(
            @Param("status") IncidentStatus status,
            @Param("severity") IncidentSeverity severity,
            @Param("serviceName") String serviceName,
            Pageable pageable
    );

    @Query(value = "SELECT nextval('incident_number_seq')", nativeQuery = true)
    Long getNextIncidentNumber();
}