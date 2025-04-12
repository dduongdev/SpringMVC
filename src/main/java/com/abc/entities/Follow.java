package com.abc.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @Column(name = "following_user_id")
    private int followingUserId;

    @Id
    @Column(name = "followed_user_id")
    private int followedUserId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Follow() {}
    
    // Constructor
    public Follow(int followingUserId, int followedUserId, LocalDateTime createdAt) {
        this.followingUserId = followingUserId;
        this.followedUserId = followedUserId;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(int followingUserId) {
        this.followingUserId = followingUserId;
    }

    public int getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(int followedUserId) {
        this.followedUserId = followedUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
