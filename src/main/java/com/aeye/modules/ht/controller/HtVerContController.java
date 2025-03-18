package com.aeye.modules.ht.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.AeyeBeanUtils;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.entity.HtVerInfoDO;
import com.aeye.modules.ht.service.HtVerContService;
import com.aeye.modules.ht.service.HtVerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtVerContDO;
import com.aeye.modules.ht.dto.HtVerContDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 版本更新说明
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/vercont")
@Api(description="HtVerCont", tags = "版本更新说明")
public class HtVerContController extends AeyeAbstractController{
    @Autowired
    private HtVerContService htVerContService;
    @Autowired
    private HtVerInfoService htVerInfoService;

    /**
     * 列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtVerContDTO>> list(HtVerContDTO params) throws Exception{
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        if(StringUtils.isNotBlank(params.getVerId())){
            params.setVerId(params.getVerId().substring(0,9));
        }
        IPage<HtVerContDO> page = htVerContService.page(
                new Query<HtVerContDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtVerContDO>()
                        .like(StringUtils.isNotBlank(params.getKeyWord()), HtVerContDO::getChangeText,params.getKeyWord())
                        .or()
                        .like(StringUtils.isNotBlank(params.getKeyWord()),HtVerContDO::getModuleName,params.getKeyWord())
                        .like(ObjectUtil.isNotEmpty(params.getVerId()), HtVerContDO::getVerId,params.getVerId())
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtVerContDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtVerContDO::getProductId,params.getProductId())
                        .orderBy(true,true,HtVerContDO::getVerId)
        );
        if(page.getRecords()!=null){
            page.getRecords().stream().forEach(e->{
                e.setVerStatus(htVerInfoService.getById(e.getVerId()).getVerStatus());
            });
        }
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{verDescId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtVerContDTO> info(@PathVariable("verDescId") Integer verDescId) throws Exception{
        HtVerContDO htVerCont = htVerContService.getById(verDescId);

        return (WrapperResponse)WrapperResponse.success(htVerCont);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtVerContDTO> save(HtVerContDTO htVerCont) throws Exception{
        htVerCont.setProductId(htVerCont.getVerId().substring(0,2));
        htVerCont.setProjectId(htVerCont.getVerId().substring(0,5));
        htVerContService.save(HtVerContDO.copyBean(htVerCont));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtVerContDTO> update(HtVerContDTO htVerCont) throws Exception{
        htVerCont.setProductId(htVerCont.getVerId().substring(0,2));
        htVerCont.setProjectId(htVerCont.getVerId().substring(0,5));
        htVerContService.updateById(HtVerContDO.copyBean(htVerCont));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] verDescIds) throws Exception{
        htVerContService.removeByIds(Arrays.asList(verDescIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{verDescId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("verDescId") Integer verDescId) throws Exception{
        htVerContService.removeById(verDescId);

        return WrapperResponse.success(null);
    }


    @ApiOperation(value = "下载模板")
    @RequestMapping(value = "/downVerRelaExcel", method = {RequestMethod.GET}, headers = "Accept=application/octet-stream")
    public void downVerRelaExcel(HttpServletRequest request, HttpServletResponse response) {
        OutputStream outs = null;
        BufferedInputStream inputStream = null;
        String  fileName = "/excleTemplate/迭代更新说明.xlsx";
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            if (resource.exists()) {
                outs = response.getOutputStream();
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                inputStream = new BufferedInputStream(resource.getInputStream());
                byte[] buffer = new byte[1024];
                while ((inputStream.read(buffer)) > 0) {
                    outs.write(buffer);
                    outs.flush();
                }
            } else {
                log.error("模板 [{}] 不存在", fileName);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public WrapperResponse upload(MultipartFile file) throws Exception {
        int iRow = 1;
        String errFile = null;
        boolean hasError = false;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<HtVerContDTO> list = new ArrayList<>();
            String[] label = new String[]{"序号", "所属迭代", "所属系统/模块", "更新内容", "是否新增功能"};
            for (Row row : sheet) {
                boolean ckeck = true;
                String[] rowData = new String[5];
                for (int i = 0; i < 5; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    rowData[i] = (cell == null) ? "" : cell.toString();
                    if (iRow == 1 && !rowData[i].equals(label[i])) {
                        return WrapperResponse.fail("Excel文件的格式不正确！", null);
                    } else {
                        if (StringUtils.isBlank(rowData[i])) {
                            hasError = true;
                            ckeck = false;
                            cell = row.createCell(5);
                            cell.setCellValue(label[i] + ",不能为空");
                        }
                    }
                }

                if (ckeck && iRow > 1) {
                    HtVerContDTO bean = new HtVerContDTO();
                    HtVerInfoDO verInfoDO = htVerInfoService.getOne(new LambdaQueryWrapper<HtVerInfoDO>().eq(HtVerInfoDO::getFirstVerNo, rowData[1]));
                    if (verInfoDO == null) {
                        hasError = true;
                        Cell cell = row.createCell(5);
                        cell.setCellValue("版本名称不存在！");
                    } else {
                        bean.setVerId(verInfoDO.getVerId());
                        bean.setProjectId(verInfoDO.getProjectId());
                        bean.setProductId(verInfoDO.getProductId());
                        bean.setModuleName(rowData[2]);
                        bean.setChangeText(rowData[3]);
                        String newFlag = (rowData[4].equals("是") ? "1" : "0");
                        bean.setNewFlag(newFlag);
                        list.add(bean);
                    }
                }
                iRow++;
            }
            htVerContService.saveBatch(AeyeBeanUtils.copyBeanList(list, HtVerContDO.class));
            if (hasError) {
                String appPath = System.getProperty("user.dir");
                if (appPath.endsWith(File.separator)) {
                    errFile = appPath + file.getOriginalFilename();
                } else {
                    errFile = appPath + File.separator + file.getOriginalFilename();
                }
                FileOutputStream outputStream = new FileOutputStream(errFile);
                workbook.write(outputStream);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hasError) {
            String s = errFile.substring(errFile.lastIndexOf(File.separator)+1);
            return WrapperResponse.success(s);
        } else {
            return WrapperResponse.success(null);
        }
    }


    @ApiOperation(value = "下载导入失败的文件")
    @RequestMapping(value = "/downErrFile/{fileName}", method = {RequestMethod.GET}, headers = "Accept=application/octet-stream")
    public void downErrFile(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outs = null;
        BufferedInputStream inputStream = null;
        try {
            String errFile = null;
            String appPath = System.getProperty("user.dir");
            if(appPath.endsWith(File.separator)){
                errFile = appPath + fileName;
            }else{
                errFile = appPath + File.separator + fileName;
            }
            File file = new File(errFile);
            if (file.exists()) {
                outs = response.getOutputStream();
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024];
                while ((inputStream.read(buffer)) > 0) {
                    outs.write(buffer);
                    outs.flush();
                }
            } else {
                log.error("模板 [{}] 不存在", fileName);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
