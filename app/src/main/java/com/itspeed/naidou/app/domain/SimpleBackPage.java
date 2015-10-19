/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itspeed.naidou.app.domain;

import com.itspeed.naidou.app.fragment.FollowFragment;
import com.itspeed.naidou.app.fragment.MyCollectFragment;
import com.itspeed.naidou.app.fragment.MyCookbookFragment;
import com.itspeed.naidou.app.fragment.detail.ChideDetailFragment;
import com.itspeed.naidou.app.fragment.detail.GuangdeDetailFragment;
import com.itspeed.naidou.app.fragment.detail.LiaodeDetailFragment;
import com.itspeed.naidou.app.fragment.setting.AboutFragment;
import com.itspeed.naidou.app.fragment.setting.EditInfoFragment;
import com.itspeed.naidou.app.fragment.setting.FeedBackFragment;
import com.itspeed.naidou.app.fragment.setting.ModifyPwdFragment;

/**
 * 返回页的基本信息注册 (其实就是将原本会在Manifest中注册的Activity转成Fragment在这里注册)
 * 
 * @author kymjs (https://www.kymjs.com/)
 * @since 2015-3
 */
public enum SimpleBackPage {


    EDITINFO(1, EditInfoFragment.class),
    MY_COOKBOOK(2, MyCookbookFragment.class),
    MY_COLLECT(3, MyCollectFragment.class),
    FOLLOW(4, FollowFragment.class),
    ABOUT(5, AboutFragment.class),
    FEED_BACK(6, FeedBackFragment.class),
    MODIFY_PWD(7, ModifyPwdFragment.class),
    LIAODE_DETAIL(8, LiaodeDetailFragment.class),
    CHIDE_DETAIL(9,ChideDetailFragment.class),
    GUANGDE_DETAIL(10,GuangdeDetailFragment.class);


    private Class<?> clazz;
    private int value;

    SimpleBackPage(int value, Class<?> cls) {
        this.clazz = cls;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static Class<?> getPageByValue(int value) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == value)
                return p.getClazz();
        }
        return null;
    }

}
