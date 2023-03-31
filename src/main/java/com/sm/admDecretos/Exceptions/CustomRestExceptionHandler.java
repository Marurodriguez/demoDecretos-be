package com.sm.admDecretos.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        List<SubError> errores = new ArrayList<SubError>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            SubError subError = new SubError();
//            subError.setName(error.getField());
//            subError.setMessage(error.getDefaultMessage());
//            errores.add(subError);
//        }
//        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            SubError subError = new SubError();
//            subError.setName(error.getObjectName());
//            subError.setMessage(error.getDefaultMessage());
//            subError.setMessage(error.getDefaultMessage());
//            errores.add(subError);
//        }
//
//        CustomException customException =
//                new CustomException(HttpStatus.BAD_REQUEST,"Argumento Invalido", ex.getLocalizedMessage(), errores);
//        return handleExceptionInternal(
//                ex, customException, headers, customException.getStatus(), request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(
//            MissingServletRequestParameterException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//
//        CustomException customException =
//                new CustomException(HttpStatus.BAD_REQUEST, "Parametros incorrectos", ex.getLocalizedMessage());
//        return new ResponseEntity<Object>(
//                customException, new HttpHeaders(), customException.getStatus());
//    }
//
//    @ExceptionHandler({ ConstraintViolationException.class })
//    public ResponseEntity<Object> handleConstraintViolation(
//            ConstraintViolationException ex, WebRequest request) {
//        List<SubError> errores = new ArrayList<>();
//        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            SubError subError = new SubError();
//            subError.setName(violation.getRootBeanClass().getName());
//            subError.setValue(violation.getPropertyPath().toString());
//            subError.setMessage(violation.getMessage());
//            errores.add(subError);
//        }
//
//        CustomException customException =
//                new CustomException(HttpStatus.BAD_REQUEST,"Argumentos invalidos", ex.getLocalizedMessage(), errores);
//        return new ResponseEntity<Object>(
//                customException, new HttpHeaders(), customException.getStatus());
//    }
//
//    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
//    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
//            MethodArgumentTypeMismatchException ex, WebRequest request) {
//        String error =
//                ex.getName() + " debe ser del tipo " + ex.getRequiredType().getName();
//
//        CustomException customException =
//                new CustomException(HttpStatus.BAD_REQUEST, error, ex.getLocalizedMessage());
//        return new ResponseEntity<Object>(
//                customException, new HttpHeaders(), customException.getStatus());
//    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        CustomException customException = new CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno", ex.getLocalizedMessage());
        return new ResponseEntity<Object>(
                customException, new HttpHeaders(), customException.getStatus());
    }

//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(CustomException ex, WebRequest request) {
////        CustomException customException = new CustomException(
////                HttpStatus.INTERNAL_SERVER_ERROR, ex.ge, ex.getLocalizedMessage());
//        return new ResponseEntity<Object>(
//                ex, new HttpHeaders(), ex.getStatus());
//    }
}
