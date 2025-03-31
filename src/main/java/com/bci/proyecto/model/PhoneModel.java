package com.bci.proyecto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHONE_ID")
    private Long phoneId;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "CITY_CODE")
    private String cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserModel user;
}
