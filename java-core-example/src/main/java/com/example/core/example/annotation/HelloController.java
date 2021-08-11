package com.example.core.example.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *      测试控制类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/11 10:03  修改内容:
 * </pre>
 */
@RestController
public class HelloController {

    private Logger LOG = LoggerFactory.getLogger(HelloController.class);

    private final RestTemplate restTemplate;

    public HelloController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     *  <pre>获取github用户信息 RESTFUL API </pre>
     * {@link ://127.0.0.1:8080/api/hello/mojombo}
     * @Author mazq
     * @Date 2021/08/11 10:11
     * @Param [userCode]
     * @return org.springframework.http.ResponseEntity<?>
     */
    @EnableLogger
    @RequestMapping(value = {"/api/hello/{userCode}"})
    public ResponseEntity<?> hello(@PathVariable("userCode") String userCode) {
        LOG.info("Looking up " + userCode);
        String url = String.format("https://api.github.com/users/%s", userCode);
        GithubUser userInfo = restTemplate.getForObject(url, GithubUser.class);
        return ResponseEntity.ok(userInfo);
    }
}
