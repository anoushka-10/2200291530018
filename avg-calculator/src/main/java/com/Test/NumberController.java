package com.Test;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Test.Model.AverageResponse;
import com.Test.Service.NumberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/numbers")
@RequiredArgsConstructor
@Slf4j
public class NumberController {

    private final NumberService numberService;

    @GetMapping("/{numberId}")
    public ResponseEntity<AverageResponse> getNumbers(@PathVariable String numberId) {
        log.info("Received request for number type: {}", numberId);
        
        if (!isValidNumberId(numberId)) {
            log.error("Invalid number id: {}", numberId);
            return ResponseEntity.badRequest().build();
        }
        
        AverageResponse response = numberService.processNumberRequest(numberId);
        return ResponseEntity.ok(response);
    }
    
    private boolean isValidNumberId(String numberId) {
        return numberId != null && 
               (numberId.equals("p") || 
                numberId.equals("f") || 
                numberId.equals("e") || 
                numberId.equals("r"));
    }
}