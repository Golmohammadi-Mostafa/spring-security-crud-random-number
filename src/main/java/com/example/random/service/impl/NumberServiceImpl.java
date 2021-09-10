package com.example.random.service.impl;


import com.example.random.domain.Number;
import com.example.random.dto.NumberDTO;
import com.example.random.dto.ResponseDTO;
import com.example.random.exception.CustomException;
import com.example.random.repository.NumberRepository;
import com.example.random.service.NumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class NumberServiceImpl implements NumberService {

    private static final Long MIN = 3_000_000L;
    private static final Long MAX = 6_000_000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberServiceImpl.class);
    private final NumberRepository numberRepository;

    public NumberServiceImpl(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Override
    public ResponseDTO generateRandomNumber() {
        numberRepository.deleteAll();
        LOGGER.info("Deleted All Numbers.......");
        List<Number> numbers = generate();
        numberRepository.saveAll(numbers);
        LOGGER.info("Added {} Number successfully...", numbers.size());
        return new ResponseDTO("Added 1000 Number successfully into DataBase");
    }

    @Override
    public List<NumberDTO> getNumbers(Long i) {
        LOGGER.info("Request to find ten smaller/bigger and nearest to this number: {} ", i);

        if (MIN > i || MAX < i) {
            throw new CustomException("number is out of range", HttpStatus.BAD_REQUEST);
        }

        List<Number> numbers = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 10);
        numbers.addAll(numberRepository.findByValueLessThanEqual(i, pageable));
        numbers.addAll(numberRepository.findByValueGreaterThanEqual(i, pageable));

        return convertEntityToDto(numbers);
    }

    private List<NumberDTO> convertEntityToDto(List<Number> numbers) {
        List<NumberDTO> numberDTOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(numbers)) {
            LOGGER.info("numbers is empty....");
            return numberDTOS;
        }
        numbers.stream().sorted(Comparator.comparingLong(Number::getValue)).forEach(n -> {
            NumberDTO numberDTO = new NumberDTO();
            numberDTO.setValue(n.getValue());
            numberDTOS.add(numberDTO);
        });
        return numberDTOS;
    }


    private List<Number> generate() {

        List<Number> numbers = new ArrayList<>();
        ThreadLocalRandom.current().longs(MIN, MAX).distinct().limit(1000).forEach(n -> {
            Number number = new Number();
            number.setValue(n);
            numbers.add(number);
        });

        return numbers;
    }
}
