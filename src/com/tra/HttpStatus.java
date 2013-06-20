package com.tra;

public class HttpStatus {
	
	    private int code;

	    private String statusLine;

	    private String statusText;

	    private String response;

	    public HttpStatus() {
	    }

	    /**
	     * @return Returns the code.
	     */
	    public int getCode() {
	        return this.code;
	    }

	    /**
	     * @param code
	     *            The code to set.
	     */
	    public void setCode(int code) {
	        this.code = code;
	    }

	    /**
	     * @return Returns the response.
	     */
	    public String getResponse() {
	        return this.response;
	    }

	    /**
	     * @param response
	     *            The response to set.
	     */
	    public void setResponse(String response) {
	        this.response = response;
	    }

	    /**
	     * @return Returns the statusLine.
	     */
	    public String getStatusLine() {
	        return this.statusLine;
	    }

	    /**
	     * @param statusLine
	     *            The statusLine to set.
	     */
	    public void setStatusLine(String statusLine) {
	        this.statusLine = statusLine;
	    }

	    /**
	     * @return Returns the statusText.
	     */
	    public String getStatusText() {
	        return this.statusText;
	    }

	    /**
	     * @param statusText
	     *            The statusText to set.
	     */
	    public void setStatusText(String statusText) {
	        this.statusText = statusText;
	    }

	    public String toString() {
	        return "Status Code: " + code + ", Status Line: " + statusLine
	                + ", Status Text: " + statusText + ", Response: " + response;
	    }
	}


