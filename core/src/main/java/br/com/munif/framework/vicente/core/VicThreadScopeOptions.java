package br.com.munif.framework.vicente.core;


public enum VicThreadScopeOptions implements IVicThreadScopeOptions {
    ENABLE_FORWARD_REQUEST_EXCEPTION {
        @Override
        public boolean getValue() {
            Boolean isEnableForwardRequestException = VicThreadScope.options.get().get(VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.name());
            return isEnableForwardRequestException != null && isEnableForwardRequestException;
        }

        @Override
        public void setValue(boolean value) {
            VicThreadScope.options.get().put(VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.name(), value);
        }
    };
}

interface IVicThreadScopeOptions {
    boolean getValue();

    void setValue(boolean value);
}