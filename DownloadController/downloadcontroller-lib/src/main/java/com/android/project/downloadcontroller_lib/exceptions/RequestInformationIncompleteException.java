package com.android.project.downloadcontroller_lib.exceptions;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   July 09, 2017<br/>
 * IN CLASS:        com.android.project.downloadcontroller_lib.exceptions<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class RequestInformationIncompleteException extends Exception {
    public RequestInformationIncompleteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "DownloadRequest information incomplete - please create DownloadRequest Class instance and assign proper values";
    }
}
