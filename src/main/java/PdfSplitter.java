import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PdfSplitter {
    static List<String> planNatureList = new ArrayList<>();
    static List<String> bathList = new ArrayList<>();
    static List<String> subjectList = new ArrayList<>();
    static{
        planNatureList.add("非定向");
        planNatureList.add("定向");
        planNatureList.add("体育（物理科目组合）");
        planNatureList.add("统考未涉及的校考");


        bathList.add("本科提前批A段");
        bathList.add("本科提前批B段");
        bathList.add("本科提前批C段");
        bathList.add("本科批");
        bathList.add("专科提前批");
        bathList.add("专科批");
        bathList.add("本科预科");
        subjectList.add("物理科目组合");
    }


    static final String outPath = "C:\\Users\\Administrator\\Desktop\\123\\";
    public static void main(String[] args) throws IOException {
//        File filePath = new File(outPath);
//        File[] files = filePath.listFiles();
//        System.out.println(files.length);

//        List<ContextVo> object = getObject();
//        for (ContextVo vo : object) {
//            System.out.println(vo);
//        }



//        List<String> text = getText();
//        changeList(text);
//        getSock(text);
        writerExcel();
    }

    //通过图形提起文本内容
     public static List<String> getText(String path) throws IOException {
        List<String> textList = new ArrayList<>();
        PDDocument document = PDDocument.load(new File( path));
        try {
            PDFTextStripperByArea textStripper = new PDFTextStripperByArea();

            PDPage page = document.getPage(0);
            int width=(int)page.getBBox().getWidth();
            int height=(int)page.getBBox().getHeight();
            Rectangle rectangle = new Rectangle(0, 0, width/2, height);

            Rectangle rectangleSecond = new Rectangle(width/2, 0, width/2, height);

            textStripper.extractRegions(page);
            textStripper.setSortByPosition(true);
            textStripper.addRegion("firstSocke", rectangle);
            textStripper.addRegion("secondeSocke",rectangleSecond);
            textStripper.extractRegions(page);
            String text = textStripper.getTextForRegion("firstSocke");
            String textTwo = textStripper.getTextForRegion("secondeSocke");
            textList.add(text);
            textList.add(textTwo);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textList;
    }


    //去除表头
    public static List<String> changeList(List<String> list){
        ArrayList<String> newList = new ArrayList<>();
        String s1 = list.get(0);
        String s2 =list.get(1);
        newList.add(s1.substring(43,s1.length()-5));
        newList.add(s2.substring(50,s2.length()-5));
        return newList;
    }

    //以半页为单位填充到String

    public static String getAllText() throws IOException {
        String allText =  "";
        ArrayList<String> newList = new ArrayList<>();
        File filePath = new File(outPath);
        File[] files = filePath.listFiles();
        for (int i = 1; i <= files.length ; i++) {
            String s = outPath + "output" + i + ".pdf";
            List<String> strings = changeList(getText(s));
            allText += strings.get(0);
            allText += "\r\n";
            allText += strings.get(1);
            allText += "\r\n";

        }
        return  allText;
    }

    //把数据放到对象中进行返回
    public static List<ContextVo> getObject() throws IOException {
        String allText = getAllText();

        //初始化对象字段等待封装

        String batch = "";
        String subject = "";
        String planNature = "";
        //院校代码
        String  schoolCode = "";

        //院校名称
        String schoolName = "";

        //专业代码
        String specialiyCode = "";

        //专业名称
        String specialiyName = "";

        //专业简注
         String specialiyNote = "";

        //次选科目
         String selectSecond = "";
        //专业招生人数
         String presonNum = "";

        //学制
        String age = "";
        //学费
        String tution = "";

        ArrayList<ContextVo> contextVos = new ArrayList<>();
        String[] strings = allText.split("\r\n");
        for (int i = 0; i < strings.length; i++) {
            String[] split1 = strings[i].split("(\\s|\u00A0)+");

            if(planNatureList.contains(split1[0]) || split1[0].startsWith("***")){
                //计划性质
                if(split1[0].contains("***") && strings[i+1].contains("综合分=文化分×0.3+(专业分÷专业满分)×750×0.7") && strings[i+2].contains("高校在已投档范围内按本校录取规则录取")){
                    planNature = split1[0] + strings[i+1] +strings[i+2];
                }else{
                    planNature = split1[0];
                }

            }else if (subjectList.contains(split1[0])){
                //科目
                subject = split1[0];

            }else if (bathList.contains(split1[0])){
                batch = split1[0];
                planNature = "非定向";
            }else if (split1.length == 3 && split1[0].length() == 4 && !split1[0].contains("称与说明")){
                //读取到文本正文
                schoolCode = split1[0];
                schoolName = split1[1];
            }else if (split1.length == 2 && split1[0].length() == 4){
                //读取到文本正文
                schoolCode = split1[0];
                schoolName = split1[1];
            } else if (split1.length == 6 && split1[0].length() == 2){
                if(split1[0].contains("(") && !split1[0].contains(")")){
                    specialiyName = split1[1] + strings[i+1];
                }else{
                    specialiyName = split1[1];
                }
                specialiyCode = split1[0];
                selectSecond = split1[2];
                presonNum = split1[3];
                age = split1[4];
                tution = split1[5];


                //判断是否写入对象
                if(strings[i+1].startsWith("[")){
                    specialiyNote = strings[i+1];
                    if(!strings[i+1].endsWith("]")){
                        int a = 1;
                        while (!strings[i+a].endsWith("]")){
                            specialiyNote += strings[i+a];
                            a += 1;
                        }
                        specialiyNote += strings[i+a];
                    }

                    ContextVo vo = new ContextVo();
                    vo.setBatch(batch);
                    vo.setSubject(subject);
                    vo.setPlanNature(planNature);
                    vo.setSchoolCode(schoolCode);
                    vo.setSchoolName(schoolName);
                    vo.setSpecialiyCode(specialiyCode);
                    vo.setSpecialiyName(specialiyName);
                    vo.setSpecialiyNote(specialiyNote);
                    vo.setSelectSecond(selectSecond);
                    vo.setPresonNum(presonNum);
                    vo.setAge(age);
                    vo.setTution(tution);
                    contextVos.add(vo);
                }else{
                    ContextVo vo = new ContextVo();
                    vo.setBatch(batch);
                    vo.setSubject(subject);
                    vo.setPlanNature(planNature);
                    vo.setSchoolCode(schoolCode);
                    vo.setSchoolName(schoolName);
                    vo.setSpecialiyCode(specialiyCode);
                    vo.setSpecialiyName(specialiyName);
                    vo.setSpecialiyNote(specialiyNote);
                    vo.setSelectSecond(selectSecond);
                    vo.setPresonNum(presonNum);
                    vo.setAge(age);
                    vo.setTution(tution);
                    contextVos.add(vo);

                }

                    //专业代码
                    specialiyCode = "";

                    //专业名称
                    specialiyName = "";

                    //专业简注
                    specialiyNote = "";

                    //次选科目
                    selectSecond = "";
                    //专业招生人数
                    presonNum = "";

                    //学制
                    age = "";
                    //学费
                    tution = "";

            }else if (split1.length == 5 && split1[0].length() == 2){
                specialiyCode = split1[0];
                specialiyName = split1[1];
                selectSecond = split1[2];
                presonNum = split1[3];
                age = split1[4];


                //判断是否写入对象
                if(strings[i+1].startsWith("[")){
                    specialiyNote = strings[i+1];
                    if(!strings[i+1].endsWith("]")){
                        int a = 1;
                        while (!strings[i+a].endsWith("]")){
                            specialiyNote += strings[i+a];
                            a += 1;
                        }
                        specialiyNote += strings[i+a];
                    }
                    ContextVo vo = new ContextVo();
                    vo.setBatch(batch);
                    vo.setSubject(subject);
                    vo.setPlanNature(planNature);
                    vo.setSchoolCode(schoolCode);
                    vo.setSchoolName(schoolName);
                    vo.setSpecialiyCode(specialiyCode);
                    vo.setSpecialiyName(specialiyName);
                    vo.setSpecialiyNote(specialiyNote);
                    vo.setSelectSecond(selectSecond);
                    vo.setPresonNum(presonNum);
                    vo.setAge(age);
                    vo.setTution(tution);
                    contextVos.add(vo);
                }else{
                    ContextVo vo = new ContextVo();
                    vo.setBatch(batch);
                    vo.setSubject(subject);
                    vo.setPlanNature(planNature);
                    vo.setSchoolCode(schoolCode);
                    vo.setSchoolName(schoolName);
                    vo.setSpecialiyCode(specialiyCode);
                    vo.setSpecialiyName(specialiyName);
                    vo.setSpecialiyNote(specialiyNote);
                    vo.setSelectSecond(selectSecond);
                    vo.setPresonNum(presonNum);
                    vo.setAge(age);
                    vo.setTution(tution);
                    contextVos.add(vo);

                }


                //专业代码
                specialiyCode = "";

                //专业名称
                specialiyName = "";

                //专业简注
                specialiyNote = "";

                //次选科目
                selectSecond = "";
                //专业招生人数
                presonNum = "";

                //学制
                age = "";
                //学费
                tution = "";
            }
//            else if(StringUtils.equals(split1[0],"名称与说明" ) || StringUtils.equals(split1[0],"明") ||StringUtils.equals(split1[0],"学")){
//
//            }
            else{
//                for (String s : split1) {
//                    System.out.println(s);
//                }
            }
        }



        return contextVos;
    }

    public static void writerExcel() throws IOException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\模板.xlsx";
        String outExcelPath = "C:\\Users\\Administrator\\Desktop\\2023河北物理招生计.xlsx";
        List<ContextVo> vos = getObject();
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        for (int i = 0; i < vos.size() ; i++) {
            Row row = sheetAt.createRow(i+1);
            row.createCell(0).setCellValue(vos.get(i).getBatch());


//            row.createCell(0).setCellValue(vos.get(i).getBatch());
            row.createCell(1).setCellValue(vos.get(i).getSubject());
            row.createCell(2).setCellValue(vos.get(i).getPlanNature());
            row.createCell(3).setCellValue(vos.get(i).getSchoolCode());
            row.createCell(4).setCellValue(vos.get(i).getSchoolName());
            row.createCell(5).setCellValue(vos.get(i).getSpecialiyCode());
            row.createCell(6).setCellValue(vos.get(i).getSpecialiyName());
            row.createCell(7).setCellValue(vos.get(i).getSpecialiyNote());
            row.createCell(8).setCellValue("");
            row.createCell(9).setCellValue(vos.get(i).getSelectSecond());
            row.createCell(10).setCellValue(vos.get(i).getPresonNum());
            row.createCell(11).setCellValue(vos.get(i).getAge());
            row.createCell(12).setCellValue(vos.get(i).getTution());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outExcelPath));
        workbook.write(fileOutputStream); // 将Workbook写入文件
        fileOutputStream.close(); // 关闭文件流
    }
}
