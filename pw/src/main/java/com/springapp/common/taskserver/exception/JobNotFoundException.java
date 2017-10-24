package com.springapp.common.taskserver.exception;


public class JobNotFoundException extends ClassNotFoundException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>JobNotFoundException</code> with no detail message.
     */
    public JobNotFoundException() {
        super();
    }

    /**
     * Constructs a <code>JobNotFoundException</code> with the specified detail message.
     * 
     * @param s
     *            the detail message.
     */
    public JobNotFoundException(String s) {
        super(s, null);
    }

    /**
     * Constructs a <code>JobNotFoundException</code> with the specified detail message and optional
     * exception that was raised while loading the class.
     * 
     * @param s
     *            the detail message
     * @param ex
     *            the exception that was raised while loading the class
     * @since 1.2
     */
    public JobNotFoundException(String s, Throwable ex) {
        super(s, ex); // Disallow initCause
    }
}
