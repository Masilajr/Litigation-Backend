package com.LDLS.Litigation.Project.Authentication.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class EntityResponse<T> {
    //This is a generic class(flexible and can be used all over code)
    //T represents a type parameter - the type of thing the entity response will be able to hold
    private String message;
    private T entity;
    private Integer statusCode;
}
