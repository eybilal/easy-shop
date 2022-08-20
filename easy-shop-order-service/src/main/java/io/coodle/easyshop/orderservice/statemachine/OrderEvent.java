package io.coodle.easyshop.orderservice.statemachine;

public enum OrderEvent {
    VALIDATE, VALIDATION_PASSED, VALIDATION_FAILED,
    ALLOCATE, ALLOCATION_SUCCESS, ALLOCATION_FAILED, ALLOCATION_NO_INVENTORY,
    PICK_UP, CANCEL
}
