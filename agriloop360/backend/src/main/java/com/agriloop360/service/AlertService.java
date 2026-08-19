package com.agriloop360.service;

import com.agriloop360.entity.Alert;
import com.agriloop360.enumtype.ModuleName;
import com.agriloop360.enumtype.Severity;
import com.agriloop360.exception.ResourceNotFoundException;
import com.agriloop360.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAllByOrderByTimestampDesc();
    }

    public List<Alert> getUnreadAlerts() {
        return alertRepository.findByReadStatusFalseOrderByTimestampDesc();
    }

    public List<Alert> getAlertsBySeverity(Severity severity) {
        return alertRepository.findBySeverityOrderByTimestampDesc(severity);
    }

    public List<Alert> getAlertsByModule(ModuleName module) {
        return alertRepository.findByModuleOrderByTimestampDesc(module);
    }

    public Alert markAsRead(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
        alert.setReadStatus(true);
        return alertRepository.save(alert);
    }

    public void markAllAsRead() {
        List<Alert> unread = alertRepository.findByReadStatusFalseOrderByTimestampDesc();
        unread.forEach(a -> a.setReadStatus(true));
        alertRepository.saveAll(unread);
    }

    public Long getUnreadCount() {
        return alertRepository.countByReadStatusFalse();
    }
}
