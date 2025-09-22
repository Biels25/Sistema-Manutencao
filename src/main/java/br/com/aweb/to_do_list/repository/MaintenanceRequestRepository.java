package br.com.aweb.to_do_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.aweb.to_do_list.model.MaintenanceRequest;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    
}