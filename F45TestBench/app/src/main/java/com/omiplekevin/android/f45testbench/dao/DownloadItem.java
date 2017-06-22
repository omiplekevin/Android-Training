package com.omiplekevin.android.f45testbench.dao;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 09, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.dao<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadItem {
    public String source;
    public String filename;
    public String fileExtension;
    public String savePath;
    public boolean highpriority;
    public boolean overwrite;
    public boolean requiresView;


    public DownloadItem(String source, boolean highPriority, boolean overwrite) {
        this.source = source;
        this.highpriority = highPriority;
        this.overwrite = overwrite;
    }

    @Override
    public String toString() {
        return "SOURCE: " + source
                + "\nFILENAME: " + filename
                + "\nFILEEXTENSION: " + fileExtension
                + "\nSAVEPATH: " + savePath
                + "\nHIGHPRIORITY: " + highpriority
                + "\nOVERWRITE: " + overwrite;
    }
}