package com.coursework.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Socks {
    private Color color;
    private Size size;
    private int cottonPart;
}
