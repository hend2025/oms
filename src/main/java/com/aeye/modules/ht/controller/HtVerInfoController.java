package com.aeye.modules.ht.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.collection.CollUtil;
import com.aeye.common.utils.*;
import com.aeye.modules.ht.dto.HtVerRcdDTO;
import com.aeye.modules.ht.dto.HtVerTreeDTO;
import com.aeye.modules.ht.dto.SendEmailDTO;
import com.aeye.modules.ht.entity.*;
import com.aeye.modules.ht.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.dto.HtVerInfoDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;

@RestController
@RequestMapping(value = "/web/ht/verinfo")
@Api(description="HtVerInfo", tags = "版本信息")
public class HtVerInfoController extends AeyeAbstractController{

    @Autowired
    private HtVerInfoService htVerInfoService;

    @Autowired
    private HtVerRelaService htVerRelaService;

    @Autowired
    private HtAppService htAppService;

    @Autowired
    private HtVerContService htVerContService;

    @Autowired
    private HtConfigService htConfigService;

    @Autowired
    private HtJobsService htJobsService;

    @Autowired
    private HtMenuService htMenuService;

    @Autowired
    private HtSqlService htSqlService;

    @Autowired
    private HtProductService htProductService;

    @Autowired
    private HtProjectService htProjectService;

    @Autowired
    private HtFileService htFileService;

    public static List<HtVerTreeDTO> listToTree(List<HtVerTreeDTO> nodes) {
        Map<String, HtVerTreeDTO> nodeMap = new HashMap<>();
        for (HtVerTreeDTO node : nodes) {
            nodeMap.put(node.getItemCode(), node);
        }
        List<HtVerTreeDTO> rootNodes = new ArrayList<>();
        for (HtVerTreeDTO node : nodes) {
            HtVerTreeDTO parent = nodeMap.get(node.getParentCode());
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    @RequestMapping(value = "/verTree",method = {RequestMethod.POST})
    @ApiOperation(value = "版本树")
    public WrapperResponse<List<HtVerTreeDTO>> verTree() throws Exception{
        List<HtVerTreeDTO> tree = new ArrayList<>();
        List<HtProductDO> list1 = htProductService.list(new LambdaQueryWrapper<>());
        HtVerTreeDTO menu10 = null;
        for(HtProductDO bean : list1){
            HtVerTreeDTO menu = new HtVerTreeDTO();
            menu.setItemCode(bean.getProductId());
            menu.setItemName(bean.getProductName());
            menu.setParentCode("0");
            menu.setItemLevel(1);
            if (bean.getProductId().equals("10")){
                menu10 = menu;
            }else{
                tree.add(menu);
            }
        }
        if(menu10!=null){
            tree.add(menu10);
        }

        List<HtProjectDO> list2 = htProjectService.list(new LambdaQueryWrapper<>());
        for(HtProjectDO bean : list2){
            HtVerTreeDTO menu = new HtVerTreeDTO();
            menu.setItemCode(bean.getProjectId());
            menu.setItemName(bean.getProjectName());
            menu.setParentCode(bean.getProductId());
            menu.setItemLevel(2);
            tree.add(menu);
        }

        List<HtVerInfoDO> list3 = htVerInfoService.list(new LambdaQueryWrapper<HtVerInfoDO>().orderByDesc(HtVerInfoDO::getVerId));
        for(HtVerInfoDO bean : list3){
            HtVerTreeDTO menu = new HtVerTreeDTO();
            menu.setItemCode(bean.getVerId());
            menu.setItemName(bean.getFirstVerNo());
            menu.setParentCode(bean.getProjectId());
            menu.setItemLevel(3);
            tree.add(menu);
        }

        return (WrapperResponse)WrapperResponse.success(listToTree(tree));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtVerInfoDTO>> list(HtVerInfoDTO params) throws Exception{
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        IPage<HtVerInfoDO> page = htVerInfoService.page(
                new Query<HtVerInfoDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtVerInfoDO>()
                    .like(StringUtils.isNotBlank(params.getKeyWord()), HtVerInfoDO::getFirstVerNo,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()), HtVerInfoDO::getFirstVerNo,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()), HtVerInfoDO::getVerContent,params.getKeyWord())
                    .eq(StringUtils.isNotBlank(params.getProductId()), HtVerInfoDO::getProductId,params.getProductId())
                    .eq(StringUtils.isNotBlank(params.getProjectId()), HtVerInfoDO::getProjectId,params.getProjectId())
                    .orderByDesc(HtVerInfoDO::getCrteTime).orderByDesc(HtVerInfoDO::getVerId)
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{verId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtVerInfoDTO> info(@PathVariable("verId") Long verId) throws Exception{
        HtVerInfoDO htVerInfo = htVerInfoService.getById(verId);
        return (WrapperResponse)WrapperResponse.success(htVerInfo);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtVerInfoDTO> save(HtVerInfoDTO htVerInfo) throws Exception{
        List<HtVerInfoDO> list = htVerInfoService.list(new LambdaQueryWrapper<HtVerInfoDO>()
                .select(HtVerInfoDO::getVerId,HtVerInfoDO::getFirstVerNo,HtVerInfoDO::getSecondVerNo)
                .eq(HtVerInfoDO::getProjectId,htVerInfo.getProjectId())
                .and(t->t.eq(HtVerInfoDO::getFirstVerNo,htVerInfo.getFirstVerNo()).or().eq(HtVerInfoDO::getSecondVerNo,htVerInfo.getSecondVerNo()))
        );
        long cnt1 = list.stream().filter(e -> e.getFirstVerNo().equals(htVerInfo.getFirstVerNo())  && !e.getVerId().equals(htVerInfo.getVerId())).count();
        long cnt2 = list.stream().filter(e -> e.getSecondVerNo().equals(htVerInfo.getSecondVerNo()) && !e.getVerId().equals(htVerInfo.getVerId())).count();
        if(cnt1 > 0){
            return WrapperResponse.fail("迭代版本号【"+htVerInfo.getFirstVerNo()+"】存在！",null);
        }
        if(cnt2 > 0){
            return WrapperResponse.fail("归档版本号【"+htVerInfo.getSecondVerNo()+"】存在！",null);
        }
        HtVerInfoDO bean = new HtVerInfoDO();
        bean.setVerId(htVerInfo.getVerId());
        bean.setFirstVerNo(htVerInfo.getFirstVerNo());
        bean.setSecondVerNo(htVerInfo.getSecondVerNo());
        bean.setVerStatus(htVerInfo.getVerStatus());
        bean.setVerContent(htVerInfo.getVerContent());
        if (StringUtils.isNotBlank(bean.getVerId())){
            htVerInfoService.updateById(bean);
        }else{
            bean.setVerId(htVerInfo.getProjectId()+"000100");
            HtVerInfoDO temp = htVerInfoService.getOne(
                    new LambdaQueryWrapper<HtVerInfoDO>().select(HtVerInfoDO::getVerId)
                            .eq(HtVerInfoDO::getProjectId,htVerInfo.getProjectId())
                            .orderByDesc(HtVerInfoDO::getVerId).last("limit 1"));
            if(temp != null){
                bean.setVerId(String.valueOf(Long.parseLong(temp.getVerId().substring(0,9))+1)+"00");
            }
            bean.setReleaseTime(new Date());
            bean.setProjectId(htVerInfo.getProjectId());
            bean.setProductId(htVerInfo.getProjectId().substring(0,2));
            htVerInfoService.save(bean);
        }
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/verCopy",method = {RequestMethod.POST})
    @ApiOperation(value = "克隆")
    public WrapperResponse<HtVerInfoDTO> verCopy(HtVerInfoDTO htVerInfo) throws Exception{
        List<HtVerInfoDO> list = htVerInfoService.list(new LambdaQueryWrapper<HtVerInfoDO>()
                .select(HtVerInfoDO::getVerId,HtVerInfoDO::getFirstVerNo,HtVerInfoDO::getSecondVerNo)
                .eq(HtVerInfoDO::getProjectId,htVerInfo.getProjectId())
                .and(t->t.eq(HtVerInfoDO::getFirstVerNo,htVerInfo.getFirstVerNo()).or().eq(HtVerInfoDO::getSecondVerNo,htVerInfo.getSecondVerNo()))
        );
        long cnt1 = list.stream().filter(e -> e.getFirstVerNo().equals(htVerInfo.getFirstVerNo())).count();
        long cnt2 = list.stream().filter(e -> e.getSecondVerNo().equals(htVerInfo.getSecondVerNo())).count();
        if(cnt1 > 0){
            return WrapperResponse.fail("迭代版本号【"+htVerInfo.getFirstVerNo()+"】存在！",null);
        }
        if(cnt2 > 0){
            return WrapperResponse.fail("归档版本号【"+htVerInfo.getSecondVerNo()+"】存在！",null);
        }

        HtVerInfoDO bean = new HtVerInfoDO();
        bean.setVerId(String.valueOf(Long.parseLong(htVerInfo.getVerId())+1));
        bean.setFirstVerNo(htVerInfo.getFirstVerNo());
        bean.setSecondVerNo(htVerInfo.getSecondVerNo());
        bean.setVerContent(htVerInfo.getVerContent());
        bean.setProjectId(htVerInfo.getProjectId());
        bean.setProductId(htVerInfo.getProjectId().substring(0,2));
        bean.setReleaseTime(new Date());
        bean.setVerStatus(htVerInfo.getVerStatus());
        htVerInfoService.save(bean);

        List<HtVerRelaDO> list1 = htVerRelaService.list(new LambdaQueryWrapper<HtVerRelaDO>().eq(HtVerRelaDO::getVerId,htVerInfo.getVerId()));
        list1.stream().forEach(e ->{e.setVerId(bean.getVerId());e.setVerRelaId(null);});
        htVerRelaService.saveBatch(list1);

        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{verId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("verId") Long verId) throws Exception{
        htVerInfoService.removeById(verId);
        htVerRelaService.remove(new LambdaQueryWrapper<HtVerRelaDO>().eq(HtVerRelaDO::getVerId,verId));
        htVerContService.remove(new LambdaQueryWrapper<HtVerContDO>().eq(HtVerContDO::getVerId,verId));
        htConfigService.remove(new LambdaQueryWrapper<HtConfigDO>().eq(HtConfigDO::getVerId,verId));
        htJobsService.remove(new LambdaQueryWrapper<HtJobsDO>().eq(HtJobsDO::getVerId,verId));
        htMenuService.remove(new LambdaQueryWrapper<HtMenuDO>().eq(HtMenuDO::getVerId,verId));
        htSqlService.remove(new LambdaQueryWrapper<HtScriptDO>().eq(HtScriptDO::getVerId,verId));
        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/reset/{verId}", method = {RequestMethod.GET})
    @ApiOperation(value = "重置")
    public WrapperResponse<HtAppDO> reset(@PathVariable("verId") String verId) throws Exception{
        HtVerInfoDO htVerInfo = htVerInfoService.getById(verId);
        if(htVerInfo==null){
            return  WrapperResponse.fail("版本不存在！",null);
        }
        htVerRelaService.remove(new LambdaQueryWrapper<HtVerRelaDO>().eq(HtVerRelaDO::getVerId,verId));
        List<HtAppDO> list = htAppService.getAppVerInfo(htVerInfo.getProjectId());
        list.forEach(item->{
            HtVerRelaDO htVerRela = new HtVerRelaDO();
            htVerRela.setVerId(verId);
            htVerRela.setAppCode(item.getAppCode());
            htVerRela.setProductId(htVerInfo.getProductId());
            htVerRela.setProjectId(htVerInfo.getProjectId());
            htVerRela.setFirstVerNo(item.getFirstVerNo());
            htVerRela.setSecondVerNo(item.getSecondVerNo());
            htVerRela.setRemarks(item.getRemarks());
            htVerRela.setOrderNum(item.getOrderNum());
            htVerRelaService.save(htVerRela);
        });
        return  (WrapperResponse)WrapperResponse.success(null);
    }

    @ApiOperation(value = "下载版本升级说明")
    @RequestMapping(value = "/downVerFile/{verId}", method = {RequestMethod.GET}, headers = "Accept=application/octet-stream")
    public void downVerFile(@PathVariable("verId") String verId) throws Exception{
        HtVerInfoDO verInfo = htVerInfoService.getById(verId);
        if(verInfo==null){
            WrapperResponse.fail("版本不存在！",null);
        }
        String pjName = htProjectService.getById(verInfo.getProjectId()).getProjectName();
        Map<String, ByteArrayOutputStream> fileMap = new LinkedHashMap<>();
        this.createExcel(verInfo, fileMap);
        this.createScript(verInfo,fileMap);
        String fileName = URLEncoder.encode( pjName+"-"+verInfo.getFirstVerNo()+"-版本升级说明.zip", "UTF-8");
        this.outZipFile(fileMap,fileName);
    }

    private void createExcel(HtVerInfoDO verInfo, Map<String, ByteArrayOutputStream> fileMap) throws IOException {
        String  fileName = "excleTemplate/版本升级说明.xlsx";
        InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream(fileName);
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
        this.createRela(verInfo,xwb);
        this.createVerContent(verInfo,xwb);
        this.createVerConfig(verInfo,xwb);
        this.createVerMenu(verInfo,xwb);
        this.createVerJob(verInfo,xwb);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xwb.write(byteArrayOutputStream);
        fileMap.put("版本升级说明.xlsx",byteArrayOutputStream);
        if (xwb != null) {
            xwb.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private void createScript(HtVerInfoDO verInfo, Map<String, ByteArrayOutputStream> fileMap) throws IOException {
        List<HtScriptDO> list = htSqlService.list(
                new LambdaQueryWrapper<HtScriptDO>()
                        .le(HtScriptDO::getVerId,verInfo.getVerId())
                        .eq(HtScriptDO::getProjectId,verInfo.getProjectId())
                        .orderByAsc(HtScriptDO::getLangType).orderByAsc(HtScriptDO::getVerId)
                        .orderByAsc(HtScriptDO::getInputMode).orderByAsc(HtScriptDO::getCodeType).orderByAsc(HtScriptDO::getScriptId)
        );
        for (HtScriptDO bean : list) {
            HtVerInfoDO verInfoDO = htVerInfoService.getById(bean.getVerId());
//            String firstVerNo = StringUtils.rightPad(verInfoDO.getFirstVerNo().replace(".","").replace("_","").replace("-",""),9,"0");
            String firstVerNo = verInfoDO.getFirstVerNo();
            String s = bean.getLangType() + "/" + firstVerNo;
            long   cnt = fileMap.keySet().stream().filter(e -> e.contains(s)).count();
            String ext = bean.getLangType().equals("python") ? ".py" : ".sql";
            String fileName = s + "_" + (cnt+1) + ext;
            byte[] bytes = null;
            if (bean.getInputMode().equals("1")){
                bytes = bean.getScriptText().getBytes();
            }else{
                bytes = htFileService.getById(bean.getFileKey()).getFileBlob();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(bytes);
            fileMap.put(fileName,byteArrayOutputStream);
        }

    }


    private void outZipFile(Map<String, ByteArrayOutputStream> fileMap,String fileName) throws Exception {
        response.reset();
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);
        for (String key : fileMap.keySet()) {
            zipOutputStream.putNextEntry(new ZipEntry(key));
            ByteArrayOutputStream byteArrayOutputStream = fileMap.get(key);
            zipOutputStream.write(byteArrayOutputStream.toByteArray());
            zipOutputStream.closeEntry();
        }
        zipOutputStream.flush();
        if (zipOutputStream!= null){
            zipOutputStream.closeEntry();
            IOUtils.closeQuietly(zipOutputStream);
        }
        if (out!= null){
            IOUtils.closeQuietly(out);
        }
    }

    private void createRela(HtVerInfoDO htVerInfo,XSSFWorkbook xwb) throws IOException {
        MPJLambdaWrapper<HtVerRelaDO> wrapper = new MPJLambdaWrapper<HtVerRelaDO>();
        wrapper.selectAll(HtVerRelaDO.class);
        wrapper.leftJoin(HtAppDO.class,HtAppDO::getAppCode,HtVerRelaDO::getAppCode);
        wrapper.eq(HtVerRelaDO::getVerId,htVerInfo.getVerId()).orderByAsc(HtAppDO::getAppType).orderByAsc(HtAppDO::getOrderNum);
        List<HtVerRelaDO> list =  htVerRelaService.list(wrapper);
        if(CollUtil.isEmpty(list)){
            return;
        }

        XSSFSheet sheet = xwb.getSheetAt(0);
        String firstVerNo = htVerInfo.getFirstVerNo();
        sheet.getRow(1).getCell(2).setCellValue(firstVerNo) ;
        sheet.getRow(1).getCell(5).setCellValue(htVerInfo.getSecondVerNo()) ;
        List<CellStyle> styleList = new ArrayList<>();
        styleList.add(null);
        styleList.add(sheet.getRow(4).getCell(1).getCellStyle());
        styleList.add(sheet.getRow(4).getCell(2).getCellStyle());
        styleList.add(sheet.getRow(4).getCell(3).getCellStyle());
        styleList.add(sheet.getRow(4).getCell(4).getCellStyle());
        styleList.add(sheet.getRow(4).getCell(5).getCellStyle());

        for (int i = 0; i < list.size(); i++) {
            HtVerRelaDO bean = list.get(i);
            String remarks = StringUtils.defaultIfEmpty(bean.getRemarks()," ");
            int line1 = remarks.split("\n").length;
            int line2 = (int)Math.ceil(remarks.length()/100.0);
            short height = (short)(600*(line1>line2?line1:line2));
            XSSFRow row = sheet.createRow(4+i);
            row.setHeight(height);

            XSSFCell cell = row.createCell(1);
            cell.setCellStyle(styleList.get(1));
            cell.setCellValue(i+1);

            cell = row.createCell(2);
            cell.setCellStyle(styleList.get(2));
            cell.setCellValue(bean.getAppCode());

            cell = row.createCell(3);
            cell.setCellStyle(styleList.get(3));
            cell.setCellValue(htAppService.getById(bean.getAppCode()).getAppName());

            cell = row.createCell(4);
            cell.setCellStyle(styleList.get(4));
            cell.setCellValue(bean.getFirstVerNo());

            cell = row.createCell(5);
            cell.setCellStyle(styleList.get(5));
            cell.setCellValue(remarks);
        }
    }

    private void createVerContent(HtVerInfoDO htVerInfo,XSSFWorkbook xwb) throws IOException {
        List<HtVerContDO> list = htVerContService.list(new LambdaQueryWrapper<HtVerContDO>()
                .le(HtVerContDO::getVerId,htVerInfo.getVerId()).eq(HtVerContDO::getProjectId,htVerInfo.getProjectId())
                .orderByDesc(HtVerContDO::getVerId).orderByAsc(HtVerContDO::getCrteTime)
        );
        if(CollUtil.isEmpty(list)){
            return;
        }

        XSSFSheet sheet = xwb.getSheetAt(4);
        List<CellStyle> styleList = new ArrayList<>();
        styleList.add(null);
        styleList.add(sheet.getRow(2).getCell(1).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(2).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(3).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(4).getCellStyle());

        for (int i = 0; i < list.size(); i++) {
            HtVerContDO bean = list.get(i);
            int line1 = bean.getChangeText().split("\n").length;
            int line2 = (int)Math.ceil(bean.getChangeText().length()/40.0);
            short height = (short)(420*(line1>line2?line1:line2));
            XSSFRow row = sheet.createRow(2+i);
            row.setHeight(height);

            XSSFCell cell = row.createCell(1);
            cell.setCellStyle(styleList.get(1));
            String firstVerNo = DicCacheUtil.getDicName("DIC_VER",bean.getVerId());
            cell.setCellValue(firstVerNo==null?"初始版本":firstVerNo) ;

            cell = row.createCell(2);
            cell.setCellStyle(styleList.get(2));
            cell.setCellValue(bean.getModuleName()) ;

            cell = row.createCell(3);
            cell.setCellStyle(styleList.get(3));
            cell.setCellValue(bean.getChangeText()) ;

            cell = row.createCell(4);
            cell.setCellStyle(styleList.get(4));
            cell.setCellValue(bean.getRemarks()) ;
        }
    }


    private void createVerMenu(HtVerInfoDO htVerInfo,XSSFWorkbook xwb) throws IOException {
        List<HtMenuDO> list = htMenuService.list(new LambdaQueryWrapper<HtMenuDO>()
                .le(HtMenuDO::getVerId,htVerInfo.getVerId()).eq(HtMenuDO::getProjectId,htVerInfo.getProjectId())
                .orderByDesc(HtMenuDO::getVerId).orderByAsc(HtMenuDO::getCrteTime)
        );
        if(CollUtil.isEmpty(list)){
            return;
        }

        XSSFSheet sheet = xwb.getSheetAt(1);
        List<CellStyle> styleList = new ArrayList<>();
        styleList.add(null);
        styleList.add(sheet.getRow(2).getCell(1).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(2).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(3).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(4).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(5).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(6).getCellStyle());

        for (int i = 0; i < list.size(); i++) {
            HtMenuDO bean = list.get(i);
            XSSFRow row = sheet.createRow(2+i);
            row.setHeight((short)650);

            XSSFCell cell = row.createCell(1);
            cell.setCellStyle(styleList.get(1));
            String firstVerNo = DicCacheUtil.getDicName("DIC_VER",bean.getVerId());
            cell.setCellValue(firstVerNo==null?"初始版本":firstVerNo) ;

            cell = row.createCell(2);
            cell.setCellStyle(styleList.get(2));
            cell.setCellValue(bean.getModuleName()) ;

            cell = row.createCell(3);
            cell.setCellStyle(styleList.get(3));
            cell.setCellValue(bean.getMenuName()) ;

            cell = row.createCell(4);
            cell.setCellStyle(styleList.get(4));
            cell.setCellValue(bean.getParentName()) ;

            cell = row.createCell(5);
            cell.setCellStyle(styleList.get(5));
            cell.setCellValue(bean.getMenuUrl()) ;

            cell = row.createCell(6);
            cell.setCellStyle(styleList.get(6));
            cell.setCellValue(bean.getRemarks()) ;

        }
    }

    private void createVerConfig(HtVerInfoDO htVerInfo,XSSFWorkbook xwb) throws IOException {
        List<HtConfigDO> list = htConfigService.list(
                new LambdaQueryWrapper<HtConfigDO>()
                        .le(HtConfigDO::getVerId,htVerInfo.getVerId()).eq(HtConfigDO::getProjectId,htVerInfo.getProjectId())
                        .orderByDesc(HtConfigDO::getVerId).orderByAsc(HtConfigDO::getConfigId)
        );
        if(CollUtil.isEmpty(list)){
            return;
        }

        XSSFSheet sheet = xwb.getSheetAt(2);
        List<CellStyle> styleList = new ArrayList<>();
        styleList.add(null);
        styleList.add(sheet.getRow(2).getCell(1).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(2).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(3).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(4).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(5).getCellStyle());

        for (int i = 0; i < list.size(); i++) {
            HtConfigDO bean = list.get(i);
            int line1 = bean.getYamlText().split("\n").length;
            int line2 = (int)Math.ceil(bean.getYamlText().length()/40.0);
            short height = (short)(400*(line1>line2?line1:line2));
            XSSFRow row = sheet.createRow(2+i);
            row.setHeight(height);

            XSSFCell cell = row.createCell(1);
            cell.setCellStyle(styleList.get(1));
            String firstVerNo = DicCacheUtil.getDicName("DIC_VER",bean.getVerId());
            cell.setCellValue(firstVerNo==null?"初始版本":firstVerNo) ;

            cell = row.createCell(2);
            cell.setCellStyle(styleList.get(2));
            cell.setCellValue(bean.getOperName()) ;

            cell = row.createCell(3);
            cell.setCellStyle(styleList.get(3));
            cell.setCellValue(bean.getServiceName()) ;

            cell = row.createCell(4);
            cell.setCellStyle(styleList.get(4));
            cell.setCellValue(bean.getYamlText()) ;

            cell = row.createCell(5);
            cell.setCellStyle(styleList.get(5));
            cell.setCellValue(bean.getRemarks()) ;
        }
    }

    private void createVerJob(HtVerInfoDO htVerInfo,XSSFWorkbook xwb) throws IOException {
        List<HtJobsDO> list = htJobsService.list(
                new LambdaQueryWrapper<HtJobsDO>()
                        .le(HtJobsDO::getVerId,htVerInfo.getVerId()).eq(HtJobsDO::getProjectId,htVerInfo.getProjectId())
                        .orderByDesc(HtJobsDO::getVerId).orderByAsc(HtJobsDO::getJobId)
        );
        if(CollUtil.isEmpty(list)){
            return;
        }

        XSSFSheet sheet = xwb.getSheetAt(3);
        List<CellStyle> styleList = new ArrayList<>();
        styleList.add(null);
        styleList.add(sheet.getRow(2).getCell(1).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(2).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(3).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(4).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(5).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(6).getCellStyle());
        styleList.add(sheet.getRow(2).getCell(7).getCellStyle());

        for (int i = 0; i < list.size(); i++) {
            HtJobsDO bean = list.get(i);
            XSSFRow row = sheet.createRow(2+i);
            row.setHeight((short)650);

            XSSFCell cell = row.createCell(1);
            cell.setCellStyle(styleList.get(1));
            String firstVerNo = DicCacheUtil.getDicName("DIC_VER",bean.getVerId());
            cell.setCellValue(firstVerNo==null?"初始版本":firstVerNo) ;

            cell = row.createCell(2);
            cell.setCellStyle(styleList.get(2));
            cell.setCellValue(bean.getServiceName()) ;

            cell = row.createCell(3);
            cell.setCellStyle(styleList.get(3));
            cell.setCellValue(bean.getJobName()) ;

            cell = row.createCell(4);
            cell.setCellStyle(styleList.get(4));
            cell.setCellValue(bean.getJobDesc()) ;

            cell = row.createCell(5);
            cell.setCellStyle(styleList.get(5));
            cell.setCellValue(bean.getJobCron()) ;

            cell = row.createCell(6);
            cell.setCellStyle(styleList.get(6));
            cell.setCellValue(bean.getAuthor()) ;

            cell = row.createCell(7);
            cell.setCellStyle(styleList.get(7));
            cell.setCellValue(StringUtils.defaultIfEmpty(bean.getExecutorParam(),""));
        }
    }

    @PostMapping("/sendEmail")
    @ApiOperation("发送邮件")
    public WrapperResponse sendEmail(SendEmailDTO emailDTO) throws Exception{
        String[] ccName = null,ccEmail = null;
        String[] toName = emailDTO.getToName().split(",");
        String[] toEmail = emailDTO.getToEmail().split(",");
        if(StringUtils.isNotBlank(emailDTO.getCcName())&& StringUtils.isNotBlank(emailDTO.getCcEmail())){
            ccName = emailDTO.getCcName().split(",");
            ccEmail = emailDTO.getCcEmail().split(",");
        }
        HtVerInfoDO verInfo = htVerInfoService.getById(emailDTO.getVerId());
        if(verInfo==null){
            WrapperResponse.fail("版本不存在！",null);
        }

        Map<String, ByteArrayOutputStream> fileMap = new LinkedHashMap<>();
        this.createExcel(verInfo, fileMap);
        this.createScript(verInfo,fileMap);

        String projectName = DicCacheUtil.getDicName("DIC_PROJECT",verInfo.getProjectId());
        String fileName = projectName+verInfo.getFirstVerNo()+"升级说明.zip";
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        OutputStream out = new FileOutputStream(file);
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);
        for (String key : fileMap.keySet()) {
            zipOutputStream.putNextEntry(new ZipEntry(key));
            ByteArrayOutputStream byteArrayOutputStream = fileMap.get(key);
            zipOutputStream.write(byteArrayOutputStream.toByteArray());
            zipOutputStream.closeEntry();
        }
        zipOutputStream.flush();
        if (zipOutputStream!= null){
            zipOutputStream.closeEntry();
            IOUtils.closeQuietly(zipOutputStream);
        }
        if (out!= null){
            IOUtils.closeQuietly(out);
        }

        String content = "<div><font size='3'> "
                + emailDTO.getContent().replaceAll("\n","<br>").replaceAll(" ","&nbsp;")
//                + toName[0]+ "，你好！<br><br> "
//                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
//                + projectName+verInfo.getFirstVerNo()+"测试已完成，SVN归档版本号【"+verInfo.getSecondVerNo()+"】"
//                + "<br><br>"
//                + "更新内容：" + verInfo.getVerContent()
//                + "<br><br>"
//                + "更多内容，通过公司内网地址【http://192.168.10.152:7010】查看！"
                + "<br><br>"
                + "-----------------------------------------------------------<br> "
                + "<img src='cid:aeyelog.png'><br> "
                + "贺年冬  医保事业部<br> "
                + "智慧眼科技股份有限公司<br> "
                + "手机：19918988761<br> "
                + "邮箱：heniandong@a-eye.cn<br> "
                + "网址：www.a-eye.cn<br> "
                + "地址：湖南省长沙市高新区尖山路39号中电软件园14<br>"
                + "</font></div>"
                ;
        MailUtil.sendMsg(toEmail,toName,ccEmail,ccName,emailDTO.getPwd(),emailDTO.getSubject(),content,file);
        return WrapperResponse.success(null);
    }

}
