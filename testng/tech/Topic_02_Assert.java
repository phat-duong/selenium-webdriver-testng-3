package tech;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assert {

    @Test
    public void TC_01() {
        System.out.println("TC_01");
        //Thư viện Assert: Kiểm tra tính đúng đắn của dữ liệu
        // Mong đợi nó đúng/sai/ đầu vào và đầu ra như nhau
        // Bằng null/ khác null/...

        String addressCity = "Ho Chi Minh";

        //Kiểm tra 1 điều kiện mong đợi là đúng
        Assert.assertTrue(addressCity.equals("Ho Chi Minh"));

        //Kiểm tra 1 điều kiện mong đợi là sai
        Assert.assertFalse(addressCity.contains("Ha Noi"));

        //Kiểm tra dữ liệu đầu vào và đầu ra như nhau
        Assert.assertEquals(addressCity, "Ho Chi Minh");

        //Kiểm tra điều kiện mong đợi là null/ not null (Đã khởi tạo)
        Object fullname = null;
        Assert.assertNull(fullname);

        fullname = "Automation FC";
        Assert.assertNotNull(fullname);
    }
}
