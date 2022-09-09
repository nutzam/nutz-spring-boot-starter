package org.nutz.spring.boot.dao.sqltpl.impl.beetl;

import org.beetl.core.Resource;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResource;
import org.beetl.core.resource.StringTemplateResource;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 */
public class ClasspathStringResourceLoader extends ClasspathResourceLoader {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ClasspathStringResourceLoader() {
        super();
    }

    public ClasspathStringResourceLoader(ClassLoader classLoader, String root, String charset) {
        super(classLoader, root, charset);
    }

    public ClasspathStringResourceLoader(ClassLoader classLoader, String root) {
        super(classLoader, root);
    }

    public ClasspathStringResourceLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public ClasspathStringResourceLoader(String root, String charset) {
        super(root, charset);
    }

    public ClasspathStringResourceLoader(String root) {
        super(root);
    }

    @Override
    public Resource getResource(String key) {
        if (key.startsWith("/"))
            return super.getResource(key);
        return new StringTemplateResource(key, this);
    }

    @Override
    public boolean isModified(Resource key) {
        if (key instanceof FileResource)
            return super.isModified(key);
        return false;
    }

    @Override
    public boolean exist(String key) {
        if (key.startsWith("/"))
            return super.exist(key);
        return true;
    }

}
