package org.tnsif.accenture.c2tc.student.service;

import java.util.List;
import java.util.Optional;

import org.tnsif.accenture.c2tc.student.entity.Application;
import org.tnsif.accenture.c2tc.student.entity.Certificate;
import org.tnsif.accenture.c2tc.student.entity.Company;
import org.tnsif.accenture.c2tc.student.entity.Student;

public interface StudentService {

    Student registerStudent(Student student);

    Optional<Student> loginStudent(String email, String password);

    Student updateProfile(Long studentId, Student updatedStudent);

    List<Company> getEligibleCompanies(Double cgpa);

    Application applyForCompany(Long studentId, Long companyId);

    List<Application> viewApplications(Long studentId);

    List<Object[]> viewApplicationRows(Long studentId);

    void deleteApplication(Long studentId, Long companyId);

    void deleteStudentAccount(Long studentId);

    Student getStudentById(Long studentId);

    Boolean addCertificate(Certificate certificate);

    Boolean updateCertificate(Certificate certificate);
}

