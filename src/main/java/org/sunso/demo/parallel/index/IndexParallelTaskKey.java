package org.sunso.demo.parallel.index;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.task.IBaseParallelTask;

public enum IndexParallelTaskKey implements ParallelTaskKey {
    IndexReadMysqlTask("IndexReadMysqlTask"), IndexReadRedisTask("IndexReadRedisTask"), IndexReadS3Task(
            "IndexReadS3Task");

    private String key;

    IndexParallelTaskKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public IBaseParallelTask getIBaseParallelTask() {
        return null;
    }
}
