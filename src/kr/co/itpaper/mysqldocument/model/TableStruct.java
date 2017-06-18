package kr.co.itpaper.mysqldocument.model;

public class TableStruct {
	// 일련번호
	private int id;
	// 컬럼명
	private String fieldName;
	// 데이터 타입
	private String dataType;
	// 널 허용 여부
	private String isNull;
	// 제약조건
	private String key;
	// 기타 속성
	private String extra;
	// 기본값
	private String defaultValue;
	// 주석
	private String comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "TableDesc [id=" + id + ", fieldName=" + fieldName + ", dataType=" + dataType + ", isNull=" + isNull
				+ ", key=" + key + ", extra=" + extra + ", defaultValue=" + defaultValue + ", comment=" + comment + "]";
	}

}
