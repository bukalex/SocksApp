package com.coursework.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostRequest {
    private Socks socks;
    private int quantity;
}
