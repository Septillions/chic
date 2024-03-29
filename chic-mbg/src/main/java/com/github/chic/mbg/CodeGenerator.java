package com.github.chic.mbg;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * MyBatis-Plus-Generator 代码生成器
 * 配置参考
 * https://baomidou.com/pages/d357af/
 * https://baomidou.com/pages/061573/
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/chic?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        generator.setDataSource(dsc);
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/chic-mbg/src/main/java");
        gc.setFileOverride(true);
        gc.setOpen(false);
        gc.setBaseResultMap(false);
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        generator.setGlobalConfig(gc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.github.chic");
        generator.setPackageInfo(pc);
        // 模板配置
        TemplateConfig tc = new TemplateConfig();
        // tc.setEntity(null);
        // tc.setMapper(null);
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setXml(null);
        generator.setTemplate(tc);
        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setControllerMappingHyphenStyle(true);
        sc.setRestControllerStyle(true);
        sc.setEntityTableFieldAnnotationEnable(true);
        sc.setTablePrefix("t_");
        generator.setStrategy(sc);
        // 模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }
}
