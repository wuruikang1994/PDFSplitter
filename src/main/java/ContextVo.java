import java.util.Objects;

public class ContextVo {
    //批次
    private String batch;

    //科目
    private String subject;

    //计划性质
    private String planNature;

    //院校代码
    private String  schoolCode;

    //院校名称
    private String schoolName;

    public String getSpecialiyCode() {
        return specialiyCode;
    }

    public void setSpecialiyCode(String specialiyCode) {
        this.specialiyCode = specialiyCode;
    }

    //专业代码
    private String specialiyCode;

    //专业名称
    private String specialiyName;

    //专业简注
    private String specialiyNote;

    //次选科目
    private String selectSecond;

    @Override
    public String toString() {
        return "ContextVo{" +
                "batch='" + batch + '\'' +
                ", subject='" + subject + '\'' +
                ", planNature='" + planNature + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", specialiyCode='" + specialiyCode + '\'' +
                ", specialiyName='" + specialiyName + '\'' +
                ", specialiyNote='" + specialiyNote + '\'' +
                ", selectSecond='" + selectSecond + '\'' +
                ", presonNum='" + presonNum + '\'' +
                ", age='" + age + '\'' +
                ", tution='" + tution + '\'' +
                '}';
    }

    //专业招生人数
    private String presonNum;



    //学制
    private String age;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlanNature() {
        return planNature;
    }

    public void setPlanNature(String planNature) {
        this.planNature = planNature;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSpecialiyName() {
        return specialiyName;
    }

    public void setSpecialiyName(String specialiyName) {
        this.specialiyName = specialiyName;
    }

    public String getSpecialiyNote() {
        return specialiyNote;
    }

    public void setSpecialiyNote(String specialiyNote) {
        this.specialiyNote = specialiyNote;
    }

    public String getSelectSecond() {
        return selectSecond;
    }

    public void setSelectSecond(String selectSecond) {
        this.selectSecond = selectSecond;
    }

    public String getPresonNum() {
        return presonNum;
    }

    public void setPresonNum(String presonNum) {
        this.presonNum = presonNum;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTution() {
        return tution;
    }

    public void setTution(String tution) {
        this.tution = tution;
    }

    //学费
    private String tution;


}
