package com.abc.controller;

import com.abc.utils.JsonResult;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

/**
 * 开发步骤
 * 1、导包>commons-fileupload
 * 2、配置上传解析
 * 3、controller上传
 */

@Controller
public class uploadController {

    @RequestMapping("save")
    public String getData(String username,String imgPath){
        System.out.println("username:"+username+",path:"+imgPath);
        return "/index.jsp";
    }

    @RequestMapping("upload")
    @ResponseBody
    public JsonResult upload(MultipartFile file){
        JsonResult jsonResult = new JsonResult();
        try {
            InputStream inputStream = file.getInputStream();
            FTPClient ftp = new FTPClient();
            //连接FTP服务器，默认端口是21
            ftp.connect("192.168.61.3",21);
            //匿名用户必须使用anonymous登录，密码是邮箱
            boolean login = ftp.login("anonymous", "1328662381@qq.com");
            System.out.println(login);

            int replyCode = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("获取响应失败");
                jsonResult.setCode(1);
                return jsonResult;
            }
            //设置接收文件类型为二进制文件
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //服务器上创建images文件夹
            ftp.makeDirectory("images");
            //切换ftp默认文件夹
            ftp.changeWorkingDirectory("images");

            //将本地图片上传到ftp服务器上
            ftp.storeFile(file.getOriginalFilename(), inputStream);
            //退出登录
            ftp.logout();
            jsonResult.setCode(0);
            jsonResult.setMsg("http://localhost/images/"+file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }

}
