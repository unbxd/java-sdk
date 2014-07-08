package com.unbxd.client.feed.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedUploadException extends Exception {

    public FeedUploadException(Throwable t){
        super(t);
    }

    public FeedUploadException(String message){
        super(message);
    }
}
