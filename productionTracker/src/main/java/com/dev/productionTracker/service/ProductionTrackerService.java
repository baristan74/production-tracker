package com.dev.productionTracker.service;

import com.dev.productionTracker.dto.CreateProductionTrackerRequest;
import com.dev.productionTracker.model.ProductionTracker;
import com.dev.productionTracker.repository.ProductionTrackerRepository;
import com.dev.productionTracker.utils.results.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionTrackerService {
    private final ProductionTrackerRepository productionTrackerRepository;

    public ProductionTrackerService(ProductionTrackerRepository productionTrackerRepository) {
        this.productionTrackerRepository = productionTrackerRepository;
    }

    public Result createProductionTracker (CreateProductionTrackerRequest createProductionTrackerRequest) {
        try{
            if(!validateProductionTracker(createProductionTrackerRequest)){
                return new ErrorResult("Invalid production tracker!");
            }
            ProductionTracker newProductionTracker = ProductionTracker.builder()
                    .productName(createProductionTrackerRequest.productName())
                    .size(createProductionTrackerRequest.size())
                    .description(createProductionTrackerRequest.description())
                    .productType(createProductionTrackerRequest.productType())
                    .quantity(createProductionTrackerRequest.quantity())
                    .build();
            productionTrackerRepository.save(newProductionTracker);
            return new SuccessResult("Production tracker created successfully");
        }catch (Exception e){
            return new ErrorResult("Error creating production tracker: " + e.getMessage());
        }
    }

    public DataResult<List<ProductionTracker>> getAllProductionTrackerByIsEnabled () {
        try{
            return new SuccessDataResult<>(productionTrackerRepository.getAllByIsEnabled(),"Production tracker listed successfully");
        }catch (Exception e){
            return new ErrorDataResult<>("Error listing production tracker: " + e.getMessage());
        }
    }

    public Result deleteProductionTracker (Long productionTrackerId) {
        try{
            Optional<ProductionTracker> optionalProductionTracker = productionTrackerRepository.findById(productionTrackerId);
            if(optionalProductionTracker.isEmpty()){
                return new ErrorResult("Production tracker not found");
            }
            ProductionTracker productionTracker = optionalProductionTracker.get();
            productionTracker.setIsEnabled(false);
            productionTrackerRepository.save(productionTracker);
            return new SuccessResult("Production tracker deleted successfully");
        }catch (Exception e){
            return new ErrorResult("Error deleted production tracker: " + e.getMessage());
        }
    }


    private boolean validateProductionTracker(CreateProductionTrackerRequest createProductionTrackerRequest) {
        if(createProductionTrackerRequest == null){
            return false;
        }
        if(createProductionTrackerRequest.productName().isEmpty() || createProductionTrackerRequest.productName().isBlank()){
            return false;
        }
        if(createProductionTrackerRequest.size().isEmpty() || createProductionTrackerRequest.size().isBlank()){
            return false;
        }
        if(createProductionTrackerRequest.description().isEmpty() || createProductionTrackerRequest.description().isBlank()){
            return false;
        }
        if(createProductionTrackerRequest.productType() == null || createProductionTrackerRequest.productType().getTypeName().isEmpty() || createProductionTrackerRequest.productType().getTypeName().isBlank()){
            return false;
        }
        if(createProductionTrackerRequest.quantity() == null || createProductionTrackerRequest.quantity().getQuantityName().isEmpty() || createProductionTrackerRequest.quantity().getQuantityName().isBlank()){
            return false;
        }
        return true;
    }




}
