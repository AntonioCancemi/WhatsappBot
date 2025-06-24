package com.whatsbot.management.controller;

import com.whatsbot.management.dto.AvailabilityDto;
import com.whatsbot.management.dto.ServiceDto;
import com.whatsbot.management.service.AvailabilityService;
import com.whatsbot.management.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/booking")
@RequiredArgsConstructor
public class AdminController {

    private final ServiceOfferingService serviceService;
    private final AvailabilityService availabilityService;

    @GetMapping("/services")
    public List<ServiceDto> services() {
        return serviceService.list();
    }

    @PostMapping("/services")
    public ServiceDto createService(@RequestBody ServiceDto dto) {
        return serviceService.create(dto);
    }

    @PutMapping("/services/{id}")
    public ServiceDto updateService(@PathVariable UUID id, @RequestBody ServiceDto dto) {
        return serviceService.update(id, dto);
    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable UUID id) {
        serviceService.delete(id);
    }

    @GetMapping("/availability")
    public List<AvailabilityDto> availability() {
        return availabilityService.list();
    }

    @PostMapping("/availability")
    public AvailabilityDto createAvailability(@RequestBody AvailabilityDto dto) {
        return availabilityService.create(dto);
    }

    @PutMapping("/availability/{id}")
    public AvailabilityDto updateAvailability(@PathVariable UUID id, @RequestBody AvailabilityDto dto) {
        return availabilityService.update(id, dto);
    }

    @DeleteMapping("/availability/{id}")
    public void deleteAvailability(@PathVariable UUID id) {
        availabilityService.delete(id);
    }
}
