package org.tnsif.accenture.c2tc.student.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.tnsif.accenture.c2tc.student.dto.LoginRequest;
import org.tnsif.accenture.c2tc.student.entity.Certificate;
import org.tnsif.accenture.c2tc.student.entity.Company;
import org.tnsif.accenture.c2tc.student.entity.Student;
import org.tnsif.accenture.c2tc.student.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.ok("Student Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginRequest loginRequest) {
        Optional<Student> student = studentService.loginStudent(loginRequest.getEmail(), loginRequest.getPassword());
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PutMapping("/profile/{studentId}")
    public ResponseEntity<Student> updateProfile(@PathVariable Long studentId, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateProfile(studentId, student));
    }

    @GetMapping("/dashboard/{studentId}")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        int eligibleCount = studentService.getEligibleCompanies(student.getCgpa()).size();
        int appliedCount = studentService.viewApplications(studentId).size();
        return ResponseEntity.ok(Map.of(
                "student", student,
                "eligibleCount", eligibleCount,
                "appliedCount", appliedCount));
    }

    @GetMapping("/companies/{studentId}")
    public ResponseEntity<List<Company>> viewCompanies(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentService.getEligibleCompanies(student.getCgpa()));
    }

    @PostMapping("/apply/{studentId}/{companyId}")
    public ResponseEntity<?> applyForCompany(@PathVariable Long studentId, @PathVariable Long companyId) {
        return ResponseEntity.ok(studentService.applyForCompany(studentId, companyId));
    }

    @GetMapping("/applications/{studentId}")
    public ResponseEntity<List<ApplicationRow>> viewApplications(@PathVariable Long studentId) {
        List<ApplicationRow> rows = studentService.viewApplicationRows(studentId)
                .stream()
                .map(data -> new ApplicationRow(
                        data[0] != null ? data[0].toString() : "N/A",
                        data[1] != null ? data[1].toString() : "N/A",
                        data[2] != null ? data[2].toString() : "N/A"))
                .collect(Collectors.toList());
        return ResponseEntity.ok(rows);
    }

    @DeleteMapping("/applications/{studentId}/{companyId}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long studentId, @PathVariable Long companyId) {
        studentService.deleteApplication(studentId, companyId);
        return ResponseEntity.ok("Application deleted successfully.");
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudentAccount(studentId);
        return ResponseEntity.ok("Student account deleted successfully.");
    }

    @PostMapping("/certificate/add")
    public ResponseEntity<String> addCertificate(@RequestBody Certificate certificate) {
        Boolean added = studentService.addCertificate(certificate);
        if (Boolean.TRUE.equals(added)) {
            return ResponseEntity.ok("Certificate added successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to add certificate.");
    }

    @PutMapping("/certificate/update")
    public ResponseEntity<String> updateCertificate(@RequestBody Certificate certificate) {
        Boolean updated = studentService.updateCertificate(certificate);
        if (Boolean.TRUE.equals(updated)) {
            return ResponseEntity.ok("Certificate updated successfully.");
        }
        return ResponseEntity.badRequest().body("Certificate not found. Update failed.");
    }

    private record ApplicationRow(String companyName, String role, String status) {
    }
}

