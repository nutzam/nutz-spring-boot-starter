package tech.riemann.demo.controller;

import tech.riemann.demo.entity.acl.User;

/**
 * @author kerbores
 *
 */
public class BaseController {

    /**
     * 当前用户
     * 
     * @return
     */
    protected User currentUser() {
        return null;
    }

}
