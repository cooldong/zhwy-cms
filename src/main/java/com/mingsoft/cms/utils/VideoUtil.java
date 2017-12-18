package com.mingsoft.cms.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <dl>
 * <dt>ProjectName : zhwy </dt>
 * <dt>PakageName : com.mingsoft.cms.utils </dt>
 * <dt>ClassName: VideoUtil </dt>
 * <dd>CreateDate: 2017-12-18 10:05 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
public class VideoUtil {

    private static String ffmpegPath = "C:\\Users\\Administrator\\Desktop\\ffmpeg-20171217-9dcecbf-win64-static\\bin\\ffmpeg.exe";

    /**
     * ffmpegPath为FFmpeg.exe所在路径
     upFilePath为视频的所在路径（本地路径）
     mediaPicPath为缩略图的存储路径（D:/test.jpg）
     * @param upFilePath
     * @param mediaPicPath
     */
    public static void handlerpic(String upFilePath, String mediaPicPath) {
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("0"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*800"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        ProcessBuilder builder = new ProcessBuilder();
        try {

            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            // 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }


    public static String getThumbNailFilePath(String video, String id){
        VideoUtil videoUtil = new VideoUtil();
        String path = videoUtil.getClass().getResource("/").getPath();
        File file = new File(path).getParentFile().getParentFile();
        String thumbnail = file.getPath() + File.separator + "uploadThumbNail";
        String videoPath = file.getPath() + video;
        File fileThumbNail = new File(thumbnail);
        if(!fileThumbNail.exists()){
            fileThumbNail.mkdirs();
        }
        String thumbnailFilePath = thumbnail + File.separator + id + ".jpg";
        String relativePath = File.separator + "uploadThumbNail" + File.separator + id + ".jpg";
        VideoUtil.handlerpic(videoPath, thumbnailFilePath);
        return relativePath;
    }
}
