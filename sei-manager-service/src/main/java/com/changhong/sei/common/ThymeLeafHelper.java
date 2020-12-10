package com.changhong.sei.common;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-10 11:04
 */
public final class ThymeLeafHelper {

    private static ThymeLeafHelper INSTANCE;

    //    private TemplateEngine templateEngine;
    private final SpringTemplateEngine templateEngine;

    private ThymeLeafHelper() {
        //        FileTemplateResolver templateResolver = new FileTemplateResolver();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        //        templateResolver.setPrefix(getTemplatePath());
        //模板所在目录，相对于当前classloader的classpath。模板的路径
        templateResolver.setPrefix("templates/");
        //        templateResolver.setPrefix("src/main/resources/templates/");
        templateResolver.setTemplateMode("TEXT");
        // 创建模板引擎
        templateEngine = new SpringTemplateEngine();
        // 将加载器放入模板引擎
        templateEngine.setTemplateResolver(templateResolver);
    }

    private String getTemplatePath() {
        return ThymeLeafHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "templates/";
    }

    public static SpringTemplateEngine getTemplateEngine() {
        if (INSTANCE == null) {
            synchronized (ThymeLeafHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ThymeLeafHelper();
                }
            }
        }
        return INSTANCE.templateEngine;
    }
}
