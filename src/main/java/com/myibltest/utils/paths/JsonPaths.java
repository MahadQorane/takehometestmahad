package com.myibltest.utils.paths;

/**
 * Class to hold JSON path constants
 */
public class JsonPaths {
    // Root paths
    public static final String SCHEDULE = "schedule";
    public static final String SCHEDULE_CHANNEL = "schedule.channel";
    public static final String SCHEDULE_ELEMENTS = "schedule.elements";

    // Channel paths
    public static final String CHANNEL_ID = "schedule.channel.id";
    public static final String CHANNEL_TYPE = "schedule.channel.type";
    public static final String CHANNEL_TITLE = "schedule.channel.title";
    public static final String CHANNEL_HAS_SCHEDULE = "schedule.channel.has_schedule";
    public static final String CHANNEL_MASTER_BRAND_ID = "schedule.channel.master_brand_id";
    public static final String CHANNEL_MASTER_BRAND_TITLE = "schedule.channel.master_brand_title";

     // Element paths
    public static final String ELEMENT_ID = "schedule.elements[].id";
    public static final String ELEMENT_SCHEDULED_START = "schedule.elements[].scheduled_start";
    public static final String ELEMENT_SCHEDULED_END = "schedule.elements[].scheduled_end";
    public static final String ELEMENT_DURATION = "schedule.elements[].duration";
    public static final String ELEMENT_TRANSMISSION_START = "schedule.elements[].transmission_start";
    public static final String ELEMENT_TRANSMISSION_END = "schedule.elements[].transmission_end";
    public static final String ELEMENT_BLANKED = "schedule.elements[].blanked";
    public static final String ELEMENT_REPEAT = "schedule.elements[].repeat";
    public static final String ELEMENT_EPISODE_ID = "schedule.elements[].episode_id";
    public static final String ELEMENT_VERSION_ID = "schedule.elements[].version_id";
    public static final String ELEMENT_SERVICE_ID = "schedule.elements[].service_id";
    public static final String ELEMENT_CHANNEL_TITLE = "schedule.elements[].channel_title";
    public static final String ELEMENT_TYPE = "schedule.elements[].type";
    public static final String ELEMENT_EVENTS = "schedule.elements[].events";

     // Episode paths
    public static final String EPISODE = "schedule.elements[].episode";
    public static final String EPISODE_ID = "schedule.elements[].episode.id";
    public static final String EPISODE_LIVE = "schedule.elements[].episode.live";
    public static final String EPISODE_TYPE = "schedule.elements[].episode.type";
    public static final String EPISODE_TITLE = "schedule.elements[].episode.title";
    public static final String EPISODE_IMAGES = "schedule.elements[].episode.images";
    public static final String EPISODE_LABELS = "schedule.elements[].episode.labels";
    public static final String EPISODE_CATEGORY = "schedule.elements[].episode.labels.category";
    public static final String EPISODE_SIGNED = "schedule.elements[].episode.signed";
    public static final String EPISODE_STATUS = "schedule.elements[].episode.status";
    public static final String EPISODE_TLEO_ID = "schedule.elements[].episode.tleo_id";
    public static final String EPISODE_GUIDANCE = "schedule.elements[].episode.guidance";
    public static final String EPISODE_SUBTITLE = "schedule.elements[].episode.subtitle";
    public static final String EPISODE_SYNOPSES = "schedule.elements[].episode.synopses";
    public static final String EPISODE_SYNOPSIS_SMALL = "schedule.elements[].episode.synopses.small";
    public static final String EPISODE_VERSIONS = "schedule.elements[].episode.versions";
    public static final String EPISODE_CHILDRENS = "schedule.elements[].episode.childrens";
    public static final String EPISODE_PARENT_ID = "schedule.elements[].episode.parent_id";
    public static final String EPISODE_TLEO_TYPE = "schedule.elements[].episode.tleo_type";
    public static final String EPISODE_CATEGORIES = "schedule.elements[].episode.categories";
    public static final String EPISODE_HAS_CREDITS = "schedule.elements[].episode.has_credits";
    public static final String EPISODE_REQUIRES_AB = "schedule.elements[].episode.requires_ab";
    public static final String EPISODE_MASTER_BRAND = "schedule.elements[].episode.master_brand";
    public static final String EPISODE_RELEASE_DATE = "schedule.elements[].episode.release_date";
    public static final String EPISODE_RELEASE_DATE_TIME = "schedule.elements[].episode.release_date_time";

    // Version paths
    public static final String VERSION = "schedule.elements[].episode.versions[]";
    public static final String VERSION_HD = "schedule.elements[].episode.versions[].hd";
    public static final String VERSION_ID = "schedule.elements[].episode.versions[].id";
    public static final String VERSION_UHD = "schedule.elements[].episode.versions[].uhd";
    public static final String VERSION_KIND = "schedule.elements[].episode.versions[].kind";
    public static final String VERSION_TYPE = "schedule.elements[].episode.versions[].type";
    public static final String VERSION_EVENTS = "schedule.elements[].episode.versions[].events";
    public static final String VERSION_DOWNLOAD = "schedule.elements[].episode.versions[].download";
    public static final String VERSION_DURATION = "schedule.elements[].episode.versions[].duration";
    public static final String VERSION_DURATION_TEXT = "schedule.elements[].episode.versions[].duration.text";
    public static final String VERSION_DURATION_VALUE = "schedule.elements[].episode.versions[].duration.value";
    public static final String VERSION_AVAILABILITY = "schedule.elements[].episode.versions[].availability";
    public static final String VERSION_AVAILABILITY_START = "schedule.elements[].episode.versions[].availability.start";
    public static final String VERSION_AVAILABILITY_END = "schedule.elements[].episode.versions[].availability.end";
    public static final String VERSION_AVAILABILITY_REMAINING = "schedule.elements[].episode.versions[].availability.remaining";
    public static final String VERSION_FIRST_BROADCAST = "schedule.elements[].episode.versions[].first_broadcast";
    public static final String VERSION_FIRST_BROADCAST_DATE_TIME = "schedule.elements[].episode.versions[].first_broadcast_date_time";

    // Event paths - use with String.format(VERSION_EVENT_NAME, elementIndex, versionIndex, eventIndex)
    public static final String VERSION_EVENT = "schedule.elements[%d].episode.versions[%d].events[%d]";
    public static final String VERSION_EVENT_NAME = "schedule.elements[%d].episode.versions[%d].events[%d].name";
    public static final String VERSION_EVENT_OFFSET = "schedule.elements[%d].episode.versions[%d].events[%d].offset";
    public static final String VERSION_EVENT_SYSTEM = "schedule.elements[%d].episode.versions[%d].events[%d].system";

    /**
     * Utility method to format paths with element index
     * @param path The path template with %d placeholder
     * @param elementIndex The index of the element
     * @return The formatted path
     */
    public static String formatElementPath(String path, int elementIndex) {
        return String.format(path, elementIndex);
    }

    /**
     * Utility method to format paths with element and version indices
     * @param path The path template with %d placeholders
     * @param elementIndex The index of the element
     * @param versionIndex The index of the version
     * @return The formatted path
     */
    public static String formatVersionPath(String path, int elementIndex, int versionIndex) {
        return String.format(path, elementIndex, versionIndex);
    }

    /**
     * Utility method to format paths with element, version and event indices
     * @param path The path template with %d placeholders
     * @param elementIndex The index of the element
     * @param versionIndex The index of the version
     * @param eventIndex The index of the event
     * @return The formatted path
     */
    public static String formatEventPath(String path, int elementIndex, int versionIndex, int eventIndex) {
        return String.format(path, elementIndex, versionIndex, eventIndex);
    }
}
