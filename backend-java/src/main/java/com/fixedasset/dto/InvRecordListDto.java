package com.fixedasset.dto;

import com.fixedasset.entity.InvRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvRecordListDto extends InvRecord {
    @Transient private String productCode;

    @Transient private String productName;

    @Transient private String placeFromCode;

    @Transient private String placeFromName;

    @Transient private String placeToCode;

    @Transient private String placeToName;
}
