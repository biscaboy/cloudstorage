package com.udacity.jwdnd.course1.cloudstorage.model;

/**
 * Simple interface for Model objects used to
 * make error and success messages easier to manage
 * See the ValidationService and ResponsePackingService.
 */
public interface CloudStorageModel {

    /**
     * The name of the implementing class for messaging.
     * @return logical functioning name of the class eg. file, credential, note, etc.
     */
    String printName();

    /**
     * @return the id of the owner of this object.
     */
    Integer getUserId();

}
