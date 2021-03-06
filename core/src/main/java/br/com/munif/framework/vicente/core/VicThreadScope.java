package br.com.munif.framework.vicente.core;

/**
 *
 * @author munif
 */
public class VicThreadScope {
    
    /**
     * Currente group, if set new records will be marked with this, if not, the first gi will be used
     */
    public static final ThreadLocal<String> cg = new ThreadLocal<>();
    /**
     * Current user token
     */
    public static final ThreadLocal<String> token = new ThreadLocal<>();

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
}
