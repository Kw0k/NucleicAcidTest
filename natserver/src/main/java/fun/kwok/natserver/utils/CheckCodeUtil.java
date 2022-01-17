package fun.kwok.natserver.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CheckCodeUtil {
    public static void code(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(size);
            char c = base.charAt(index);
            sb.append(c);
            //绘制验证码
            g.setColor(getColor());
            g.setFont(getRandomFont());
            g.drawString(c+"", (29*i)+4, 32);
            //绘制干扰线
            g.setColor(getColor());
            g.drawLine(r.nextInt(width), r.nextInt(height), r
                    .nextInt(width), r.nextInt(height));

        }
        String checkCode = sb.toString().toUpperCase();//将session中要存放的验证码转换为大写
        //将验证码放入HttpSession中
        request.getSession().setAttribute("check_code", checkCode);
        //将内存中的图片输出到浏览器
        ImageIO.write(image, "PNG", response.getOutputStream());
    }
    private static Color getColor() {
        Random random = new Random();
        int r = 0, g = 0, b = 0;
        for (int i = 1; i < 255; i++) {
            for (int j = 1; j < 255; j++) {
                r = random.nextInt(i) + 1;
                g = random.nextInt(j) + 1;
                b = random.nextInt((i + j) / 2) + 1;
            }
        }
        return new Color(r, g, b);
    }
    private static Font getRandomFont() {
        Random random = new Random();
        String[] fonts = {"Georgia", "Verdana", "Arial", "Tahoma", "Time News Roman", "Courier New", "Arial Black", "Quantzite"};
        int fontIndex = (int) Math.round(Math.random() * (fonts.length - 1));
        int fontSize = (int) Math.round(Math.random() * 4 + 32);
        return new Font(fonts[fontIndex], random.nextInt(3), fontSize);
    }
}
