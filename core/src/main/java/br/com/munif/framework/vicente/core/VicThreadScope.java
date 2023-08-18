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
    public static final ThreadLocal<String> cg = new InheritableThreadLocal<>();
    /**
     * Current user token
     */
    public static final ThreadLocal<String> token = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> origin = new InheritableThreadLocal<>();

    /**
     * Group identifier, the current groups for operations.
     */
    public static final ThreadLocal<String> gi = new InheritableThreadLocal<>();

    /**
     * User identifier, the current user for operations.
     */
    public static final ThreadLocal<String> ui = new InheritableThreadLocal<>();

    /**
     * Organization identifier, the current organization for operations, must end with dot (.) .
     */
    public static final ThreadLocal<String> oi = new InheritableThreadLocal<>();
    /**
     * The ip that make the request.
     */
    public static final ThreadLocal<String> ip = new InheritableThreadLocal<>();
    /**
     * The key of the operation.
     */
    public static final ThreadLocal<String> key = new InheritableThreadLocal<>();

    /**
     * The time for operations. The system time will be ignored if this is present.
     */
    public static final ThreadLocal<Long> effectiveTime = new InheritableThreadLocal<>();

    /**
     * The default rights values for new records.
     */
    public static final ThreadLocal<Integer> defaultRights = new InheritableThreadLocal<>();

    /**
     * If set, time constrains will be ignored in querys
     */
    public static final ThreadLocal<Boolean> ignoreTime = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> language = InheritableThreadLocal.withInitial(() -> "pt-BR");
    public static final ThreadLocal<String> timezone = InheritableThreadLocal.withInitial(() -> "America/Sao_Paulo");
    /**
     * 8
     * Map of thread options
     */
    public static final ThreadLocal<Map<String, Boolean>> options = InheritableThreadLocal.withInitial(HashMap::new);

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
}
