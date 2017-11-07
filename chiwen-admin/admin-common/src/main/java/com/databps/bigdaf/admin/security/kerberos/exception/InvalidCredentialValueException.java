package com.databps.bigdaf.admin.security.kerberos.exception;

public class InvalidCredentialValueException extends Exception {

	  public InvalidCredentialValueException(String msg) {
	    super(msg);
	  }

	  public InvalidCredentialValueException(String msg, Throwable throwable) {
	    super(msg, throwable);
	  }
	}

