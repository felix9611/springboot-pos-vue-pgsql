package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.MemberSpecialDay;
import com.fixedasset.mapper.MemberSpecialDayMapper;
import com.fixedasset.service.MemberSpecialDaysService;
import org.springframework.stereotype.Service;

@Service
public class MemberSpecialDaysServiceImpl extends ServiceImpl<MemberSpecialDayMapper, MemberSpecialDay> implements MemberSpecialDaysService {
}
