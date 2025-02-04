package x_clients.rest_assured.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Company(int id, String name, String description, Boolean isActive) {

}
