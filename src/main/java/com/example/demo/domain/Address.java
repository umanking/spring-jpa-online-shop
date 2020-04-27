package com.example.demo.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @author Geonguk Han
 * @since 2020-04-27
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;
}
