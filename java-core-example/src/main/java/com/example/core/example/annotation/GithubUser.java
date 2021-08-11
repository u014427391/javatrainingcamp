package com.example.core.example.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  用户信息实体类
 *
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/20 10:14  修改内容:
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubUser implements Serializable {

    private String name;
    private String blog;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
