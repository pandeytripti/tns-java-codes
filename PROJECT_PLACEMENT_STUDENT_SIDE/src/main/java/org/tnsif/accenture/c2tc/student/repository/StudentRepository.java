package org.tnsif.accenture.c2tc.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tnsif.accenture.c2tc.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByUsn(String usn);

    boolean existsByEmail(String email);

    boolean existsByHallTicketNumber(String hallTicketNumber);

    Optional<Student> findByEmailAndPassword(String email, String password);
}

