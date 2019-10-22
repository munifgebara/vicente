package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicThreadScope;
import org.hibernate.envers.RevisionListener;

/**
 * @author munif
 */
public class VicRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        VicRevisionEntity v = (VicRevisionEntity) o;
        v.setIp("" + VicThreadScope.ip.get());
        v.setUser("" + VicThreadScope.ui.get());
    }


}
