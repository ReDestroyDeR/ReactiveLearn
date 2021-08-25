package ru.red.reactivelearn.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.red.reactivelearn.exception.BadRequestException;
import ru.red.reactivelearn.exception.ForbiddenException;
import ru.red.reactivelearn.exception.NotFoundException;

import javax.validation.constraints.NotNull;


/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerResponse<T> {
    private HttpStatus status;
    private T payload;
    private String message;
    private Throwable exception;

    // ------- Builder ------- //

    public ServerResponse<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ServerResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServerResponse<T> setException(Throwable exception) {
        this.exception = exception;
        return this;
    }

    // ------- OK Pipeline ------- //

    public static <T> ServerResponse<T> ok() {
        return ok(null);
    }

    public static <T> ServerResponse<T> ok(T payload) {
        return ok(payload, "");
    }

    public static <T> ServerResponse<T> ok(String message) {
        return ok(null, message);
    }

    public static <T> ServerResponse<T> ok(T payload, String message) {
        return createServerResponse(HttpStatus.OK, payload, message, null);
    }

    // ------- NOT_FOUND Pipeline ------- //

    public static <T> ServerResponse<T> notFound() {
        return notFound(null, "");
    }

    public static <T> ServerResponse<T> notFound(T payload) {
        return notFound(payload, "");
    }

    public static <T> ServerResponse<T> notFound(@NotNull Throwable exception) {
        return notFound(exception.getMessage(), exception);
    }

    public static <T> ServerResponse<T> notFound(T payload, String message) {
        return notFound(payload, message, new NotFoundException());
    }

    public static <T> ServerResponse<T> notFound(String message, Throwable exception) {
        return notFound(null, message, exception);
    }

    public static <T> ServerResponse<T> notFound(T payload, String message, Throwable exception) {
        return createServerResponse(HttpStatus.NOT_FOUND, payload, message, exception);
    }

    // ------- BAD_REQUEST Pipeline ------- //

    public static <T> ServerResponse<T> badRequest() {
        return badRequest(null, "");
    }

    public static <T> ServerResponse<T> badRequest(T payload) {
        return badRequest(payload, "");
    }

    public static <T> ServerResponse<T> badRequest(@NotNull Throwable exception) {
        return badRequest(exception.getMessage(), exception);
    }

    public static <T> ServerResponse<T> badRequest(T payload, String message) {
        return badRequest(payload, message, new BadRequestException());
    }

    public static <T> ServerResponse<T> badRequest(String message, Throwable exception) {
        return badRequest(null, message, exception);
    }

    public static <T> ServerResponse<T> badRequest(T payload, String message, Throwable exception) {
        return createServerResponse(HttpStatus.BAD_REQUEST, payload, message, exception);
    }

    // ------- FORBIDDEN Pipeline ------- //

    public static <T> ServerResponse<T> forbidden() {
        return forbidden(null, "");
    }

    public static <T> ServerResponse<T> forbidden(T payload) {
        return forbidden(payload, "");
    }

    public static <T> ServerResponse<T> forbidden(Throwable exception) {
        return forbidden(exception.getMessage(), exception);
    }

    public static <T> ServerResponse<T> forbidden(T payload, String message) {
        return forbidden(payload, message, new ForbiddenException());
    }

    public static <T> ServerResponse<T> forbidden(String message, Throwable exception) {
        return forbidden(null, message, exception);
    }

    public static <T> ServerResponse<T> forbidden(T payload, String message, Throwable exception) {
        return createServerResponse(HttpStatus.FORBIDDEN, payload, message, exception);
    }

    // ------- Factory Method ------- //

    public static <T> ServerResponse<T> createServerResponse(HttpStatus status, T payload,
                                                             String message, Throwable exception) {
        return new ServerResponse<>(status, payload, message, exception);
    }
}
