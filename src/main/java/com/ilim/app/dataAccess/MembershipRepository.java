package com.ilim.app.dataAccess;

import com.ilim.app.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
