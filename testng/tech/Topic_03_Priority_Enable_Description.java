package tech;

import org.testng.annotations.Test;

public class Topic_03_Priority_Enable_Description {

    //Nếu set priority thì chạy theo priority trước
    //Nếu không set priority thì chạy theo tên của testcase (theo Alphabet: từ 0 - 9, a - z)
    @Test(priority = 1, enabled = true, description = "#JIRA3599 - Tạo sản phẩm")
    public void Product_01_Create() {

    }

    @Test(priority = 2,enabled = false)
    public void Product_02_View() {

    }

    @Test(priority = 4)
    public void Product_03_Edit() {

    }

    @Test(priority = 3)
    public void Product_04_Delete() {

    }
}
