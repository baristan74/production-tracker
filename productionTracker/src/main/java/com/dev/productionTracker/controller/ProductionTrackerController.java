package com.dev.productionTracker.controller;

import com.dev.productionTracker.dto.CreateProductionTrackerRequest;
import com.dev.productionTracker.dto.CreateUserRequest;
import com.dev.productionTracker.service.ProductionTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productionTracker")
@CrossOrigin
public class ProductionTrackerController {
    private final ProductionTrackerService productionTrackerService;

    public ProductionTrackerController(ProductionTrackerService productionTrackerService){
        this.productionTrackerService = productionTrackerService;
    }

    @GetMapping("/getAllProductionTrackerByIsEnabled")
    public ResponseEntity<?> getAllProductionTrackerByIsEnabled() {
        return ResponseEntity.ok(productionTrackerService.getAllProductionTrackerByIsEnabled());
    }

    @PostMapping("/addNewProductionTracker")
    public ResponseEntity<?> addNewUser(@RequestBody CreateProductionTrackerRequest createProductionTrackerRequest) {
        return ResponseEntity.ok(productionTrackerService.createProductionTracker(createProductionTrackerRequest));
    }

    @PostMapping("/deleteProductionTracker")
    public ResponseEntity<?> getAllProductionTrackerByIsEnabled(@RequestParam("productionTrackerId") Long productionTrackerId) {
        return ResponseEntity.ok(productionTrackerService.deleteProductionTracker(productionTrackerId));
    }
}
