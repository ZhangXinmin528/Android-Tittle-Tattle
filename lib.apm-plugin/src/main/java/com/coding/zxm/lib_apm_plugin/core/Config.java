package com.coding.zxm.lib_apm_plugin.core;

import android.content.Context;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.api.IExtraDataCallback;
import com.coding.zxm.lib_apm_plugin.cloud.IRuleRequest;
import com.coding.zxm.lib_apm_plugin.network.IUpload;
import com.coding.zxm.lib_apm_plugin.util.LogX;
import com.coding.zxm.lib_apm_plugin.util.ProcessUtils;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;

/**
 * APM启动配置项
 *
 * @author ArgusAPM Team
 */
public class Config {
    private static final String SUB_TAG = "Config";
    public Context appContext;
    public String appName = "";
    public String appVersion = "";
    public String apmId = "apm_unknown";
    public int localFlags = 0xffffffff;// 默认开关全开,用于控制在多进程的场景下，是否执行采集任务，是否做上传、清理
    public IExtraDataCallback mExtraDataCallback;
    /**
     * 云规则请求
     */
    public IRuleRequest mRuleRequest;
    /**
     * 采集数据上传至服务器
     */
    public IUpload mCollectDataUpload;

    public Config() {
        // 本地读取配置文件开关需要默认关闭
        localFlags &= ~ApmTask.FLAG_LOCAL_DEBUG;
        // 默认使用Instrumation的方式，不使用aop的方式
        localFlags &= ~ApmTask.FLAG_COLLECT_ACTIVITY_AOP;
    }

    public boolean isEnabled(int flag) {
        return (localFlags & flag) == flag;
    }

    @Override
    public String toString() {
        return "apm config : appContext:" + appContext.toString() +
                " appName:" + appName +
                " appVersion:" + appVersion +
                " apmId:" + apmId +
                " flags:" + Integer.toBinaryString(localFlags) +
                " proc: " + ProcessUtils.getCurrentProcessName()
                ;
    }

    public static class ConfigBuilder {
        private Config config = new Config();

        public ConfigBuilder setAppContext(Context appContext) {
            this.config.appContext = appContext;
            return this;
        }

        public ConfigBuilder setAppName(String appName) {
            this.config.appName = appName;
            return this;
        }

        public ConfigBuilder setAppVersion(String appVersion) {
            this.config.appVersion = appVersion;
            return this;
        }

        public ConfigBuilder setApmid(String apmId) {
            this.config.apmId = apmId;
            return this;
        }

        public ConfigBuilder setRuleRequest(IRuleRequest ruleRequest) {
            this.config.mRuleRequest = ruleRequest;
            return this;
        }

        public ConfigBuilder setUpload(IUpload upload) {
            this.config.mCollectDataUpload = upload;
            return this;
        }

        public ConfigBuilder setExtraDataCallback(IExtraDataCallback reader) {
            this.config.mExtraDataCallback = reader;
            return this;
        }

        public ConfigBuilder setEnabled(int flag) {
            this.config.localFlags |= flag;
            return this;
        }

        public ConfigBuilder setDisabled(int flag) {
            this.config.localFlags &= (~flag);
            return this;
        }

        public Config build() {
            if (DEBUG) {
                LogX.d(TAG, SUB_TAG, config.toString());
            }
            if (this.config.mRuleRequest == null) {
                throw new RuntimeException("Please use this method(@link ConfigBuilder setRuleRequest(IRuleRequest ruleRequest)) to configure network requests");
            }

            if (this.config.mCollectDataUpload == null) {
                throw new RuntimeException("Please use this method(@link ConfigBuilder.setUpload(IUpload upload)) to configure network requests");
            }
            return config;
        }
    }
}