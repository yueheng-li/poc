package com.auto.myte.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.auto.myte.entity.ReceiptInfo;

public interface ReceiptInfoMapper {
	List<ReceiptInfo> selectAllByEid(@Param("eid")String eid);
	List<ReceiptInfo> selectAllByEid(@Param("eid")String eid, RowBounds rowBounds);
	List<ReceiptInfo> selectAll();
	ReceiptInfo selectByPrimaryKey(@Param("id")String id);
	int insertReceiptInfo(ReceiptInfo receipt);
	int updateReceiptInfoByKey(ReceiptInfo receipt);
	int deleteByPrimaryKey(@Param("id")String id);
}