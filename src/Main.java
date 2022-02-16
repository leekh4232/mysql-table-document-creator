import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.itpaper.mysqldocument.dao.MyBatisConnectionFactory;
import kr.co.itpaper.mysqldocument.model.TableName;
import kr.co.itpaper.mysqldocument.model.TableStruct;
import kr.co.itpaper.mysqldocument.service.TableService;
import kr.co.itpaper.mysqldocument.service.impl.TableServiceImpl;

public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        // parent component of the dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("파일이 저장될 경로를 지정하세요.");

        int userSelection = fileChooser.showSaveDialog(null);
        String saveFileName = null;

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            saveFileName = fileToSave.getAbsolutePath();
        } else {
            return;
        }

        if (saveFileName.lastIndexOf(".xlsx") == -1) {
            saveFileName += ".xlsx";
        }

        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();

        TableService tableService = new TableServiceImpl(sqlSession);

        List<TableName> table = null; // 테이블 목록

        try {
            table = tableService.selectTableList();

            for (int i = 0; i < table.size(); i++) {
                TableName tableItem = table.get(i);
                // 테이블 목록 수 만큼 구조 조회 SQL 호출
                tableItem.setStruct(tableService.selectTableStruct(tableItem.getTableName()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "확인", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            sqlSession.close();
            return;
        } finally {
            sqlSession.close();
        }

        // -----------

        // 1차로 workbook을 생성
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 해더부분셀에 스타일을 주기위한 인스턴스 생성
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER); // 스타일인스턴스의 속성
        headerStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 0)));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderRight(BorderStyle.THIN); // 테두리 설정
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        XSSFFont font = workbook.createFont(); // 폰트 조정 인스턴스 생성
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        headerStyle.setFont(font);

        // 가운데 정렬과 얇은 테두리를 위한 스타일 인스턴스 생성
        XSSFCellStyle cellStyle0 = workbook.createCellStyle();
        cellStyle0.setAlignment(HorizontalAlignment.CENTER);
        cellStyle0.setBorderRight(BorderStyle.THIN);
        cellStyle0.setBorderLeft(BorderStyle.THIN);
        cellStyle0.setBorderTop(BorderStyle.THIN);
        cellStyle0.setBorderBottom(BorderStyle.THIN);
        XSSFFont font0 = workbook.createFont(); // 폰트 조정 인스턴스 생성
        font0.setFontHeightInPoints((short) 14);
        cellStyle0.setFont(font0);

        // 얇은 테두리를 위한 스타일 인스턴스 생성
        XSSFCellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.LEFT);
        cellStyle1.setBorderRight(BorderStyle.THIN);
        cellStyle1.setBorderLeft(BorderStyle.THIN);
        cellStyle1.setBorderTop(BorderStyle.THIN);
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        XSSFFont font1 = workbook.createFont(); // 폰트 조정 인스턴스 생성
        font1.setFontHeightInPoints((short) 14);
        cellStyle1.setFont(font1);

        XSSFRow row = null;
        XSSFCell cell = null;
        
        // 2차는 sheet생성
        XSSFSheet sheet = workbook.createSheet("테이블명세서");

        // 셀별 널비 정하기 헤더 그리기
        sheet.setColumnWidth((short) 0, (short) 2000);
        sheet.setColumnWidth((short) 1, (short) 5000);
        sheet.setColumnWidth((short) 2, (short) 4000);
        sheet.setColumnWidth((short) 3, (short) 3000);
        sheet.setColumnWidth((short) 4, (short) 3000);
        sheet.setColumnWidth((short) 5, (short) 5000);
        sheet.setColumnWidth((short) 6, (short) 10000);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 6));

        // 시트에 타이틀 행을 하나 생성한다.(i 값이 0이면 첫번째 줄에 해당)
        int k = 0;

        for (int i = 0; i < table.size(); i++) {
            TableName name = table.get(i);
            
            row = sheet.createRow((short) k++);
            cell = row.createCell(0);
            cell.setCellValue("TableName");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);

            cell = row.createCell(2);
            cell.setCellValue(name.getTableName().trim());
            cell.setCellStyle(cellStyle1);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(4);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(5);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(6);
            cell.setCellStyle(cellStyle1);

            row = sheet.createRow((short) k++);
            cell = row.createCell(0);
            cell.setCellValue("Description");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);

            cell = row.createCell(2);
            cell.setCellValue(name.getComment().trim());
            cell.setCellStyle(cellStyle1);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(4);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(5);
            cell.setCellStyle(cellStyle1);
            cell = row.createCell(6);
            cell.setCellStyle(cellStyle1);

            row = sheet.createRow((short) k++);

            cell = row.createCell(0);
            cell.setCellValue("No");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(1);
            cell.setCellValue("FieldName");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(2);
            cell.setCellValue("DataType");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(3);
            cell.setCellValue("Null");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(4);
            cell.setCellValue("Key");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(5);
            cell.setCellValue("Extra");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(6);
            cell.setCellValue("Comment");
            cell.setCellStyle(headerStyle);

            List<TableStruct> structList = name.getStruct();

            for (int j = 0; j < structList.size(); j++, k++) {
                // 시트에 하나의 행을 생성한다(i 값이 0이면 첫번째 줄에 해당)
                row = sheet.createRow((short) k);

                TableStruct struct = structList.get(j);

                // 생성된 row에 컬럼을 생성한다
                int x = 0;
                cell = row.createCell(x++);
                cell.setCellValue(struct.getId());
                cell.setCellStyle(cellStyle0);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getFieldName().trim());
                cell.setCellStyle(cellStyle1);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getDataType().trim());
                cell.setCellStyle(cellStyle0);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getIsNull().trim());
                cell.setCellStyle(cellStyle0);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getKey().trim());
                cell.setCellStyle(cellStyle0);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getExtra().trim());
                cell.setCellStyle(cellStyle0);

                cell = row.createCell(x++);
                cell.setCellValue(struct.getComment().replace("\\n", " ").trim());
                cell.setCellStyle(cellStyle1);
            }
            
            // 테이블간 빈 줄 삽입
            row = sheet.createRow((short) k++);
            cell = row.createCell(1);
            cell = row.createCell(2);
            cell = row.createCell(3);
            cell = row.createCell(4);
            cell = row.createCell(5);
            cell = row.createCell(6);
            row = sheet.createRow((short) k++);
            cell = row.createCell(1);
            cell = row.createCell(2);
            cell = row.createCell(3);
            cell = row.createCell(4);
            cell = row.createCell(5);
            cell = row.createCell(6);
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFileName);
            // 파일을 쓴다
            workbook.write(fos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "확인", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "확인", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null, saveFileName + " 경로에 엑셀파일이 저장되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
    }
}
