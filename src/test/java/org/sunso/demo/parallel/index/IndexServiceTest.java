package org.sunso.demo.parallel.index;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.demo.parallel.index.IndexService;
import org.sunso.demo.parallel.index.parameter.IndexTaskParameter;

public class IndexServiceTest extends BaseSpringBootTest {
    @Autowired
    private IndexService indexService;

    @Test
    public void runIndexParallelTaskTest() {
        indexService.runIndexParallelTask(IndexTaskParameter.create());
    }
}
