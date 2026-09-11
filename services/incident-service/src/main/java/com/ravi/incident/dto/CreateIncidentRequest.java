package com.ravi.incident.dto;

import com.ravi.incident.domain.IncidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIncidentRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        String description,

        @NotNull(message = "Severity is required")
        IncidentSeverity severity,

        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String serviceName,

        @Size(max = 100, message = "Assigned to must not exceed 100 characters")
        String assignedTo
) {
}