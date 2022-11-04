package com.infotech.poc.um.dl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DPT_ID")
    private Long dptId;
}
