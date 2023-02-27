package org.sunso.demo.parallel.index.parameter;

import org.sunso.parallel.key.ParallelTaskKey;
import org.sunso.parallel.parameter.BaseParallelResponse;

public class IndexMysqlParallelResponse extends BaseParallelResponse<IndexMysqlParallelResponse.MysqlData> {

    public static IndexMysqlParallelResponse newBizSuccessResponse(ParallelTaskKey parallelTaskKey,
            IndexMysqlParallelResponse.MysqlData mysqlData) {
        IndexMysqlParallelResponse response = new IndexMysqlParallelResponse();
        response.setBizSuccess(true);
        response.setParallelTaskKey(parallelTaskKey);
        response.setData(mysqlData);
        return response;
    }

    public static class MysqlData {

        public static MysqlData create() {
            return new MysqlData();
        }

        private String mysqlData;

        public String getMysqlData() {
            return mysqlData;
        }

        public MysqlData setMysqlData(String mysqlData) {
            this.mysqlData = mysqlData;
            return this;
        }
    }
}
