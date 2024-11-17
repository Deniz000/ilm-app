package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.PhoneCallService;
import com.ilim.app.dataAccess.PhoneCallRepository;
import com.ilim.app.entities.PhoneCall;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhoneCallServiceImpl implements PhoneCallService {

    private final PhoneCallRepository phoneCallRepository;

    public PhoneCallServiceImpl(PhoneCallRepository phoneCallRepository) {
        this.phoneCallRepository = phoneCallRepository;
    }

    @Override
    public PhoneCall createPhoneCall(PhoneCall phoneCall) {
        phoneCall.setCallTime(LocalDateTime.now());
        return phoneCallRepository.save(phoneCall);
    }

    @Override
    public PhoneCall getPhoneCallById(Long id) {
        return phoneCallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone call not found with ID: " + id));
    }

    @Override
    public List<PhoneCall> getCallsBetweenUsers(Long callerId, Long receiverId) {
        return phoneCallRepository.findByCallerIdAndReceiverId(callerId, receiverId);
    }

    @Override
    public void deletePhoneCall(Long id) {
        phoneCallRepository.deleteById(id);
    }
}
