package com.bilgeadam.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * Bu sınıf uygulama içind oluşacak tüm istisnaların yakalanması için kullanılacaktır.
 * burada bu sınıfın bizim belirlediğimiz özelleştirilmiş istisnaları yakalayacağız.
 * Bunun dışında ek kullandığımız kütüphanelerin istisnalarını da ayrıca belirleyip yakalayacağız.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    public ErrorMessage createErrorMessage(ErrorType errorType,Exception exception){
        System.out.println("Bu kısımda hata mesajlarının loglama işlemlerini yapmalıyız.");
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.message)
                .build();
    }
    /**
     * Exception Handlerda
     * @ExceptionHandler parametre olarak verilen exception'ı yakalar ve sonrasında
     * ilgili metotu çalıştırır. Hatalar kontroller sonrası throw new denilerek ortamda fırlatılır.
     *
     *  return  new ResponseEntity(hata alınınca hangi metottan hata geçirilecek,kullanıcıya dönecek http kodu);
     */
     @ExceptionHandler(MovieAppException.class)
     @ResponseBody
     public ResponseEntity<String> handlerJava7MonoException(MovieAppException exception){
         System.out.println("Java7MonoException hatası...: " + exception.toString());
         return  new ResponseEntity(createErrorMessage(exception.getErrorType(),exception),exception.getErrorType().getHttpStatus());
     }

     @ExceptionHandler(ArithmeticException.class)
     @ResponseBody
     public ResponseEntity<String> handleArithmeticException(ArithmeticException exception){
          /**
           * İlgili arithmeti exception oluştuğunda, nesnesini yakalar ve nesnesini parametre olarak kullanır.
           */
          System.out.println("Aritmetik hatası...." + exception.toString());
          return ResponseEntity.ok("Sıfıra bölme işlem hatası");
    }
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handle(MissingPathVariableException exception){
         ErrorType errorType = ErrorType.BAD_REQUEST;
         return new ResponseEntity<>(createErrorMessage(errorType,exception), errorType.getHttpStatus());
    }
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<String> handleException(Exception exception){
//        return ResponseEntity.badRequest().body("Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.");
//    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception exception){
         ErrorType errorType = ErrorType.ERROR;
         return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {

        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(errorType, exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }

}
