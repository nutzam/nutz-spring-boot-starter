package tech.riemann.nutz.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

import tech.riemann.nutz.demo.entity.IdBaseEntity;

public class MysqlGenerator {

    static String jdbcUrl = "jdbc:mysql://10.100.130.91:3306/thunder?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    static String user = "thunder";
    static String password = "ZhaXcYaabZRKx5zw";
    static String packageName = "tech.riemann.nutz";
    static String module = "demo";

    static Mode mode = Mode.NUTZ;

    public enum Mode {
        NUTZ, MYBATIS, BOTH
    }

    public static String[] prefixes(String... addOn) {
        List<String> pool = Lang.array2list("abcdefghijklmnopqrstuvwxyz".toCharArray(), char.class)
                                .stream()
                                .map(c -> c + "")
                                .collect(Collectors.toList());

        List<String> prefixes = pool.stream().map(item -> item + "_").collect(Collectors.toList());

        pool.stream().forEach(item -> {
            pool.stream().forEach(item1 -> {
                prefixes.add(String.format("%s%s_", item, item1));
            });
        });
        if (Lang.isNotEmpty(addOn)) {
            Arrays.stream(addOn).forEach(add -> {
                Arrays.stream(Lang.collection2array(prefixes)).forEach(p -> {
                    prefixes.add(String.format("%s%s_", p, add));
                });
            });
        }
        return Lang.collection2array(prefixes);
    }

    /**
     * RUN THIS TO GEN CODE
     */
    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");
        List<String> relationTables = Lang.list("t_acl_role_permission", "t_acl_user_permission", "t_acl_user_role");
        NutMap.NEW()
              .addv("acl", Lang.array("t_acl_permission"))
              // .addv("dictionary", Lang.array("t_dictionary_dictionary",
              // "t_dictionary_group"))
              .entrySet()
              .stream()
              .forEach(e -> {

                  FastAutoGenerator.create(jdbcUrl, user, password)
                                   .globalConfig(builder -> {
                                       builder
                                              .author("Kerbores(kerbores@gmail.com)")
                                              .enableSpringdoc()
                                              .commentDate(() -> LocalDateTime.now()
                                                                              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",
                                                                                                                  Locale.CHINESE)))
                                              .disableOpenDir()
                                              .dateType(DateType.TIME_PACK)
                                              .outputDir(projectPath + "/src/main/java");
                                   })
                                   .injectionConfig(builder -> builder.customMap(Collections.singletonMap("mode", mode)))
                                   .packageConfig(builder -> {
                                       builder.parent(packageName)
                                              .moduleName(module)
                                              .entity(String.format("entity.%s", e.getKey()))
                                              .mapper(String.format("mapper.%s", e.getKey()))
                                              .service(String.format("service.%s", e.getKey()))
                                              .serviceImpl(String.format("service.%s.impl", e.getKey()))
                                              .controller(String.format("controller.platform.%s", e.getKey()))
                                              .pathInfo(Collections.singletonMap(OutputFile.xml,
                                                                                 String.format("%s/src/main/resources/mapper_",
                                                                                               projectPath)));
                                   })
                                   .templateConfig(builder -> builder
                                                                     .disable(mode == Mode.NUTZ ? Lang.array(
                                                                                                             TemplateType.MAPPER,
                                                                                                             TemplateType.XML,
                                                                                                             TemplateType.SERVICE)
                                                                                                : Lang.array())
                                                                     .controller(String.format("templates/%s/controller.java",
                                                                                               mode.name().toLowerCase()))
                                                                     .serviceImpl(String.format("templates/%s/serviceImpl.java",
                                                                                                mode.name().toLowerCase()))
                                                                     .service(String.format("templates/%s/service.java",
                                                                                            mode.name().toLowerCase()))
                                                                     .xml(String.format("templates/%s/mapper.xml",
                                                                                        mode.name().toLowerCase()))
                                                                     .mapper(String.format("templates/%s/mapper.java",
                                                                                           mode.name().toLowerCase()))
                                                                     .entity(String.format("templates/%s/entity.java",
                                                                                           mode.name().toLowerCase())))
                                   .strategyConfig(builder -> {
                                       builder.addTablePrefix(String.format("t_%s_", e.getKey()))
                                              .addFieldPrefix(prefixes(e.getKey()))
                                              .addInclude((String[]) e.getValue()) // 指定表生成
                                              .entityBuilder()
                                              .enableLombok()
                                              .enableChainModel()
                                              .enableRemoveIsPrefix()
                                              .enableFileOverride()
                                              .enableTableFieldAnnotation()
                                              .superClass(IdBaseEntity.class)
                                              .addSuperEntityColumns(
                                                                     "id",
                                                                     "created_by",
                                                                     "created_time",
                                                                     "updated_by",
                                                                     "updated_time")
                                              .naming(NamingStrategy.underline_to_camel)

                                              .controllerBuilder()
                                              .enableRestStyle()
                                              .enableHyphenStyle()
                                              .convertFileName(entityName -> {
                                                  boolean isRelation = relationTables.stream().anyMatch(item -> {
                                                      String name = NamingStrategy.capitalFirst(
                                                                                                NamingStrategy.underlineToCamel(
                                                                                                                                item.replace(String.format("t_%s_",
                                                                                                                                                           e.getKey()),
                                                                                                                                             "")));
                                                      return Strings.equals(entityName, name);
                                                  });
                                                  return isRelation ? null : entityName + ConstVal.CONTROLLER;
                                              })
                                              .mapperBuilder()
                                              .enableFileOverride()
                                              .convertMapperFileName(entityName -> mode == Mode.NUTZ ? null : entityName + ConstVal.MAPPER)
                                              .convertXmlFileName(entityName -> mode == Mode.NUTZ ? null : entityName + ConstVal.MAPPER)
                                              .superClass(BaseMapper.class)
                                              .enableBaseResultMap()
                                              .enableBaseColumnList()
                                              .serviceBuilder()
                                              .convertServiceFileName(entityName -> (mode == Mode.NUTZ ? "" : "I")
                                                                                    + entityName
                                                                                    + ConstVal.SERVICE)
                                              .convertServiceImplFileName(entityName -> mode == Mode.NUTZ ? null
                                                                                                          : entityName
                                                                                                            + ConstVal.SERVICE_IMPL);
                                   })
                                   .templateEngine(new BeetlTemplateEngine())
                                   .execute();
              });

    }
}
