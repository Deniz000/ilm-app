package com.ilim.app.dataAccess;

import com.ilim.app.entities.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneCallRepository extends JpaRepository<PhoneCall, Long> {
}
