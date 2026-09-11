package com.ravi.incident.dto;

import com.ravi.incident.domain.IncidentSeverity;
import com.ravi.incident.domain.IncidentStatus;

import java.time.Instant;
import java.util.UUID;

public record IncidentResponse(
        UUID id,
        String incidentNumber,
        String title,
        String description,
        IncidentSeverity severity,
        IncidentStatus status,
        String serviceName,
        String assignedTo,
        Instant createdAt,
        Instant updatedAt,
        Long version
) {
}