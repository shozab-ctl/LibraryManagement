package com.management.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
    private String message;
    private T data;
}
