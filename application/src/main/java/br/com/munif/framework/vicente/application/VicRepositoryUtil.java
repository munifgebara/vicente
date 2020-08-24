package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.vquery.Param;
import br.com.munif.framework.vicente.core.vquery.ParamList;
import br.com.munif.framework.vicente.core.vquery.SetUpdateQuery;

import java.util.Map;

public class VicRepositoryUtil {

    public static final String DEFAULT_ALIAS = "obj";

    public static SetUpdateQuery getSetUpdate(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        ParamList params = new ParamList();
        getSetUpdate(map, stringBuilder, DEFAULT_ALIAS, params);
        return new SetUpdateQuery(stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString(), params);
    }

    private static void getSetUpdate(Map<String, Object> map, StringBuilder builder, String prefix, ParamList params) {
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    getSetUpdate((Map<String, Object>) entry.getValue(), builder, entry.getKey(), params);
                } else {
                    Param param = new Param(entry.getValue(), entry.getValue().getClass());
                    params.add(param);
                    builder.append(prefix + "." + entry.getKey() + " = " + param.getKey() + ",");
                }

            }
        }
    }
}
