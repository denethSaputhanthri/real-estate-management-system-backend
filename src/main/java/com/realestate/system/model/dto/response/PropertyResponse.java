package com.realestate.system.model.dto.response;

import com.realestate.system.enums.PropertyStatus;
import com.realestate.system.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PropertyResponse {

    private Long id;

    private String title;

    private String description;

    private PropertyType type;

    private BigDecimal price;

    private BigDecimal size;

    private String location;

    private PropertyStatus status;

    private Long ownerId;

    private Long agentId;

    private List<String> imageUrls;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
