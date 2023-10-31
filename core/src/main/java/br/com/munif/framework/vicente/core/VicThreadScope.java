package br.com.munif.framework.vicente.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author munif
 */
public class VicThreadScope {

    /**
     * Current group, if set new records will be marked with this, if not, the first gi will be used
     */
    public static final ThreadLocal<String> cg = new ThreadLocal<>();
    /**
     * Current user token
     */
    public static final ThreadLocal<String> token = new ThreadLocal<>();
    public static final ThreadLocal<String> origin = new ThreadLocal<>();

    /**
     * Group identifier, the current groups for operations.
     */
    public static final ThreadLocal<String> gi = new ThreadLocal<>();

    /**
     * User identifier, the current user for operations.
     */
    public static final ThreadLocal<String> ui = new ThreadLocal<>();

    /**
     * Organization identifier, the current organization for operations, must end with dot (.) .
     */
    public static final ThreadLocal<String> oi = new ThreadLocal<>();
    /**
     * The ip that make the request.
     */
    public static final ThreadLocal<String> ip = new ThreadLocal<>();
    /**
     * The key of the operation.
     */
    public static final ThreadLocal<String> key = new ThreadLocal<>();

    /**
     * The time for operations. The system time will be ignored if this is present.
     */
    public static final ThreadLocal<Long> effectiveTime = new ThreadLocal<>();

    /**
     * The default rights values for new records.
     */
    public static final ThreadLocal<Integer> defaultRights = new ThreadLocal<>();

    /**
     * If set, time constrains will be ignored in querys
     */
    public static final ThreadLocal<Boolean> ignoreTime = new ThreadLocal<>();
    public static final ThreadLocal<String> language = ThreadLocal.withInitial(() -> "pt-BR");
    public static final ThreadLocal<String> timezone = ThreadLocal.withInitial(() -> "America/Sao_Paulo");
    /**
     * 8
     * Map of thread options
     */
    public static final ThreadLocal<Map<String, Boolean>> options = ThreadLocal.withInitial(HashMap::new);

    public static boolean isOnJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public static String getTopOi() {
        String s = VicThreadScope.oi.get();
        if (s == null) s = "";
        return s.split("\\.")[0] + ".";
    }

    public class Values {
        public String cg;
        public String token;
        public String origin;
        public String gi;
        public String ui;
        public String oi;
        public String ip;
        public String key;

        public Long effectiveTime;
        public Integer defaultRights;
        public Boolean ignoreTime;
        public String language;
        public String timezone;
        public Map<String, Boolean> options;

        public Values() {
            cg = VicThreadScope.cg.get();
            token = VicThreadScope.token.get();
            origin = VicThreadScope.origin.get();
            gi = VicThreadScope.gi.get();
            ui = VicThreadScope.ui.get();
            oi = VicThreadScope.oi.get();
            ip = VicThreadScope.ip.get();
            key = VicThreadScope.key.get();

            effectiveTime = VicThreadScope.effectiveTime.get();
            defaultRights = VicThreadScope.defaultRights.get();
            ignoreTime = VicThreadScope.ignoreTime.get();
            language = VicThreadScope.language.get();
            timezone = VicThreadScope.timezone.get();
            options = VicThreadScope.options.get();
        }


        public Values(String cg, String token, String origin, String gi, String ui, String oi, String ip, String key,
                      Long effectiveTime, Integer defaultRights, Boolean ignoreTime, String language,
                      String timezone, Map<String, Boolean> options) {
            this.cg = cg;
            this.token = token;
            this.origin = origin;
            this.gi = gi;
            this.ui = ui;
            this.oi = oi;
            this.ip = ip;
            this.key = key;
            this.effectiveTime = effectiveTime;
            this.defaultRights = defaultRights;
            this.ignoreTime = ignoreTime;
            this.language = language;
            this.timezone = timezone;
            this.options = options;
        }
    }
}
