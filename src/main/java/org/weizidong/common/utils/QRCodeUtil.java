package org.weizidong.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * 二维码工具类
 *
 * @author WeiZiDong
 */
public class QRCodeUtil {

    private QRCodeUtil() {
    }

    /**
     * 二维码存放目录
     */
    private static String RESOURCE_URL = File.separator + "user_qr_code" + File.separator;
    /**
     * 编码格式，utf-8
     */
    private static final String CHARSET = "utf-8";
    /**
     * 二维码格式,PNG
     */
    private static final String FORMAT = "PNG";
    /**
     * 二维码尺寸
     */
    private static final int QRCODE_SIZE = 300;
    /**
     * LOGO宽度
     */
    private static final int LOGO_WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     LOGO图片路径
     * @param needCompress 是否压缩LOGO图片
     * @return 二维码图片
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        } catch (WriterException e) {
            throw new RuntimeException("生成二维码图片失败！", e);
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (StringUtils.isBlank(logoPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) {
        Image src;
        try {
            src = ImageIO.read(ResourceUtil.getResourceAsStream(logoPath));
        } catch (IOException e) {
            throw new RuntimeException("加载logo文件异常！", e);
        }
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO) 二维码文件名随机
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, boolean needCompress) {
        BufferedImage image = createImage(content, logoPath, needCompress);
        FileUtil.mkDir(FileUtil.BASE_PATH + RESOURCE_URL);
        String fileName = System.currentTimeMillis() + new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        try {
            ImageIO.write(image, FORMAT, new File(FileUtil.BASE_PATH + RESOURCE_URL + fileName));
        } catch (IOException e) {
            throw new RuntimeException("输出二维码图片失败！", e);
        }
        return RESOURCE_URL + fileName;
    }

    /**
     * 生成二维码(内嵌LOGO) 调用者指定二维码文件名
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param fileName     二维码文件名
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, String fileName, boolean needCompress) {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);
        FileUtil.mkdirs(FileUtil.BASE_PATH + RESOURCE_URL);
        fileName = fileName.substring(0, fileName.lastIndexOf(".") > 0 ? fileName.lastIndexOf(".") : fileName.length()) + "." + FORMAT.toLowerCase();
        try {
            ImageIO.write(image, FORMAT, new File(FileUtil.BASE_PATH + RESOURCE_URL + fileName));
        } catch (IOException e) {
            throw new RuntimeException("输出二维码图片失败！", e);
        }
        return RESOURCE_URL + fileName;
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param logoPath LOGO地址
     */
    public static String encode(String content, String logoPath) {
        return QRCodeUtil.encode(content, logoPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     */
    public static String encode(String content) {
        return QRCodeUtil.encode(content, null, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      二维码内容
     * @param logoPath     LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress) {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);
        try {
            ImageIO.write(image, FORMAT, output);
        } catch (IOException e) {
            throw new RuntimeException("输出二维码图片失败！", e);
        }
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param output  输出流
     */
    public static void encode(String content, OutputStream output) {
        QRCodeUtil.encode(content, null, output, false);
    }
}
