package com.api.slcontract.exception;

import com.api.slcontract.security.jwt.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

	// TODO: check how to update this to new version
//	@Bean
//	public ErrorAttributes errorAttributes() {
//		// Hide exception field in the return object
//		return new DefaultErrorAttributes() {
//			@Override
//			public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
//				Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
//				errorAttributes.remove("exception");
//				return errorAttributes;
//			}
//		};
//	}

	@ExceptionHandler(CustomException.class)
	public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
		res.sendError(ex.getHttpStatus().value(), ex.getMessage());
	}

	@Profile("prod")
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	}

	@Profile("prod") // TODO: Why is this not working?
	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res, Exception ex) throws IOException {
		ex.printStackTrace();
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}

//	@ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
//	public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
//		return status(UNAUTHORIZED).build();
//	}

	@ExceptionHandler(BadCredentialsException.class)
	public void badCredentials(BadCredentialsException ex, HttpServletResponse res) throws IOException {
		log.debug("handling InvalidJwtAuthenticationException...");
		res.sendError(UNAUTHORIZED.value(), ex.getMessage());
	}

}
