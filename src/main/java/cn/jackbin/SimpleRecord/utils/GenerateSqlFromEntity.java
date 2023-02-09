package cn.jackbin.SimpleRecord.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

// 通过实体类生成 mysql 的建表语句
public class GenerateSqlFromEntity {
    // main
    public static void main(String[] a) throws ClassNotFoundException {
        String outputPath = "./输出建表语句/建表语句.txt";   // c:/
        Class<?> aClass = Class.forName("cn.jackbin.SimpleRecord.entity.RecordBookDO");

        String name = aClass.getName();
        String simpleName = aClass.getSimpleName();
		generateTableSql(aClass, outputPath);

        System.out.println("操作结束");
    }

    // writeFile with BufferedWriter
    public static void writeFile(String content, String outputPath) {
        File file = new File(outputPath);
        System.out.println("文件路径: " + file.getAbsolutePath());

        // 输出文件的路径
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter out = null;

        try {
            // 如果文件存在，就删除
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos);
            out = new BufferedWriter(osw);

            out.write(content);

            // 清空缓冲流，把缓冲流里的文本数据写入到目标文件里
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateTableSql(Class obj, String outputPath) {
        String tableName = "";
        TableName annotation = (TableName) obj.getAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        } else {
            tableName = StrUtil.toUnderlineCase(obj.getSimpleName());
        }
        List<Field> fields = new ArrayList<>();
        //只要父类存在，就获取其类的属性到集合
        while (obj != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(obj.getDeclaredFields())));
            //获取其父类
            obj = obj.getSuperclass();
        }

        Object param = null;
        String column = null;

        StringBuilder sb = new StringBuilder();

        sb.append("drop table if exists ").append(tableName).append(";\r\n");

        sb.append("\r\n");

        sb.append("create table ").append(tableName).append("(\r\n");

        System.out.println(tableName);

        boolean firstId = false;

        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            if (f.getName().equals("serialVersionUID")) {
                continue;
            }

            column = StrUtil.toUnderlineCase(f.getName());

            System.out.println(column + ", " + f.getType().getSimpleName());
            TableId primaryId = f.getAnnotation(TableId.class);
            if (primaryId != null) {
                column = primaryId.value();
                firstId = true;

            }
            sb.append("`").append(column).append("`");
            param = f.getType();
            System.out.println("类型：" + param);
            System.out.println("时间类型是否：" + (param instanceof Date));
            if (param instanceof Integer) {
                sb.append(" INT ");
            } else if (param instanceof Long) {
                sb.append(" INT ");
            } else if (param instanceof Date) {
                System.out.println("这个进不去？");
                sb.append(" DATETIME ");
            } else {
                // 注意：根据需要，自行修改 varchar 的长度。这里设定为长度等于 50
                int length = 50;
                sb.append(" VARCHAR(").append(length).append(")");
            }

            if (firstId) {
                sb.append(" PRIMARY KEY ");
                firstId = false;
            }
            // 获取字段中包含 fieldMeta 的注解
            // 获取属性的所有注释
            XmlElement xmlElement = f.getAnnotation(XmlElement.class);
            System.out.println(xmlElement);
            if (xmlElement != null) {
                sb.append(" COMMIT '");
                param = xmlElement.name();
                System.out.println("属性 " + f.getName()
                        + " ----- 的注释类型有: " + param);
                sb.append(param).append("'");
            }
            // 最后一个属性后面不加逗号
            if (i != fields.size() - 1) {
                sb.append(", ");
            }

            sb.append("\n");
        }

        String sql = sb.toString();

        sql = sb.substring(0, sql.length() - 1)
                + "\n) " + "ENGINE = INNODB DEFAULT  CHARSET = utf8;";

        writeFile(sql, outputPath);
    }

}