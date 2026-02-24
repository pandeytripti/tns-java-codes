package org.tnsif.accenture.c2tc.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.tnsif.accenture.c2tc.student.entity.Application;
import org.tnsif.accenture.c2tc.student.entity.Company;
import org.tnsif.accenture.c2tc.student.entity.Student;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a JOIN FETCH a.company WHERE a.student.id = :studentId")
    List<Application> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c.companyName, c.role, a.status FROM Application a JOIN a.company c WHERE a.student.id = :studentId")
    List<Object[]> findApplicationRowsByStudentId(@Param("studentId") Long studentId);

    Optional<Application> findByStudentAndCompany(Student student, Company company);

    Optional<Application> findByStudentIdAndCompanyId(Long studentId, Long companyId);

    void deleteByStudentId(Long studentId);
}

