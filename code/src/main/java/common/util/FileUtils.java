package common.util;


import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import mo.must.common.constants.TestConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Utility class for file operations.
 * This class provides methods for reading and displaying file contents, particularly useful for handling UI hierarchy files in UI tests.
 */
public class FileUtils {

    // UI 层次结构文件的存储目录
    public static final String UI_HIERARCHY_DIR = InstrumentationRegistry.getInstrumentation().getTargetContext().getFilesDir().toString() + "/";
    public static final String UI_HIERARCHY_FILE = UI_HIERARCHY_DIR + "current_ui_hierarchy.xml";

    /**
     * Displays the contents of the UI hierarchy file in the log.
     *
     * @param uiHierarchyFile The path to the UI hierarchy file.
     */
    public static void displayUiHierarchyFile(File uiHierarchyFile) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(uiHierarchyFile.getAbsolutePath()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TestConstants.TAG, line);
            }
        } catch (IOException e) {
            // 处理文件读取异常
            Log.e(TestConstants.TAG, "读取 UI 布局到 Logcat 时出现异常");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 处理文件关闭异常
                    Log.e(TestConstants.TAG, "reader.close() 时出现异常");
                }
            }
        }
    }

    /**
     * Reads the contents of an XML file and returns it as a concatenated string.
     *
     * @param xmlFile The file to read.
     * @return The contents of the file.
     */
    public static String readXmlFileToString(File xmlFile) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(xmlFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            Log.e(TestConstants.TAG, "读取文件时出错: " + e.getMessage());
        }

        return content.toString();
    }
}


