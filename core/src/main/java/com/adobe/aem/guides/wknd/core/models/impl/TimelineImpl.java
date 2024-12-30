package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.Timeline;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = { Timeline.class,
        ComponentExporter.class }, resourceType = TimelineImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class TimelineImpl implements Timeline, ComponentExporter {

    static final String RESOURCE_TYPE = "wknd/components/timeline";

    @SlingObject
    private Resource resource;

    @ChildResource
    private List<TimelineEntryImpl> entries;

    private List<TimelineEntry> processedEntries;

    @PostConstruct
    private void init() {
        processedEntries = new ArrayList<>();
        if (entries != null) {
            boolean isLeft = true;
            for (TimelineEntryImpl entry : entries) {
                entry.setLeftSide(isLeft);
                processedEntries.add(entry);
                isLeft = !isLeft; // Toggle for next entry
            }
        }
    }

    @Override
    public List<TimelineEntry> getEntries() {
        return processedEntries != null ? Collections.unmodifiableList(processedEntries) : Collections.emptyList();
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class TimelineEntryImpl implements TimelineEntry {

        @ChildResource(name = "date")
        private String date;

        @ChildResource(name = "title")
        private String title;

        @ChildResource(name = "image")
        private String image;

        @ChildResource(name = "sourceAttribution")
        private String sourceAttribution;

        private boolean leftSide;

        public void setLeftSide(boolean leftSide) {
            this.leftSide = leftSide;
        }

        @Override
        public String getDate() {
            return date;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getImage() {
            return image;
        }

        @Override
        public String getSourceAttribution() {
            return sourceAttribution;
        }

        @Override
        public boolean isLeftSide() {
            return leftSide;
        }
    }
}