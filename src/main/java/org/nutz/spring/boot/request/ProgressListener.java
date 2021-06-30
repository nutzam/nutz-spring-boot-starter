package org.nutz.spring.boot.request;

import org.nutz.lang.util.Callback;

/**
 * @author kerbores
 *
 */
public abstract class ProgressListener implements Callback<Integer> {

    /**
     * @param obj
     * @see org.nutz.lang.util.Callback#invoke(java.lang.Object)
     */
    @Override
    public void invoke(Integer obj) {
        onProcess(obj);
    }

    public abstract void onProcess(int process);

}
