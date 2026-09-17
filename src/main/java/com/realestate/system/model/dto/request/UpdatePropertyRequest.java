package com.realestate.system.model.dto.request;

import com.realestate.system.enums.PropertyStatus;
import com.realestate.system.enums.PropertyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class UpdatePropertyRequest {

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    private String description;

    private PropertyType type;

    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Size must be greater than or equal to 0")
    private BigDecimal size;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    private PropertyStatus status;

    private Long agentId;

    private List<String> imageUrls;
}
