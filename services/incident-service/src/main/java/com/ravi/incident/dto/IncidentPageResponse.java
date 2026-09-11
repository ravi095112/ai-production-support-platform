package com.ravi.incident.dto;

import java.util.List;

public record IncidentPageResponse(
        List<IncidentResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}