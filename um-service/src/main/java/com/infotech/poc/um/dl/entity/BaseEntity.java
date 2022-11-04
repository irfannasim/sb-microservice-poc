package com.infotech.poc.um.dl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@JsonIgnoreProperties(value = {"createdOn", "updatedOn"}, allowGetters = true)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_ON", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @CreatedDate
    private ZonedDateTime createdOn;

    @Column(name = "UPDATED_ON", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private ZonedDateTime updatedOn;

}
