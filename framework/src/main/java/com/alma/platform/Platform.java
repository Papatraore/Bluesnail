package com.alma.platform;


import com.alma.platform.MissingPropertyParser;
import com.alma.factory.*;
import com.alma.monitor.*;
import com.alma.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Classe singleton representant une plateforme de plugins
 */
public class Platform {

    private static Platform instance;
    private Properties config;
    private Monitor monitor;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private IFactory extensionFactory;

    private Platform() throws MalformedURLException {
        extensionFactory = new Factory();
        monitor = Monitor.getInstance();
        Parser parser = new Parser();

        try {
            plugins = parser.parseFile("src/main/resources/extensions.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on enregistre les plugins aupre du monitor
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            monitor.registerPlugin(plugin.getValue());
        }

        // on charge la config de la plateforme
        try {
            config = parser.loadConfig("src/main/resources/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // generation des urls du class loader
        int i = 0;
        File current_directory = new File(System.getProperty("user.dir"));
        String classpath_prefix = current_directory.getParentFile().toString() + "/";

        URL[] urls = new URL[plugins.size()];
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            urls[i] = new URL("file://" + classpath_prefix + plugin.getValue().getDirectory());
            i++;
        }
        classLoader = URLClassLoader.newInstance(urls);


        extensionFactory = new Factory();

    }

    /**
     * Methode qui retourne l'instance de la plateforme
     * @return
     * @throws MalformedURLException
     * @throws PropertyNotFoundException
     */
    public static Platform getInstance() throws MalformedURLException, MissingPropertyParser {
        if(instance == null) {
            synchronized (Platform.class) {
                if(instance == null) {
                    instance = new Platform();
                }
            }
        }
        return instance;
    }

    /**
     * Methode qui charge et retourne une extension precise
     * @param extension_name Le nom de l'extension que l'on veut recuperer
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object getExtension(String extension_name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSavedInstanceException {
        Plugin plugin = plugins.get(extension_name);
        Object objet = extensionFactory.get(plugin.getClassName(), classLoader);
        monitor.reportNewInstance(extension_name, plugin.getClassName() + "#" + System.identityHashCode(objet));
        return objet;
    }

    /**
     * Methode qui retourne une liste des noms des extensions a  lancer au demarrage de l'application
     * @return
     */
    public List<String> getAutorunExtensions() {
        List<String> results = new ArrayList<>();
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            if(plugin.getValue().hasOption("autorun")) {
                if(plugin.getValue().getOption("autorun").equals("true")) {
                    results.add(plugin.getKey());
                }
            }
        }
        return results;
    }

    /**
     * Methode qui retourne une liste des noms des extensions implementant une interface donnee
     * @param interface_name
     * @return
     */
    public List<String> getByInterface(String interface_name) {
        List<String> results = new ArrayList<>();
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            if(plugin.getValue().getInterface().equals(interface_name)) {
                results.add(plugin.getKey());
            }
        }
        return results;
    }

    public static void main(String[] args) {
        try {
            // on instancie les extensions qui sont en mode autorun
            for(String plugin_name: Platform.getInstance().getAutorunExtensions()) {
                try {
                    Platform.getInstance().getExtension(plugin_name);
                } catch (Exception e) {
                    Monitor.getInstance().addLog(new Log(LogLevel.CRITICAL, e.getClass().getName(), e.toString()));
                }
            }
        } catch (MalformedURLException | MissingPropertyParser e) {
            e.printStackTrace();
        }
    }
}
