package org.sunso.demo.parallel.index.task;

import org.springframework.stereotype.Component;
import org.sunso.demo.parallel.index.IndexParallelTaskKey;
import org.sunso.demo.parallel.index.parameter.IndexMysqlParallelResponse;
import org.sunso.demo.parallel.index.parameter.IndexTaskParameter;
import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelRequest;
import org.sunso.parallel.parameter.BaseParallelResponse;
import org.sunso.parallel.task.IBaseParallelTask;

@Component
public class IndexReadMysqlTask implements IBaseParallelTask<IndexTaskParameter> {
    @Override
    public ParallelTaskKey getParallelTaskKey() {
        return IndexParallelTaskKey.IndexReadMysqlTask;
    }

    @Override
    public BaseParallelResponse taskEntry(BaseParallelRequest<IndexTaskParameter> baseParallelRequest)
            throws Exception {
        return IndexMysqlParallelResponse.newBizSuccessResponse(getParallelTaskKey(),
                IndexMysqlParallelResponse.MysqlData.create().setMysqlData("mysqlData"));
    }
}
