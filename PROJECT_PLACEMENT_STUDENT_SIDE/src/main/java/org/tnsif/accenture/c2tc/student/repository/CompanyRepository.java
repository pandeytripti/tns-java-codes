package org.tnsif.accenture.c2tc.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tnsif.accenture.c2tc.student.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByEligibilityCgpaLessThanEqual(Double cgpa);

    boolean existsByCompanyNameAndRole(String companyName, String role);
}

