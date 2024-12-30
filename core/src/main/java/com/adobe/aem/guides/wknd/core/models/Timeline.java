package com.adobe.aem.guides.wknd.core.models;

import org.osgi.annotation.versioning.ConsumerType;

import java.util.List;

@ConsumerType
public interface Timeline {

    /**
     * @return List of timeline entries
     */
    List<TimelineEntry> getEntries();

    /**
     * Interface representing a single timeline entry
     */
    interface TimelineEntry {
        /**
         * @return the date of the timeline entry
         */
        String getDate();

        /**
         * @return the title of the timeline entry
         */
        String getTitle();

        /**
         * @return the image path for the timeline entry
         */
        String getImage();

        /**
         * @return the source attribution text (optional)
         */
        String getSourceAttribution();

        /**
         * @return true if this entry should appear on the left side
         */
        boolean isLeftSide();
    }
}