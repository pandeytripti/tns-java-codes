package org.tnsif.accenture.c2tc.student.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.tnsif.accenture.c2tc.student.entity.Company;
import org.tnsif.accenture.c2tc.student.entity.Student;
import org.tnsif.accenture.c2tc.student.repository.CompanyRepository;
import org.tnsif.accenture.c2tc.student.repository.StudentRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public DataSeeder(StudentRepository studentRepository, CompanyRepository companyRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedStudents();
        seedCompanies();
    }

    private void seedStudents() {
        List<Student> students = List.of(
                createStudent("Hemanth sinha", "USN2026H001", "hemanth.sinha@example.com", "9876543210",
                        "Computer Science", "B.Tech", "Information Technology", 2026, "HT2026H001", 8.2, "pass123"),
                createStudent("Krish K", "USN2026K002", "krish.k@example.com", "9876543211",
                        "Information Science", "B.E", "Software Engineering", 2026, "HT2026K002", 7.8, "pass123"),
                createStudent("Manjunath Anwari", "USN2026M003", "manjunath.anwari@example.com", "9876543212",
                        "Electronics", "B.Tech", "Embedded Systems", 2025, "HT2026M003", 8.5, "pass123")
        );

        for (Student student : students) {
            if (!studentRepository.existsByEmail(student.getEmail())) {
                studentRepository.save(student);
            }
        }
    }

    private void seedCompanies() {
        List<Company> companies = List.of(
                createCompany("Infosys", "Systems Engineer", 6.5, 6.5, LocalDate.of(2026, 8, 31)),
                createCompany("TCS", "Digital Engineer", 7.0, 6.0, LocalDate.of(2026, 9, 15)),
                createCompany("Wipro", "Project Engineer", 5.5, 6.2, LocalDate.of(2026, 10, 10)),
                createCompany("Accenture", "Associate Software Engineer", 6.8, 6.8, LocalDate.of(2026, 11, 5)),
                createCompany("Capgemini", "Analyst", 5.8, 6.0, LocalDate.of(2026, 12, 1))
        );

        for (Company company : companies) {
            if (!companyRepository.existsByCompanyNameAndRole(company.getCompanyName(), company.getRole())) {
                companyRepository.save(company);
            }
        }
    }

    private Student createStudent(String name, String usn, String email, String phone, String department,
                                  String qualification, String course, Integer yearOfPassing,
                                  String hallTicketNumber, Double cgpa, String password) {
        Student student = new Student();
        student.setName(name);
        student.setUsn(usn);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDepartment(department);
        student.setQualification(qualification);
        student.setCourse(course);
        student.setYearOfPassing(yearOfPassing);
        student.setHallTicketNumber(hallTicketNumber);
        student.setCgpa(cgpa);
        student.setPassword(password);
        return student;
    }

    private Company createCompany(String companyName, String role, Double packageAmount,
                                  Double eligibilityCgpa, LocalDate lastDate) {
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setRole(role);
        company.setPackageAmount(packageAmount);
        company.setEligibilityCgpa(eligibilityCgpa);
        company.setLastDate(lastDate);
        return company;
    }
}

