package kr.co.itpaper.mysqldocument.service;

import java.util.List;

import kr.co.itpaper.mysqldocument.model.TableName;
import kr.co.itpaper.mysqldocument.model.TableStruct;

public interface TableService {
	
	/**
	 * 테이블 목록을 리턴한다.
	 * @return
	 * @throws Exception
	 */
	public List<TableName> selectTableList() throws Exception;
	
	/**
	 * 주어진 테이블의 구조를 조회한다.
	 * @param tableName - 테이블이름
	 * @return
	 * @throws Exception
	 */
	public List<TableStruct> selectTableStruct(String tableName) throws Exception;
	
}
