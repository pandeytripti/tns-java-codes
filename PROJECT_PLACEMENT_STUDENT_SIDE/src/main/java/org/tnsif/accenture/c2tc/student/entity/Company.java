package org.tnsif.accenture.c2tc.student.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    @Size(max = 120, message = "Company name can contain at most 120 characters")
    private String companyName;

    @NotBlank(message = "Role is required")
    @Size(max = 80, message = "Role can contain at most 80 characters")
    private String role;

    @NotNull(message = "Package amount is required")
    @DecimalMin(value = "0.0", message = "Package amount cannot be negative")
    private Double packageAmount;

    @NotNull(message = "Eligibility CGPA is required")
    @DecimalMin(value = "0.0", message = "Eligibility CGPA cannot be negative")
    private Double eligibilityCgpa;

    @NotNull(message = "Last date is required")
    @FutureOrPresent(message = "Last date cannot be in the past")
    private LocalDate lastDate;

    public Company() {
    }

    public Company(Long id, String companyName, String role, Double packageAmount,
                   Double eligibilityCgpa, LocalDate lastDate) {
        this.id = id;
        this.companyName = companyName;
        this.role = role;
        this.packageAmount = packageAmount;
        this.eligibilityCgpa = eligibilityCgpa;
        this.lastDate = lastDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public Double getEligibilityCgpa() {
        return eligibilityCgpa;
    }

    public void setEligibilityCgpa(Double eligibilityCgpa) {
        this.eligibilityCgpa = eligibilityCgpa;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }
}

