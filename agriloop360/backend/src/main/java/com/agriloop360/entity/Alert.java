package com.agriloop360.entity;

import com.agriloop360.enumtype.ModuleName;
import com.agriloop360.enumtype.Severity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModuleName module;

    @Column(nullable = false, length = 1000)
    private String message;

    private Boolean readStatus = false;

    private LocalDateTime timestamp = LocalDateTime.now();

    public Alert() {}

    public Alert(Long id, Severity severity, ModuleName module, String message, Boolean readStatus, LocalDateTime timestamp) {
        this.id = id;
        this.severity = severity;
        this.module = module;
        this.message = message;
        this.readStatus = readStatus != null ? readStatus : false;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static AlertBuilder builder() {
        return new AlertBuilder();
    }

    public static class AlertBuilder {
        private Long id;
        private Severity severity;
        private ModuleName module;
        private String message;
        private Boolean readStatus = false;
        private LocalDateTime timestamp = LocalDateTime.now();

        public AlertBuilder id(Long id) { this.id = id; return this; }
        public AlertBuilder severity(Severity severity) { this.severity = severity; return this; }
        public AlertBuilder module(ModuleName module) { this.module = module; return this; }
        public AlertBuilder message(String message) { this.message = message; return this; }
        public AlertBuilder readStatus(Boolean readStatus) { this.readStatus = readStatus; return this; }
        public AlertBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public Alert build() {
            return new Alert(id, severity, module, message, readStatus, timestamp);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public ModuleName getModule() { return module; }
    public void setModule(ModuleName module) { this.module = module; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Boolean getReadStatus() { return readStatus; }
    public void setReadStatus(Boolean readStatus) { this.readStatus = readStatus; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
