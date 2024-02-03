package dev.weverton.ecommerce.dto;

import java.io.Serializable;

public interface BaseDTO<T> extends Serializable {
    public T toEntity();
}
