package org.sunso.demo.perf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.test.perf.bootstrap.AsyncBenchmarkBootstrap;
import org.sunso.test.perf.request.BenchmarkRequest;

import java.util.concurrent.Executors;

public class PerfMapServiceTest extends BaseSpringBootTest {
    @Autowired
    private PerfMapService perfMapService;

    @Test
    public void putMapTest() {
        BenchmarkRequest request = AsyncBenchmarkBootstrap.create()
                .setStatisticsExecuteService(Executors.newFixedThreadPool(1))
                .setTotalProcessingNum(1000)
                .setTotalThreadNum(5)
                .setRemark("put map test")
                .setRunnable(() -> {
                    perfMapService.putMap(100, 10000000);
                }).run();
        Assert.assertNotNull(request.getReportList());
        request.getReportList().forEach(report -> {
            Assert.assertEquals(report.getTotalProcessingNum(), request.getTotalProcessingNum());
            Assert.assertEquals(report.getTotalThreadNum(), request.getTotalThreadNum());
            Assert.assertEquals(report.getTotalStatisticsNum(), request.getTotalProcessingNum());
            Assert.assertEquals(report.getRemark(), request.getRemark());
        });
    }
}
