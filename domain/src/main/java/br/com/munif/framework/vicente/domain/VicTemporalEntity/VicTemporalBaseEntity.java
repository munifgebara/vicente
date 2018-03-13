package br.com.munif.framework.vicente.domain.VicTemporalEntity;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author munif
 */
@MappedSuperclass
public class VicTemporalBaseEntity extends BaseEntity {

    @JsonIgnore
    protected Long startTime;
    @JsonIgnore
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
        return startTime<=ed && ed <= endTime;
    }

    @Override
    public String toString() {
        return "VicTemporalBaseEntity{ valid=" + valid() + " efectiveTime=" + VicTemporalBaseEntityHelper.getEffectiveTime() + " startTime=" + startTime + ", endTime=" + endTime + '}' + super.toString();
    }

}
