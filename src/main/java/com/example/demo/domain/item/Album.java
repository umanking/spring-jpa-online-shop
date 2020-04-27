package com.example.demo.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Geonguk Han
 * @since 2020-04-27
 */
@Entity
@Getter
@Setter
@DiscriminatorValue(value = "A")
public class Album extends Item {
    private String artist;
    private String etc;
}
