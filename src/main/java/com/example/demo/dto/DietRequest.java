package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DietRequest {
    private LocalDate date; // e.g. "2023-11-20"
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snacks;
}