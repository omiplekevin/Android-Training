package android.application.com.multipledownloaderlist;

/**
 * Created by OMIPLEKEVIN on Jan 27, 2016.
 */
public interface DownloadItemCallback {

    /**
     * filesize is being request from connection for future download progress usage
     * @param fileSize size of the sourceURL content
     */
    void onDownloadQueue(int fileSize);

    /**
     * progress download of the current connection stream
     * @param downloaded downloaded byte size from the stream
     * @param streamDownloadedSize byte buffer downloaded from stream, in this implementation, it is set to 4096
     */
    void onDownloadProgress(int downloaded, int streamDownloadedSize);

    /**
     * content is downloaded successfully from the connection stream
     */
    void onDownloadComplete();

    /**
     * stream is either interrupted, connection is disrupted or any Exception is thrown by API
     * @param errMessage error message
     */
    void onDownloadError(String errMessage);
}
