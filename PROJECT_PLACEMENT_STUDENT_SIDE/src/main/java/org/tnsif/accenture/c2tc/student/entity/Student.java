package org.tnsif.accenture.c2tc.student.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can contain at most 100 characters")
    private String name;

    @NotBlank(message = "USN is required")
    @Size(max = 20, message = "USN can contain at most 20 characters")
    @Column(unique = true, nullable = false)
    private String usn;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a 10-digit number")
    private String phone;

    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department can contain at most 50 characters")
    private String department;

    @NotBlank(message = "Qualification is required")
    @Size(max = 100, message = "Qualification can contain at most 100 characters")
    private String qualification;

    @NotBlank(message = "Course is required")
    @Size(max = 100, message = "Course can contain at most 100 characters")
    private String course;

    @Min(value = 1990, message = "Year of passing must be after 1990")
    @Max(value = 2100, message = "Year of passing is invalid")
    private Integer yearOfPassing;

    @NotBlank(message = "Hall ticket number is required")
    @Size(max = 30, message = "Hall ticket number can contain at most 30 characters")
    @Column(unique = true, nullable = false)
    private String hallTicketNumber;

    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0.0")
    @DecimalMax(value = "10.0", message = "CGPA cannot be above 10.0")
    private Double cgpa;

    @Size(max = 255, message = "Certificate file name can contain at most 255 characters")
    private String certificateFileName;

    @Size(max = 100, message = "Certificate content type can contain at most 100 characters")
    private String certificateContentType;

    @Lob
    private byte[] certificateData;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    public Student() {
    }

    public Student(Long id, String name, String usn, String email, String phone,
                   String department, String qualification, String course,
                   Integer yearOfPassing, String hallTicketNumber, Double cgpa,
                   String certificateFileName, String certificateContentType,
                   byte[] certificateData, String password) {
        this.id = id;
        this.name = name;
        this.usn = usn;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.qualification = qualification;
        this.course = course;
        this.yearOfPassing = yearOfPassing;
        this.hallTicketNumber = hallTicketNumber;
        this.cgpa = cgpa;
        this.certificateFileName = certificateFileName;
        this.certificateContentType = certificateContentType;
        this.certificateData = certificateData;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public String getHallTicketNumber() {
        return hallTicketNumber;
    }

    public void setHallTicketNumber(String hallTicketNumber) {
        this.hallTicketNumber = hallTicketNumber;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public String getCertificateFileName() {
        return certificateFileName;
    }

    public void setCertificateFileName(String certificateFileName) {
        this.certificateFileName = certificateFileName;
    }

    public String getCertificateContentType() {
        return certificateContentType;
    }

    public void setCertificateContentType(String certificateContentType) {
        this.certificateContentType = certificateContentType;
    }

    public byte[] getCertificateData() {
        return certificateData;
    }

    public void setCertificateData(byte[] certificateData) {
        this.certificateData = certificateData;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }
}

