package com.ilim.app.business.abstracts;

import com.ilim.app.entities.PhoneCall;

import java.util.List;

public interface PhoneCallService {
    PhoneCall createPhoneCall(PhoneCall phoneCall);
    PhoneCall getPhoneCallById(Long id);
    List<PhoneCall> getCallsBetweenUsers(Long callerId, Long receiverId);
    void deletePhoneCall(Long id);
}
