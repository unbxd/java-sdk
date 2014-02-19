package com.unbxd.library;

public class UnbxdException extends Exception {
	
	public static String NORESPONSE = "Couldn't connect to Unbxd";
	public static String NOSITENAME = "Site name cannot be null";
	public static String NOAPIKEY = "Api key cannot be null";
	public int code;
	
	public static String getNORESPONSE() {
		return NORESPONSE;
	}

	public static void setNORESPONSE(String nORESPONSE) {
		NORESPONSE = nORESPONSE;
	}

	public static String getNOSITENAME() {
		return NOSITENAME;
	}

	public static void setNOSITENAME(String nOSITENAME) {
		NOSITENAME = nOSITENAME;
	}

	public static String getNOAPIKEY() {
		return NOAPIKEY;
	}

	public static void setNOAPIKEY(String nOAPIKEY) {
		NOAPIKEY = nOAPIKEY;
	}
	UnbxdException()
	{
		//log(NORESPONSE);	
	}
	
	UnbxdException(String errorMessage)
	{
		super(errorMessage);
		
		
	}
	UnbxdException(String errorMessage,int errorCode)
	{
		super(errorMessage);
		this.code = errorCode;
		//log error
		
		
		
	}
}