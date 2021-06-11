package org.nutz.spring.boot.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutz.log.Log;
import org.nutz.log.LogAdapter;
import org.nutz.log.Logs;
import org.nutz.log.impl.AbstractLog;
import org.nutz.plugin.Plugin;

public class Log4j2Adapter implements LogAdapter, Plugin {
    @Override
    public Log getLogger(String className) {
        return new Log4J2Logger(className);
    }

    static class Log4J2Logger extends AbstractLog {

        private Logger logger;

        public Log4J2Logger(String className) {
            logger = LogManager.getLogger(className);
            isFatalEnabled = logger.isFatalEnabled();
            isErrorEnabled = logger.isErrorEnabled();
            isWarnEnabled = logger.isWarnEnabled();
            isInfoEnabled = logger.isInfoEnabled();
            isDebugEnabled = logger.isDebugEnabled();
            isTraceEnabled = logger.isTraceEnabled();
        }

        @Override
        protected void log(int level, Object message, Throwable tx) {
            switch (level) {
            case LEVEL_FATAL:
                logger.log(Level.FATAL, message, tx);
                break;
            case LEVEL_ERROR:
                logger.log(Level.ERROR, message, tx);
                break;
            case LEVEL_WARN:
                logger.log(Level.WARN, message, tx);
                break;
            case LEVEL_INFO:
                logger.log(Level.INFO, message, tx);
                break;
            case LEVEL_DEBUG:
                logger.log(Level.DEBUG, message, tx);
                break;
            case LEVEL_TRACE:
                logger.log(Level.TRACE, message, tx);
                break;
            default:
                break;
            }
        }

        @Override
        public void fatal(Object message, Throwable t) {
            log(LEVEL_FATAL, message, t);
        }

        @Override
        public void error(Object message, Throwable t) {
            log(LEVEL_ERROR, message, t);
        }

        @Override
        public void warn(Object message, Throwable t) {
            log(LEVEL_WARN, message, t);
        }

        @Override
        public void info(Object message, Throwable t) {
            log(LEVEL_INFO, message, t);
        }

        @Override
        public void debug(Object message, Throwable t) {
            log(LEVEL_DEBUG, message, t);
        }

        @Override
        public void trace(Object message, Throwable t) {
            log(LEVEL_TRACE, message, t);
        }

    }

    @Override
    public boolean canWork() {
        try {
            Logger.class.getName();
            return true;
        }
        catch (Exception e) {
            Logs.get().debug(e);
        }
        return false;
    }
}
