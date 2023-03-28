package com.hossein.PermissionTree.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationExceptionDetail> handleApplicationException(Exception e) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		return new ResponseEntity<>(new ApplicationExceptionDetail(status, e.getMessage()), status);
	}
	
}
