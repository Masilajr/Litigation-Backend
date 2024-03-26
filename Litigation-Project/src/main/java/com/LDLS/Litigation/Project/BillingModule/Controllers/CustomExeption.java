package com.LDLS.Litigation.Project.BillingModule.Controllers;

public class CustomExeption extends Throwable {
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String invoiceNotFound) {
        }
    }
}
