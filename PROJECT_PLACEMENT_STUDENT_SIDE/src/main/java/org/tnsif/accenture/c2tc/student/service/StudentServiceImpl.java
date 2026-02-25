package org.tnsif.accenture.c2tc.student.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.tnsif.accenture.c2tc.student.entity.Application;
import org.tnsif.accenture.c2tc.student.entity.Certificate;
import org.tnsif.accenture.c2tc.student.entity.Company;
import org.tnsif.accenture.c2tc.student.entity.Student;
import org.tnsif.accenture.c2tc.student.repository.ApplicationRepository;
import org.tnsif.accenture.c2tc.student.repository.CertificateRepository;
import org.tnsif.accenture.c2tc.student.repository.CompanyRepository;
import org.tnsif.accenture.c2tc.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;
    private final CertificateRepository certificateRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              CompanyRepository companyRepository,
                              ApplicationRepository applicationRepository,
                              CertificateRepository certificateRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.applicationRepository = applicationRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    @Transactional
    public Student registerStudent(Student student) {
        if (studentRepository.existsByUsn(student.getUsn())) {
            throw new IllegalArgumentException("USN already exists.");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (studentRepository.existsByHallTicketNumber(student.getHallTicketNumber())) {
            throw new IllegalArgumentException("Hall ticket number already exists.");
        }
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> loginStudent(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }

    @Override
    @Transactional
    public Student updateProfile(Long studentId, Student updatedStudent) {
        Student existingStudent = getStudentById(studentId);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setQualification(updatedStudent.getQualification());
        existingStudent.setCourse(updatedStudent.getCourse());
        existingStudent.setYearOfPassing(updatedStudent.getYearOfPassing());
        existingStudent.setCgpa(updatedStudent.getCgpa());
        if (updatedStudent.getCertificateData() != null && updatedStudent.getCertificateData().length > 0) {
            existingStudent.setCertificateData(updatedStudent.getCertificateData());
            existingStudent.setCertificateFileName(updatedStudent.getCertificateFileName());
            existingStudent.setCertificateContentType(updatedStudent.getCertificateContentType());
        }
        existingStudent.setPassword(updatedStudent.getPassword());
        return studentRepository.save(existingStudent);
    }

    @Override
    public List<Company> getEligibleCompanies(Double cgpa) {
        return companyRepository.findByEligibilityCgpaLessThanEqual(cgpa)
                .stream()
                .sorted(Comparator.comparing(Company::getLastDate))
                .toList();
    }

    @Override
    @Transactional
    public Application applyForCompany(Long studentId, Long companyId) {
        Student student = getStudentById(studentId);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found."));

        if (student.getCgpa() < company.getEligibilityCgpa()) {
            throw new IllegalArgumentException("You are not eligible for this company.");
        }

        Optional<Application> existingApplication = applicationRepository.findByStudentAndCompany(student, company);
        if (existingApplication.isPresent()) {
            throw new IllegalArgumentException("You have already applied to this company.");
        }

        Application application = new Application();
        application.setStudent(student);
        application.setCompany(company);
        application.setStatus("APPLIED");

        return applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> viewApplications(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> viewApplicationRows(Long studentId) {
        return applicationRepository.findApplicationRowsByStudentId(studentId);
    }

    @Override
    @Transactional
    public void deleteApplication(Long studentId, Long companyId) {
        Application application = applicationRepository.findByStudentIdAndCompanyId(studentId, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));
        applicationRepository.delete(application);
    }

    @Override
    @Transactional
    public void deleteStudentAccount(Long studentId) {
        Student student = getStudentById(studentId);
        applicationRepository.deleteByStudentId(studentId);
        studentRepository.delete(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found."));
    }

    @Override
    @Transactional
    public Boolean addCertificate(Certificate certificate) {
        Certificate savedCertificate = certificateRepository.save(certificate);
        return savedCertificate.getId() != null;
    }

    @Override
    @Transactional
    public Boolean updateCertificate(Certificate certificate) {
        if (certificate.getId() == null || !certificateRepository.existsById(certificate.getId())) {
            return false;
        }
        certificateRepository.save(certificate);
        return true;
    }
}

