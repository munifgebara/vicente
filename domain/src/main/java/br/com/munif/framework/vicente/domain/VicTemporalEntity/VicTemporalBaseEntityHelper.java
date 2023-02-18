package br.com.munif.framework.vicente.domain.VicTemporalEntity;

import br.com.munif.framework.vicente.core.VicThreadScope;

public class VicTemporalBaseEntityHelper {

    public static Long getEffectiveTime() {
        Long tr = VicThreadScope.effectiveTime.get();
        if (tr == null) {
            return System.currentTimeMillis();
        }
        return tr;
    }

}
