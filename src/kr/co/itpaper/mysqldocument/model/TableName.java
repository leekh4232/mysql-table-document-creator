package kr.co.itpaper.mysqldocument.model;

import java.util.List;

public class TableName {
	// 테이블이름
	private String tableName;
	// 테이블 주석
	private String comment;
	// 테이블 구조
	private List<TableStruct> struct;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<TableStruct> getStruct() {
		return struct;
	}

	public void setStruct(List<TableStruct> struct) {
		this.struct = struct;
	}

	@Override
	public String toString() {
		return "TableName [tableName=" + tableName + ", comment=" + comment + ", struct=" + struct + "]";
	}

}
