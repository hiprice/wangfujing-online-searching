package com.wfj.search.online.index.operation;

import com.wfj.search.online.index.service.IRetryService;
import com.wfj.search.util.record.pojo.Operation;
import com.wfj.search.util.record.pojo.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <br/>create at 15-12-7
 *
 * @author liuxh
 * @author liufl
 * @since 1.0.0
 */
@Component("retrySaveItemOperation")
public class RetrySaveItemOperation implements IOperation<Void> {
    @Autowired
    private IRetryService retryService;

    /**
     * 此处多写一个operation，完全是为了能够使用operation切面记录。
     */
    @Override
    public Void operate(Operation opRecord) throws Exception {
        retryService.retrySaveItem(Long.parseLong(opRecord.getSid())).ifPresent(collector -> {
            if (collector.isFail()) {
                opRecord.setStatus(Status.FAILURE.getValue());
            }
            String msg = collector.toString();
            String comment = opRecord.getComment();
            if (StringUtils.isBlank(comment)) {
                opRecord.setComment(msg);
            } else {
                opRecord.setComment(comment + "\n" + msg);
            }
        });
        return null;
    }
}
