package com.ilim.app.business.concretes;

import com.ilim.app.business.abstracts.CommunityService;
import com.ilim.app.dataAccess.CommunityRepository;
import com.ilim.app.entities.Community;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Community not found with ID: " + id));
    }

    @Override
    public Community updateCommunity(Long id, Community communityDetails) {
        Community community = getCommunityById(id);
        community.setName(communityDetails.getName());
        community.setDescription(communityDetails.getDescription());
        community.setUpdatedAt(LocalDateTime.now());
        return communityRepository.save(community);
    }

    @Override
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }
}
