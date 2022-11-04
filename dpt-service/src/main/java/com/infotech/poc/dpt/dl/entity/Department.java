package com.infotech.poc.dpt.dl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DEPARTMENTS")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class Department extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;
}
