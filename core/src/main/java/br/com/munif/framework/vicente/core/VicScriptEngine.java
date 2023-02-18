package br.com.munif.framework.vicente.core;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;
import java.util.Map;

/**
 * Interpretador Javascript para backend
 *
 * @author munif
 */
public class VicScriptEngine {

    private static final ScriptEngineManager engineManager = new ScriptEngineManager();

    /**
     * Avalia o valor do script com o contexto de objetos java passados no map e
     * retora um objeto.
     *
     * @param script  script a ser executado
     * @param objects objetos java a serem inseridos no contexto javascript
     * @return valor resultante
     */
    public static Object eval(String script, Map<String, Object> objects) {
        try {
            ScriptEngine engine = engineManager.getEngineByName("JavaScript");
            if (objects != null) {
                for (String key : objects.keySet()) {
                    engine.put(key, objects.get(key));
                }
            }
            return engine.eval(script);
        } catch (ScriptException ex) {
            System.out.println("Problemas ao executar javascript no backend." + ex);
        }
        return null;
    }

    /**
     * Avalia o valor do script com o contexto de objetos java passados no map e
     * retora uma data.
     *
     * @param script  script a ser executado
     * @param objects objetos java a serem inseridos no contexto javascript
     * @return valor resultante
     */
    public static Date evalForDate(String script, Map<String, Object> objects) {
        if (script == null || script.trim().isEmpty()) {
            return null;
        }
        String scriptForDate = "(" + script + ").getTime()";
        long mili = (long) (double) eval(scriptForDate, objects);
        return new Date(mili);
    }

}
