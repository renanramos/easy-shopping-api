/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 29/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.exceptionhandler;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.error.ApiError;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.error.builder.ApiErrorBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "br.com.renanrramos.easyshopping")
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withErrorTitle(error)
                .withMessage(ex.getMessage())
                .withErrors(new ArrayList<>())
                .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(status)
                .withErrorTitle(status.getReasonPhrase())
                .withMessage(ex.getMessage())
                .withErrors(new ArrayList<>())
                .build());
    }

    @ExceptionHandler({TransactionSystemException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final Exception ex, final WebRequest request) {
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        final List<String> errors = new ArrayList<>();
        if (cause instanceof ConstraintViolationException consEx) {
            for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
                errors.add(violation.getMessage());
            }
            return buildResponseEntity(new ApiErrorBuilder()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withErrorTitle(ExceptionConstantMessages.INVALID_FIELDS_TITLE)
                    .withMessage(ExceptionConstantMessages.INVALID_FIELDS_TITLE)
                    .withErrors(errors)
                    .build());
        }
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withErrorTitle(ExceptionConstantMessages.ERRORS_FOUND)
                .withMessage(ex.getLocalizedMessage())
                .withErrors(new ArrayList<>())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final EntityNotFoundException ex) {
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .withErrorTitle(ExceptionConstantMessages.NOT_FOUND_TITLE)
                .withErrors(new ArrayList<>())
                .build());
    }

    @ExceptionHandler(EasyShoppingException.class)
    protected ResponseEntity<Object> handleEmailUnavailable(final EasyShoppingException ex) {
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(HttpStatus.CONFLICT)
                .withErrorTitle(ExceptionConstantMessages.INVALID_FIELDS_TITLE)
                .withMessage(ex.getMessage())
                .withErrors(new ArrayList<>())
                .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException ex) {
        return buildResponseEntity(new ApiErrorBuilder()
                .withStatus(HttpStatus.UNAUTHORIZED)
                .withErrorTitle(ExceptionConstantMessages.INVALID_CREDENTIALS)
                .withMessage(ex.getMessage())
                .withErrors(new ArrayList<>())
                .build());
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
