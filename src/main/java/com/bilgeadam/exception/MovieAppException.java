package com.bilgeadam.exception;

import lombok.Getter;

/**
 * Kendi özelleştirilmiş exception'ınımız
 * Hatalarımızın tümü runtime'da oluşacağından burdan miras almak daha doğru
 */
@Getter
public class MovieAppException extends RuntimeException{
    private final ErrorType errorType;
    public MovieAppException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }
    public MovieAppException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
