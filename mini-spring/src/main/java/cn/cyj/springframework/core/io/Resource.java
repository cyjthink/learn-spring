package cn.cyj.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

    // spring中getInputStream()定义在InputStreamSource接口中
    InputStream getInputStream() throws IOException;
}
