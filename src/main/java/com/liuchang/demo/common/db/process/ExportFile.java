package com.liuchang.demo.common.db.process;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 9:58 2020/9/27
 * @ Description：生成文件
 * @ Modified By：
 */
@Component
public class ExportFile {

    public static void ExportToFile(String fileName,String content) {
        File testFile = new File("D:" + File.separator + fileName);

        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(testFile);
            fwriter.write(content);
        } catch (
                IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
