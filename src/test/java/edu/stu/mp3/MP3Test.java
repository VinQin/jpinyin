package edu.stu.mp3;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.github.stuxuhai.jpinyin.TestPinyinHelper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class MP3Test {

    private String input;
    private String filepathSrcDir;
    private String filepathTargetDir;

    public MP3Test(String input, String filepathSrcDir, String filepathTargetDir) throws PinyinException {
        this.input = input;
        this.filepathSrcDir = filepathSrcDir;
        this.filepathTargetDir = filepathTargetDir;
        File src = new File(filepathSrcDir);
        File target = new File(filepathTargetDir);

        if (!src.exists() || !src.isDirectory()) {
            throw new PinyinException("源MP3目录不存在！");
        }
        if (!target.exists() || !target.isDirectory()) {
            throw new PinyinException("目标目录不存在！");
        }
    }


    public void setInput(String input) {
        this.input = input;
    }

    private String[] getMP3Filename() throws PinyinException {
        StringBuilder sb = new StringBuilder();
        String separator = ",";

        String str = PinyinHelper.convertToPinyinString(input, separator, PinyinFormat.WITH_TONE_NUMBER);
        String[] res = str.split(separator);

        for (String name : res) {
            if (input.contains(name)) {
                //拼音转化失败
                continue;
            }
            name = filepathSrcDir + name + ".mp3 ";
            sb.append(name);
        }

        String[] filename = new String[2];
        filename[0] = sb.toString().trim();
        filename[1] = filepathTargetDir + PinyinHelper.getShortPinyin(input) + (new Date().getTime()) + ".mp3";

        return filename;
    }

    private String[] mp3ConvertCMD() throws PinyinException {
        String[] filename = getMP3Filename();
        String src = filename[0];
        String target0 = filename[1];
        String cmd0 = "mp3wrap " + target0 + " " + src;
        String target1 = target0.substring(0, target0.indexOf(".mp3")) + "_MP3WRAP" + ".mp3";
        String cmd1 = "sox " + target1 + " " + target0 + " speed 1.7 pitch -700";

        String[] cmd = new String[]{cmd0, cmd1};

        return cmd;
    }

    private void executeCMD() throws PinyinException {
        String[] cmds = mp3ConvertCMD();

        for (String cmd : cmds) {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();//阻塞异步
                process.destroy();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public static void main(String[] args) throws PinyinException {
        String input = "";
        Scanner scanner = new Scanner(System.in);

        String filepathSrcDir = "/home/vinqin/git-repository/jpinyin/src/test/resources/mp3/";
        String filepathTargetDir = "/home/vinqin/git-repository/jpinyin/src/test/resources/target/";

        MP3Test mp3Test = new MP3Test(input, filepathSrcDir, filepathTargetDir);

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            mp3Test.setInput(input);
//            String[] cmd = mp3Test.mp3ConvertCMD();
//            TestPinyinHelper.print(cmd);
            mp3Test.executeCMD();
        }
    }
}
