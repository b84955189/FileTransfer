package transfer.data;

import java.util.ArrayList;
import java.util.List;

public class DataList {
    //侦测数据集合--因为String已经重写equals方法，所以可以判断重复数据
    public static List<String> detectList=new ArrayList<String>();

    static{
        //---测试用
        detectList.add("127.0.0.1");



    }

}
