package com.destiny.common.util;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * LocalDateTime toolkit
 * @Author Destiny
 * @Version 1.0.0
 */
public class LocalDateTimeUtil {
    /**
     * The ISO date-time formatter that formats or parses a date-time without
     * an offset, such as '2011-12-03 10:15:30'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset date-time format.
     * The format consists of:
     * <ul>
     * <li>The {@link DateTimeFormatter#ISO_LOCAL_DATE}
     * <li>The space ' '.
     * <li>The {@link DateTimeFormatter#ISO_LOCAL_TIME}
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link java.time.format.ResolverStyle#STRICT STRICT} resolver style.
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    static {
        ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .toFormatter(Locale.CHINA);
    }

    /**
     * Serializer of LocalDateTime with 'ISO_LOCAL_DATE_TIME' DateTimeFormatter
     * @return
     */
    public static LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME);
    }
    /**
     * Deserializer of LocalDateTime with 'ISO_LOCAL_DATE_TIME' DateTimeFormatter
     * @return
     */
    public static LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(ISO_LOCAL_DATE_TIME);
    }
}
