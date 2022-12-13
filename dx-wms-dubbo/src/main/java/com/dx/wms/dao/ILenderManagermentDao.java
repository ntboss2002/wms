package com.dx.wms.dao;

import java.util.Date;

import com.dx.wms.bean.InvokeLog;
import com.dx.wms.bean.LenderApply;

public interface ILenderManagermentDao {

    /**
     * 
     * 功能描述: 根据调用日志流水号 查询日志 〈功能详细描述〉
     *
     * @param InvokeLogCode 调用日志Code
     * @return
     */
    InvokeLog queryByInvokeLogCode(String invokeLogCode);

    /**
     * 
     * 功能描述: 根据调理财申请编号 查询日志 〈功能详细描述〉
     *
     * @param InvokeLogCode 调用日志Code
     * @return
     */
    InvokeLog queryByLenderApplyId(Long lenderApplyId);

    /**
     * 
     * 功能描述: 根据调用CODE 修改调用数据状态 〈功能详细描述〉
     *
     * @param lenderCode 理财编号
     * @param dataStatus 调用日志状态
     * @return
     */
    int setInvokeLogStatusByCode(String lenderCode, String dataStatus);

    /**
     * 
     * 功能描述: 根据调用CODE 修改调用数据状态 〈功能详细描述〉
     *
     * @param enderCode 理财编号
     * @param dataStatus 调用日志状态
     * @return
     */
    int setInvokeLogInfoByCode(String lenderCode, String dataStatus, String approve);

    /**
     * 
     * 功能描述: 根据理财编号修改到账日期 〈功能详细描述〉
     *
     * @return
     */
    int setSettlementDateById(Long lenderApplyId, Date settlementDate, Long updateUser);

    /**
     * 
     * 功能描述: 根据理财申请ID查询到 对应的客户基础表和客户开户表，修改状态为认证状态，使此时数据不再能修改
     *
     * @param lenderApplyId
     * @return
     */
    Boolean changeStatesByLengderApplyId(Long lenderApplyId);

    /**
     * 
     * 功能描述: 修改到账日时 同时修改 等待投资确认日志的updateUser 〈功能详细描述〉
     *
     * @param lenderApplyId
     * @param updateUser
     * @return
     */
    int updateLenderLogUpdateUser(Long lenderApplyId, Long updateUser);

    /**
     * 
     * 功能描述: 根据理财申请ID修改申请单状态
     *
     * @param lenderApplyId
     * @param formStatus
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    Boolean updateLenderApplyFormStatus(Long lenderApplyId, Long formStatus);

    /**
     * 
     * 功能描述: 根据理财申请ID查询理财申请表
     *
     * @param lenderApplyId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    LenderApply getLenderApplyById(Long lenderApplyId);

    /**
     * 
     * 功能描述: 根据理财申请code查询理财申请表
     *
     * @param lenderApplyId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    LenderApply queryByCode(String lenderCode);

    /**
     * 
     * 功能描述:根据理财申请单 ID 修改 理财申请单状态 〈功能详细描述〉
     *
     * @param lenderApplyId
     * @param dataStatus
     * @return
     */
    int updateLenderApplyDataStatus(Long lenderApplyId, String dataStatus);

    /**
     * 
     * 更新账单日
     *
     * @param id
     * @param stateDate
     * @param matchDate
     */
    int updateForState(String lenderCode, Integer statementDate, Date matchDate);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param lenderApplyId
     * @param dueStatus
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void updateDueApplyStatus(Long lenderApplyId, String dueStatus);
}
