package com.example.provider.controller;

import com.example.common.vo.DemoRepVO;
import com.example.common.vo.DemoVO;
import com.example.common.vo.ResponseVO;
import com.example.provider.aspect.AutoLog;
import com.example.provider.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author diaoyn
 * @create 2024-03-29 15:00:41
 */
@RestController
@RequestMapping
@Api(tags = "demo服务端接口")
public class DemoController {

    @Resource
    DemoService demoService;

    @ApiOperation("测试用户")
    @PostMapping("/demo")
    @AutoLog(value = "/demo", name = "测试用户")
    public ResponseVO<DemoRepVO> demo(@RequestBody @Validated DemoVO vo) {
        return demoService.demo(vo);
    }


    @ApiOperation("超过3秒")
    @PostMapping("/sleep")
    public ResponseVO<DemoRepVO> sleep(@RequestBody @Validated DemoVO vo) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return demoService.demo(vo);
    }
}
