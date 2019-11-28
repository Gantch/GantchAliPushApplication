package com.gantch.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>时钟工具类</h1>
 * 每一次 tick 记录一次当前的时间, 用于把方法的完整执行过程时间打印出来
 * Created by Qinyi.
 */
public class TickUtil {

    private static final Logger logger = LoggerFactory.getLogger(TickUtil.class);

    /** 存储当前 tick 的时间戳 */
    private List<Long> ticks;
    /** 方法信息 */
    private String funcInfo;
    /** 额外信息, 选填 */
    private String extra;
    /** 最短时间限制, 执行时间低于这个时间将不会打印记录日志, 默认是 100ms */
    private Long timeLimit;

    /**
     * <h2>构造函数</h2>
     * */
    public TickUtil(String funcInfo) {

        this.ticks = new ArrayList<>();
        this.funcInfo = funcInfo;
        this.extra = "default";
        this.timeLimit = 100L;
    }

    /**
     * <h2>构造函数</h2>
     * @param timeLimit 最小输出时间限制, 默认值是 100ms
     * */
    public TickUtil(String funcInfo, String extra, Long timeLimit) {

        this.ticks = new ArrayList<>();
        this.funcInfo = funcInfo;
        this.extra = extra == null ? "default" : extra;
        this.timeLimit = (timeLimit != null && timeLimit >= 100) ? timeLimit : 100;
    }

    /**
     * <h2>开始 tick</h2>
     * */
    public void start() {
        tick();
    }

    /**
     * <h2>停止 tick</h2>
     * */
    public void stop() {
        tick();
        log();
    }

    /**
     * <h2>tick 过程, 记录当前的时间戳</h2>
     * */
    public void tick() {
        ticks.add(System.currentTimeMillis());
    }

    /**
     * <h2>日志记录</h2>
     * */
    private void log() {

        // 时间 tick 数小于 2, 不需要输出
        if (CollectionUtils.isEmpty(ticks) || ticks.size() < 2) {
            return;
        }

        // 时间区间小于最小时间限制, 不需要输出
        if (ticks.get(ticks.size() - 1) - ticks.get(0) < timeLimit) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(funcInfo).append("-").append(extra).append(": ");

        // 把每一个步骤的耗时打印出来
        for (int i = 0; i != ticks.size() - 1; ++i) {

            sb.append(String.format("step%s-%sms", i + 1, ticks.get(i + 1) - ticks.get(i)));
            sb.append(";");
        }

        logger.info("{}", sb.toString());
    }
}
