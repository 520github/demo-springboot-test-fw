package org.sunso.demo.parallel.index;

import org.springframework.stereotype.Service;
import org.sunso.demo.parallel.index.parameter.IndexTaskParameter;
import org.sunso.parallel.bootstrap.FailRetryParallelTaskBootstrap;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class IndexService {
    public void runIndexParallelTask(IndexTaskParameter indexTaskParameter) {
        List<BaseParallelResponse> responseList = FailRetryParallelTaskBootstrap.create()
                .setFailRetryNum(3)
                .setExecutor(Executors.newFixedThreadPool(3))
                .setPoolTaskResultTimeout(100)
                .setTimeUnit(TimeUnit.MILLISECONDS).setBizRequest(indexTaskParameter)
                .isSpringTaskBean(true)
                .executeParallelTask(
                        IndexParallelTaskKey.IndexReadMysqlTask,
                        IndexParallelTaskKey.IndexReadRedisTask,
                        IndexParallelTaskKey.IndexReadS3Task
                );
        System.out.println("size:" + responseList.size());
    }
}
