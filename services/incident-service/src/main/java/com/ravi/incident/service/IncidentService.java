package com.ravi.incident.service;

import com.ravi.incident.domain.Incident;
import com.ravi.incident.domain.IncidentSeverity;
import com.ravi.incident.domain.IncidentStatus;
import com.ravi.incident.dto.CreateIncidentRequest;
import com.ravi.incident.dto.IncidentPageResponse;
import com.ravi.incident.dto.IncidentResponse;
import com.ravi.incident.dto.UpdateIncidentRequest;
import com.ravi.incident.exception.ResourceNotFoundException;
import com.ravi.incident.repository.IncidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public IncidentResponse createIncident(CreateIncidentRequest request) {

        Incident incident = new Incident();

        incident.setIncidentNumber("INC-" + incidentRepository.getNextIncidentNumber());
        incident.setTitle(request.title());
        incident.setDescription(request.description());
        incident.setSeverity(request.severity());
        incident.setStatus(IncidentStatus.OPEN);
        incident.setServiceName(request.serviceName());
        incident.setAssignedTo(request.assignedTo());

        Incident savedIncident = incidentRepository.save(incident);

        return toResponse(savedIncident);
    }

    @Transactional(readOnly = true)
    public IncidentResponse getIncident(UUID id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Incident not found: " + id
                        ));

        return toResponse(incident);
    }

    @Transactional(readOnly = true)
    public List<IncidentResponse> getAllIncidents() {

        return incidentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IncidentResponse updateIncident(
            UUID id,
            UpdateIncidentRequest request) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Incident not found: " + id
                        ));

        if (request.title() != null) {
            incident.setTitle(request.title());
        }

        if (request.description() != null) {
            incident.setDescription(request.description());
        }

        if (request.severity() != null) {
            incident.setSeverity(request.severity());
        }

        if (request.status() != null) {
            incident.setStatus(request.status());
        }

        if (request.serviceName() != null) {
            incident.setServiceName(request.serviceName());
        }

        if (request.assignedTo() != null) {
            incident.setAssignedTo(request.assignedTo());
        }

        return toResponse(incidentRepository.save(incident));
    }

    public void deleteIncident(UUID id) {

        if (!incidentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Incident not found: " + id
            );
        }

        incidentRepository.deleteById(id);
    }

    private IncidentResponse toResponse(Incident incident) {

        return new IncidentResponse(
                incident.getId(),
                incident.getIncidentNumber(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getServiceName(),
                incident.getAssignedTo(),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                incident.getVersion()
        );
    }

    @Transactional(readOnly = true)
    public IncidentPageResponse searchIncidents(
            IncidentStatus status,
            IncidentSeverity severity,
            String serviceName,
            Pageable pageable) {

        Page<Incident> page = incidentRepository.searchIncidents(
                status,
                severity,
                serviceName,
                pageable
        );

        return new IncidentPageResponse(
                page.getContent()
                        .stream()
                        .map(this::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}