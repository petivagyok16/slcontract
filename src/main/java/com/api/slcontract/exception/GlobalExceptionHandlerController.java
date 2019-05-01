package com.api.slcontract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
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

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	}

	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}

}
