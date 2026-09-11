package com.ravi.incident.dto;

import com.ravi.incident.domain.IncidentSeverity;
import com.ravi.incident.domain.IncidentStatus;
import jakarta.validation.constraints.Size;

public record UpdateIncidentRequest(

        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        String description,

        IncidentSeverity severity,

        IncidentStatus status,

        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String serviceName,

        @Size(max = 100, message = "Assigned to must not exceed 100 characters")
        String assignedTo
) {
}