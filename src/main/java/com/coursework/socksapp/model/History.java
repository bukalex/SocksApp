package com.coursework.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class History {
    private OperationType operationType;
    private LocalDateTime localDateTime;
    private int quantity;
    private Size size;
    private int cottonPart;
    private Color color;
}
