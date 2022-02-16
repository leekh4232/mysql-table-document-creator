package kr.co.itpaper.mysqldocument.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import kr.co.itpaper.mysqldocument.model.TableName;
import kr.co.itpaper.mysqldocument.model.TableStruct;
import kr.co.itpaper.mysqldocument.service.TableService;

public class TableServiceImpl implements TableService {

	/** MyBatis */
	// --> import org.apache.ibatis.session.SqlSession
	SqlSession sqlSession;

	/** 생성자를 통한 객체 생성 */
	public TableServiceImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<TableName> selectTableList() throws Exception {
		List<TableName> result = null;

		try {
			result = sqlSession.selectList("TableMapper.selectTableList");
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 테이블 목록이 없습니다.");
		} catch (Exception e) {
		    e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
			throw new Exception("테이블 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<TableStruct> selectTableStruct(String tableName) throws Exception {
		List<TableStruct> result = null;

		try {
			result = sqlSession.selectList("TableMapper.selectTableStruct", tableName);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception(tableName + "(은)는 존재하지 않는 테이블입니다.");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			throw new Exception(tableName + "테이블 구조 조회에 실패했습니다.");
		}

		return result;
	}

}
