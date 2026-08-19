package com.agriloop360.dto;

public class SimulationControlDto {
    private Boolean active;
    private Integer intervalSeconds;
    private String message;

    public SimulationControlDto() {}

    public SimulationControlDto(Boolean active, Integer intervalSeconds, String message) {
        this.active = active;
        this.intervalSeconds = intervalSeconds;
        this.message = message;
    }

    public static SimulationControlDtoBuilder builder() {
        return new SimulationControlDtoBuilder();
    }

    public static class SimulationControlDtoBuilder {
        private Boolean active;
        private Integer intervalSeconds;
        private String message;

        public SimulationControlDtoBuilder active(Boolean active) { this.active = active; return this; }
        public SimulationControlDtoBuilder intervalSeconds(Integer intervalSeconds) { this.intervalSeconds = intervalSeconds; return this; }
        public SimulationControlDtoBuilder message(String message) { this.message = message; return this; }

        public SimulationControlDto build() {
            return new SimulationControlDto(active, intervalSeconds, message);
        }
    }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Integer getIntervalSeconds() { return intervalSeconds; }
    public void setIntervalSeconds(Integer intervalSeconds) { this.intervalSeconds = intervalSeconds; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
