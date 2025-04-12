package com.java.customer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerNotFoundExceptipn extends RuntimeException {
    private final String msg;
}
