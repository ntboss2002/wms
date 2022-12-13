/*
 * Copyright (C), 2014-2016, 达信财富投资管理（上海）有限公司
 * FileName: IDueService.java
 * Author:   chenjie
 * Date:     2016年4月15日 下午1:27:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.dx.wms.service;

import java.util.List;

/**
 * 查询到期用户<br>
 * 〈功能详细描述〉
 *
 * @author chenjie
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IDueService {

    List<String> queryDueCustName(List<Long> dueApplyIds, Long userId);

    /**
     * 
     * 功能描述: 根据规则查询理财申请ID 〈功能详细描述〉
     *
     * @param ruleKey
     * @return
     */
    List<Long> getDueIds(String ruleKey);

}