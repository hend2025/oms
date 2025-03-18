package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtFileDTO;
import com.aeye.modules.ht.entity.HtFileDO;
import com.aeye.modules.ht.service.HtFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/web/ht/File")
@Api(description="File", tags = "文件服务")
public class HtFileController extends AeyeAbstractController {

    @Autowired
    private HtFileService htFileService;

    @ResponseBody
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/uploadFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public WrapperResponse<HtFileDTO>  uploadFile(MultipartFile upfile) throws Exception {
        HtFileDO fileDO = new HtFileDO();
        fileDO.setFileBlob(toByteArray(upfile.getInputStream()));
        fileDO.setFileName(upfile.getOriginalFilename());
        fileDO.setFileType(fileDO.getFileName().substring(fileDO.getFileName().lastIndexOf(".")+1));
        htFileService.save(fileDO);

        HtFileDTO bean = new HtFileDTO();
        bean.setFileKey(fileDO.getFileKey());
        bean.setFileName(fileDO.getFileName());
        bean.setFileType(fileDO.getFileType());
        return WrapperResponse.success(bean);
    }


    @ApiOperation(value = "文件下载")
    @RequestMapping(value = "/downFile/{fileKey}", method = {RequestMethod.GET}, headers = "Accept=application/octet-stream")
    public void downFile(@PathVariable("fileKey") Integer fileKey, HttpServletResponse response) {
        try {
            HtFileDO fileDO = htFileService.getById(fileKey);
            if (fileDO != null) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileDO.getFileName(), "UTF-8"));
                OutputStream outs = response.getOutputStream();
                outs.write(fileDO.getFileBlob());
                outs.flush();
            } else {
                log.error("模板 [{}] 不存在", fileKey);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


}
