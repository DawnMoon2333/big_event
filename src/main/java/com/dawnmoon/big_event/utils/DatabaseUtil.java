package com.dawnmoon.big_event.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseUtil {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @PostConstruct
    public void initDB() {
        System.out.println("开始初始化数据库...");
        
        Connection connection = null;
        Statement statement = null;
        
        try {
            // 读取schema.sql文件内容
            ClassPathResource resource = new ClassPathResource("InitDB.sql");
            String sqlScript = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            
            // 建立数据库连接
            connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
            statement = connection.createStatement();
            
            // 按分号分割SQL语句并执行
            String[] sqlStatements = sqlScript.split(";");
            for (String sql : sqlStatements) {
                sql = sql.trim();
                if (!sql.isEmpty()) {
//                    System.out.println("执行SQL: " + sql);
                    statement.execute(sql);
                }
            }
            
            System.out.println("数据库初始化成功！");
            
        } catch (SQLException e) {
            System.out.println("数据库初始化失败！错误信息：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("读取schema.sql文件失败！错误信息：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("关闭Statement失败：" + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("关闭数据库连接失败：" + e.getMessage());
                }
            }
        }
    }
}

