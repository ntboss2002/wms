package com.dx.math.service.manager;

import com.dx.math.service.entity.MathSignType;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author tony
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IMathDetailService {
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param signKey
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    MathSignType getTypeBySign(String signKey);
}
