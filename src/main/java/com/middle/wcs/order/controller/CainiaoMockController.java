package com.middle.wcs.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 菜鸟大包查询模拟接口（仅开发调试用）
 * 路径与真实菜鸟接口一致，生产环境前端直连 apiplat，不走此接口
 */
@Api(tags = "菜鸟模拟接口（开发调试）")
@RestController
@RequestMapping("/PreSupervision")
@Slf4j
public class CainiaoMockController {

    private static final String[] LANE_CODES = {
            "L_AE_EXPRESS_SGSEA_KR_V2V",
            "L_AE_EXPRESS_SEA_XTW_CJ",
            "L_AE_EXPRESS_XTWSEA_ACT_V2V"
    };

    private final Random random = new Random();

    @ApiOperation("模拟根据大包号查询大包信息")
    @PostMapping("/getBigPackageToMCS")
    public Map<String, Object> getBigPackageToMCS(
            @RequestParam(value = "key", required = false) String key,
            @RequestBody(required = false) Map<String, Object> payload) {
        log.info("菜鸟模拟接口收到请求，key={}，payload={}", key, payload);

        // 大包号：使用前端扫码传入的码
        String bigPackageCode = "";
        if (payload != null && payload.get("bigPackageCode") != null) {
            bigPackageCode = String.valueOf(payload.get("bigPackageCode")).trim();
        }

        // 小包数量：50% 为 1（小包），50% 为 2~40（大包）
        String smallPackageQuantity;
        if (random.nextBoolean()) {
            smallPackageQuantity = "1";
        } else {
            smallPackageQuantity = String.valueOf(2 + random.nextInt(39));
        }

        // 渠道：3 个固定值随机
        String laneCode = LANE_CODES[random.nextInt(LANE_CODES.length)];

        Map<String, Object> data = new HashMap<>();
        data.put("bigPackageCode", bigPackageCode);
        data.put("standardWeight", "18147");
        data.put("grossWeight", "18070");
        data.put("netWeight", "17947");
        data.put("labelWeight", "18");
        data.put("packingWeight", "200");
        data.put("volume", null);
        data.put("volumeUnit", null);
        data.put("laneCode", laneCode);
        data.put("smallPackageQuantity", smallPackageQuantity);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("subCode", null);
        result.put("message", "成功");
        result.put("success", true);
        result.put("data", data);

        log.info("菜鸟模拟接口返回：大包号={}，小包数={}，渠道={}",
                bigPackageCode, smallPackageQuantity, laneCode);
        return result;
    }
}
