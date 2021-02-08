package com.james.points.services;

public class PointsServiceException extends RuntimeException {
    public PointsServiceException(String errorMessage) {
        super(errorMessage);
    }
}
