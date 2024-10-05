package com.fixedasset.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.fixedasset.common.lang.Const;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.SysUser;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
public class AuthController extends BaseController {


    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();


        BufferedImage image = producer.createImage(code);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpg", outputStream);

        Base64 base64 = new Base64();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + base64.encodeBase64String(outputStream.toByteArray());



        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120 );

        return Result.succ(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64Img)
                        .build()

        );
    }
    /**
     * Obtain user information interface
     * @param principal
     * @return
     */
    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal) {

        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        return Result.succ(sysUser);
    }
}
