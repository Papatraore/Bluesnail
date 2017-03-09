package com.alma.factory;

/**
 * Classe representant une fabrique de plugins
 */
public class Factory implements IFactory {

    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(extension_name, true, loader).newInstance();
    }
}
