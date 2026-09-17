package com.realestate.system.service.impl;

import com.realestate.system.entity.Property;
import com.realestate.system.entity.User;
import com.realestate.system.enums.PropertyStatus;
import com.realestate.system.enums.PropertyType;
import com.realestate.system.model.dto.request.CreatePropertyRequest;
import com.realestate.system.model.dto.request.UpdatePropertyRequest;
import com.realestate.system.model.dto.response.PropertyResponse;
import com.realestate.system.repository.PropertyRepository;
import com.realestate.system.repository.PropertySpecification;
import com.realestate.system.repository.UserRepository;
import com.realestate.system.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public PropertyResponse createProperty(CreatePropertyRequest request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        User agent = null;

        if (request.getAgentId() != null) {
            agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));
        }

        Property property = new Property();

        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setType(request.getType());
        property.setPrice(request.getPrice());
        property.setSize(request.getSize());
        property.setLocation(request.getLocation());
        property.setOwner(owner);
        property.setAgent(agent);
        property.setImageUrls(request.getImageUrls());

        Property savedProperty = propertyRepository.save(property);
        return mapToResponse(savedProperty);
    }

    @Override
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        return mapToResponse(property);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PropertyResponse updateProperty(Long id, UpdatePropertyRequest request) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not founded"));

        if (request.getTitle() != null) {
            existingProperty.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            existingProperty.setDescription(request.getDescription());
        }

        if (request.getType() != null) {
            existingProperty.setType(request.getType());
        }

        if (request.getPrice() != null) {
            existingProperty.setPrice(request.getPrice());
        }

        if (request.getSize() != null) {
            existingProperty.setSize(request.getSize());
        }

        if (request.getLocation() != null) {
            existingProperty.setLocation(request.getLocation());
        }

        if (request.getStatus() != null) {
            existingProperty.setStatus(request.getStatus());
        }

        if (request.getAgentId() != null) {

            User agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));

            existingProperty.setAgent(agent);
        }

        if (request.getImageUrls() != null) {
            existingProperty.setImageUrls(request.getImageUrls());
        }

        Property updatedProperty = propertyRepository.save(existingProperty);

        return mapToResponse(updatedProperty);
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        propertyRepository.delete(property);
    }

    @Override
    public List<PropertyResponse> searchProperties(String location, PropertyType type, PropertyStatus status, BigDecimal minPrice, BigDecimal maxPrice) {


        Specification<Property> specification = Specification.allOf();

        if (location != null && !location.isBlank()) {
            specification = specification.and(
                    PropertySpecification.hasLocation(location)
            );
        }

        if (type != null) {
            specification = specification.and(
                    PropertySpecification.hasType(type)
            );
        }

        if (status != null) {
            specification = specification.and(
                    PropertySpecification.hasStatus(status)
            );
        }

        if (minPrice != null) {
            specification = specification.and(
                    PropertySpecification.priceGreaterThanOrEqual(minPrice)
            );
        }

        if (maxPrice != null) {
            specification = specification.and(
                    PropertySpecification.priceLessThanOrEqual(maxPrice)
            );
        }

        return propertyRepository.findAll(specification)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PropertyResponse mapToResponse(Property property) {

        PropertyResponse response = new PropertyResponse();

        response.setId(property.getId());
        response.setTitle(property.getTitle());
        response.setDescription(property.getDescription());
        response.setType(property.getType());
        response.setPrice(property.getPrice());
        response.setSize(property.getSize());
        response.setLocation(property.getLocation());
        response.setStatus(property.getStatus());

        if (property.getOwner() != null) {
            response.setOwnerId(property.getOwner().getId());
        }

        if (property.getAgent() != null) {
            response.setAgentId(property.getAgent().getId());
        }

        response.setImageUrls(property.getImageUrls());
        response.setCreatedAt(property.getCreatedAt());
        response.setUpdatedAt(property.getUpdatedAt());

        return response;
    }
}
