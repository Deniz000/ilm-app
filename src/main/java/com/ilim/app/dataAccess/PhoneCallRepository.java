package com.ilim.app.dataAccess;

import com.ilim.app.entities.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneCallRepository extends JpaRepository<PhoneCall, Long> {
    List<PhoneCall>  findByCallerIdAndReceiverId(Long callerId, Long receiverId);
}
