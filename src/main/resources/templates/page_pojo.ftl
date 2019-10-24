package ${packageName}.pojo

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ${author}
 * @Date ${date}
 * 封装分页对象
 **/
@Data
public class ${pageName}<T> {

    /**
    * 当前页
    */
    private int page;
    /**
    * 总行数
    */
    private int totalRows;
    /**
    * 总页数
    */
    private int pages;

    /**
    * 存放数据集
    */
    private List<T> list = new ArrayList();

    public PageList() {
    }

    public PageList(int page, int pages, int totalRows, List<T> list) {
        this.page = page;
        this.list = list;
        this.pages = pages;
        this.totalRows = totalRows;
    }

}