package net.wouterdanes.docker.maven;

/**
 * This class holds information about an image that was built so that it can be references in the start goal and
 * removed in the stop goal.
 */
public class BuiltImageInfo {

    private final String startId;
    private final String imageId;

    public BuiltImageInfo(final String startId, final String imageId) {
        this.startId = startId;
        this.imageId = imageId;
    }

    public String getStartId() {
        return startId;
    }

    public String getImageId() {
        return imageId;
    }
}
