package com.growtalents.controller;

import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.helper.DateValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from GrowTalents Backend! Date: " + DateValidator.formatDate(LocalDate.now());
    }
    
    @GetMapping("/date-validation")
    public ResponseEntity<GlobalResponse<String>> testDateValidation(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate testDate) {
        
        String formattedDate = DateValidator.formatDate(testDate);
        String message = "Date validation successful: " + formattedDate;
        
        return ResponseEntity.ok(GlobalResponse.success(message, formattedDate));
    }
}
