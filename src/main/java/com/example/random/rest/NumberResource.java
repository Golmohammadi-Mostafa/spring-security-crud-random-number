package com.example.random.rest;


import com.example.random.dto.NumberDTO;
import com.example.random.dto.ResponseDTO;
import com.example.random.service.NumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/numbers")

public class NumberResource {


    private final NumberService numberService;

    public NumberResource(NumberService numberService) {
        this.numberService = numberService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/generate")
    public ResponseEntity<ResponseDTO> generate() {
        return ResponseEntity.ok(numberService.generateRandomNumber());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user/check-number/{number}")
    public ResponseEntity<List<NumberDTO>> getTenNearestSmallerAndBiggerNumber(@PathVariable Long number) {
        return ResponseEntity.ok(numberService.getNumbers(number));
    }
}
