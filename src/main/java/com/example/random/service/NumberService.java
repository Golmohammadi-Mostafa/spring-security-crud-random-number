package com.example.random.service;


import com.example.random.dto.NumberDTO;
import com.example.random.dto.ResponseDTO;

import java.util.List;

public interface NumberService {
    ResponseDTO generateRandomNumber();

    List<NumberDTO> getNumbers(Long i);
}