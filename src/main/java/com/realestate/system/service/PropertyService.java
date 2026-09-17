package com.realestate.system.service;

import com.realestate.system.enums.PropertyStatus;
import com.realestate.system.enums.PropertyType;
import com.realestate.system.model.dto.request.CreatePropertyRequest;
import com.realestate.system.model.dto.request.UpdatePropertyRequest;
import com.realestate.system.model.dto.response.PropertyResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyService {

    PropertyResponse createProperty(CreatePropertyRequest request);

    PropertyResponse getPropertyById(Long id);

    List<PropertyResponse> getAllProperties();

    PropertyResponse updateProperty(Long id, UpdatePropertyRequest request);

    void deleteProperty(Long id);

    List<PropertyResponse> searchProperties(
            String location,
            PropertyType type,
            PropertyStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

}
