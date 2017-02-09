package com.zsorg.neteasecloudmusic.models;

import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public interface IConfigModel {
    List<ConfigBean> getConfigList();
}
