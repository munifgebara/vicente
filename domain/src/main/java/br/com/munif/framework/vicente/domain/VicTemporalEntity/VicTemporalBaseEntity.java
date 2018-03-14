package br.com.munif.framework.vicente.domain.VicTemporalEntity;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author munif
 */
@MappedSuperclass
public class VicTemporalBaseEntity extends BaseEntity {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

    protected Long startTime;

    protected Long endTime;

    public VicTemporalBaseEntity() {
        startTime = VicTemporalBaseEntityHelper.getEffectiveTime() - 5000;
        endTime = startTime + 5000 * 2;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public boolean valid() {
        Long ed = VicTemporalBaseEntityHelper.getEffectiveTime();
        return startTime <= ed && ed <= endTime;
    }

    @JsonGetter
    public String start() {
        return  ZonedDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE);
    }

    @JsonGetter
    public String end() {
        return  ZonedDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE);
        
    }

    @Override
    public String toString() {
        return "VicTemporalBaseEntity{ valid=" + valid() + " efectiveTime=" + VicTemporalBaseEntityHelper.getEffectiveTime() + " startTime=" + startTime + ", endTime=" + endTime + '}' + super.toString();
    }

}
