package org.tnsif.accenture.c2tc.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tnsif.accenture.c2tc.student.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}

