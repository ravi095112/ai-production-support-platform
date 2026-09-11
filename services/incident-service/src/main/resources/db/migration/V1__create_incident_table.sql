CREATE TABLE incident (
                          id UUID PRIMARY KEY,
                          incident_number VARCHAR(50) NOT NULL UNIQUE,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          severity VARCHAR(20) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          service_name VARCHAR(100) NOT NULL,
                          assigned_to VARCHAR(100),
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                          updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
                          version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_incident_status
    ON incident(status);

CREATE INDEX idx_incident_severity
    ON incident(severity);

CREATE INDEX idx_incident_service_name
    ON incident(service_name);