package com.yixue.media.service;

import com.yixue.base.mode.PageParams;
import com.yixue.base.mode.PageResult;
import com.yixue.base.mode.RestResponse;
import com.yixue.media.mode.dto.UploadFileParamsDto;
import com.yixue.media.mode.dto.UploadFileResultDto;
import com.yixue.media.mode.po.MediaFiles;
import com.yixue.media.mode.dto.QueryMediaParamsDto;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService {

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.yixue.base.model.PageResult<com.yixue.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 上传文件信息
     * @param localFilePath       文件磁盘路径
     * @return 文件信息
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    /**
     * @param fileMd5 文件的md5
     * @return com.yixue.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查文件是否存在
     */
    RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @param fileMd5    文件的md5
     * @param chunkIndex 分块序号
     * @return com.yixue.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查分块是否存在
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * @description 上传分块
     * @param fileMd5  文件md5
     * @param chunk  分块序号
     * @param localFilePath  暂存文件路径
     * @return com.yixue.base.model.RestResponse
     */
    RestResponse uploadChunk(String fileMd5,int chunk,String localFilePath);

    /**
     * @description 合并分块
     * @param companyId  机构id
     * @param fileMd5  文件md5
     * @param chunkTotal 分块总和
     * @param uploadFileParamsDto 文件信息
     * @return com.yixue.base.model.RestResponse
     */
    RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);


}
