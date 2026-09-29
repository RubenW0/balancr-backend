package com.example.balancrapi.model;

/**
 * Training intensity for an {@link ActivityGoal} of type TRAINING. Left null
 * for STUDY goals, where intensity has no meaning.
 */
public enum Intensity {
    LIGHT,
    MEDIUM,
    HEAVY
}
