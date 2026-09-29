package com.example.balancrapi.model;

/**
 * Discriminates what an {@link ActivityGoal} represents. Unlike
 * {@link FixedEvent#getType()} (free text), this is a controlled enum because
 * the type decides which other fields are relevant (e.g. intensity only
 * applies to TRAINING).
 */
public enum ActivityType {
    TRAINING,
    STUDY
}
