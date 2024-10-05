package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.MemberClass;

import java.util.List;

public interface MemberClassService extends IService<MemberClass> {
    List<MemberClass> getAll();

    void saveData(MemberClass memberClass);

    void voidClass(Long id);

    public void update(MemberClass memberClass);

}
