package com.dx.cmm.service.manager;

/**
 * 
 * 支持者
 *
 * @author tony
 */
public interface Supporter<P> {
    /**
     * 
     * 业务支持
     *
     * @param param
     * @return
     */
    Boolean supports(P param);

    /**
     * 
     * 服务加入
     *
     */
    void join();

}
