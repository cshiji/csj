package com.cshiji.superviki.base.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 文件说明: 基于j2se的读取properties文件类
 * 
 */
public class JProperties {

    public final static int BY_PROPERTIES = 1;

    public final static int BY_RESOURCEBUNDLE = 2;

    public final static int BY_PROPERTYRESOURCEBUNDLE = 3;

    public final static int BY_CLASS = 4;

    public final static int BY_CLASSLOADER = 5;

    public final static int BY_SYSTEM_CLASSLOADER = 6;

    /**
     * 加载properties文件,返回Properties对象
     * @param name
     * 文件路径
     * @param type
     * 读取文件类型
     * @return
     * @throws IOException
     */
    public final static Properties loadProperties(final String name, final int type)
            throws IOException {
        Properties p = new Properties();
        InputStream in = null;
        if (type == BY_PROPERTIES) {
            in = new BufferedInputStream(new FileInputStream(name));
            if (in==null) {
				throw new FileNotFoundException(name+" not found");
			}
            p.load(in);
        } else if (type == BY_RESOURCEBUNDLE) {
            ResourceBundle rb = ResourceBundle.getBundle(name, Locale.getDefault());

            p = new ResourceBundleAdapter(rb);
        } else if (type == BY_PROPERTYRESOURCEBUNDLE) {
            in = new BufferedInputStream(new FileInputStream(name));
            if (in==null) {
            	throw new FileNotFoundException(name+" not found");
			}
            ResourceBundle rb = new PropertyResourceBundle(in);
            p = new ResourceBundleAdapter(rb);
        } else if (type == BY_CLASS) {

            in = JProperties.class.getResourceAsStream(name);
            if (in==null) {
            	throw new FileNotFoundException(name+" not found");
			}
            p.load(in);
            // return new JProperties().getClass().getResourceAsStream(name);
        } else if (type == BY_CLASSLOADER) {
            in = JProperties.class.getClassLoader().getResourceAsStream(name);
            if (in==null) {
            	throw new FileNotFoundException(name+" not found");
			}
            p.load(in);
            // return new
            // JProperties().getClass().getClassLoader().getResourceAsStream(name);
        } else if (type == BY_SYSTEM_CLASSLOADER) {
            in = ClassLoader.getSystemResourceAsStream(name);
            if (in==null) {
            	throw new FileNotFoundException(name+" not found");
			}
            p.load(in);
        }

        if (in != null) {
            in.close();
        }
        return p;

    }

    // ---------------------------------------------- servlet used
    /*
     * public static Properties loadProperties(ServletContext context, String
     * path) throws IOException { assert (context != null); InputStream in =
     * context.getResourceAsStream(path); assert (in != null); Properties p =
     * new Properties(); p.load(in); in.close(); return p; }
     */
    // ---------------------------------------------- support class
    /**
     * ResourceBundle Adapter class.
     */
    public static class ResourceBundleAdapter extends Properties {
        public ResourceBundleAdapter(ResourceBundle rb) {
            this.rb = rb;
            Enumeration e = rb.getKeys();
            while (e.hasMoreElements()) {
                Object o = e.nextElement();
                this.put(o, rb.getObject((String) o));
            }
        }

        private ResourceBundle rb = null;

        public ResourceBundle getBundle(String baseName) {
            return ResourceBundle.getBundle(baseName);
        }

        public ResourceBundle getBundle(String baseName, Locale locale) {
            return ResourceBundle.getBundle(baseName, locale);
        }

        public ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
            return ResourceBundle.getBundle(baseName, locale, loader);
        }

        public Enumeration getKeys() {
            return rb.getKeys();
        }

        public Locale getLocale() {
            return rb.getLocale();
        }

        public Object getObject(String key) {
            return rb.getObject(key);
        }

        public String getString(String key) {
            return rb.getString(key);
        }

        public String[] getStringArray(String key) {
            return rb.getStringArray(key);
        }

        protected Object handleGetObject(String key) {
            return ((PropertyResourceBundle) rb).handleGetObject(key);
        }

    }
}
