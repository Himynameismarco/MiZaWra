package com.project.mizawra.dao;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, UUID> {
    List<Journal> findAllByOwner(Client owner, Pageable pageable);
    long countByOwner(Client owner);
}
