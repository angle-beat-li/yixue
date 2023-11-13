package com.yixue.media.service;

import com.yixue.base.mode.PageParams;
import com.yixue.base.mode.PageResult;
import com.yixue.media.mode.dto.UploadFileParamsDto;
import com.yixue.media.mode.dto.UploadFileResultDto;
import com.yixue.media.mode.po.MediaFiles;
import com.yixue.media.mode.dto.QueryMediaParamsDto;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.yixue.base.model.PageResult<com.yixue.media.model.po.MediaFiles>
 */
 public PageResult<MediaFiles> queryMediaFiels(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);
 /**
  * 上传文件
  * @param companyId 机构id
  * @param uploadFileParamsDto 上传文件信息
  * @param localFilePath 文件磁盘路径
  * @return 文件信息
  */
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);


}
