package com.ilim.app.dataAccess;

import com.ilim.app.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
