package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Membership;

import java.util.List;

public interface MembershipService {
    Membership createMembership(Membership membership);
    Membership getMembershipById(Long id);
    Membership updateMembership(Long id, Membership membershipDetails);
    void deleteMembership(Long id);
    List<Membership> getAllMemberships();
}
