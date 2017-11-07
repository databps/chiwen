package com.databps.bigdaf.admin.security.kerberos.execption;

public class InvalidCredentialValueException extends Exception {

	  public InvalidCredentialValueException(String msg) {
	    super(msg);
	  }

	  public InvalidCredentialValueException(String msg, Throwable throwable) {
	    super(msg, throwable);
	  }
	}

