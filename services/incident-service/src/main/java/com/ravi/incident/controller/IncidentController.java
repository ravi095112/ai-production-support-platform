package com.ravi.incident.controller;

import com.ravi.incident.domain.IncidentSeverity;
import com.ravi.incident.domain.IncidentStatus;
import com.ravi.incident.dto.CreateIncidentRequest;
import com.ravi.incident.dto.IncidentPageResponse;
import com.ravi.incident.dto.IncidentResponse;
import com.ravi.incident.dto.UpdateIncidentRequest;
import com.ravi.incident.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse createIncident(
            @Valid @RequestBody CreateIncidentRequest request) {

        return incidentService.createIncident(request);
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncident(@PathVariable UUID id) {

        return incidentService.getIncident(id);
    }

    @GetMapping
    public IncidentPageResponse searchIncidents(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentSeverity severity,
            @RequestParam(required = false) String serviceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be greater than or equal to 0");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Size must be between 1 and 100"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return incidentService.searchIncidents(
                status,
                severity,
                serviceName,
                pageable
        );
    }

    @PatchMapping("/{id}")
    public IncidentResponse updateIncident(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateIncidentRequest request) {

        return incidentService.updateIncident(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncident(@PathVariable UUID id) {

        incidentService.deleteIncident(id);
    }
}