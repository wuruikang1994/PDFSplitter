import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String string;
        ContextVo vo = new ContextVo();
        List<ContextVo> voList = new ArrayList<>();
        vo.setSpecialiyNote("start");
        voList.add(vo);
        vo.setSpecialiyNote("end");
        for (ContextVo contextVo : voList) {
            System.out.println(contextVo.getSpecialiyNote());
        }
    }
}
