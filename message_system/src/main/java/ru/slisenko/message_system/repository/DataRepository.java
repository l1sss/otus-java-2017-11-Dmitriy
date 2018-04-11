package ru.slisenko.message_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slisenko.message_system.model.DomainObject;

@Repository
public interface DataRepository extends JpaRepository<DomainObject, Long> {

}
