package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.MembershipService;
import com.ilim.app.dataAccess.MembershipRepository;
import com.ilim.app.entities.Membership;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership createMembership(Membership Membership) {
        return membershipRepository.save(Membership);
    }

    @Override
    public Membership getMembershipById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membership not found with ID: " + id));
    }

    @Override
    public Membership updateMembership(Long id, Membership MembershipDetails) {
        Membership Membership = getMembershipById(id);
        Membership.setName(MembershipDetails.getName());
        return membershipRepository.save(Membership);
    }

    @Override
    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }

    @Override
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }
}
