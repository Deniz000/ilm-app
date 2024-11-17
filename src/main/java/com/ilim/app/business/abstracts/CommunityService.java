package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Community;

import java.util.List;

public interface CommunityService {
    Community createCommunity(Community community);
    Community getCommunityById(Long id);
    Community updateCommunity(Long id, Community communityDetails);
    void deleteCommunity(Long id);
    List<Community> getAllCommunities();
}
