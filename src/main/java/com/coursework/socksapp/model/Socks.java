package com.coursework.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Socks {
    private Color color;
    private Size size;
    private int cottonPart;
}
