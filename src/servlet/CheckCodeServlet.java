package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.MailUtil;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * A servlet for generating random check code image
 */
@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String referer = request.getHeader("referer");
        String character= "Aa0BbCc1DdEe2FfGg3HhIi4JjKk5LlMmNnOoPpQqRrSs6TtUu7VvWw8XxYy9Zz";
        StringBuilder checkCode= new StringBuilder();
        Random random=new Random();
        //Register
        System.out.println(referer+" seek check code!");
        if (referer.contains("signUp.jsp")){
            for (int i=1;i<=8;i++) {
                char c = character.charAt(random.nextInt(character.length()));
                checkCode.append(c);
            }
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String text = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hi, " + username +
                    "! Weclome to EKMall, we offer you the best products you have never seen before!" +
                    " Your registered check code is: <a>" + checkCode + 
                    "</a>! Please finish your sign up process ASAP!<br><hr/>"+
                    "Do not reply this email!<br>---------------------------------------Your dear dongdong";
            boolean result = MailUtil.sendEmail(email,"EKMall",username, "EKMall Registration",text);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("signUp_Msg",result ? "Check code is already been sent!":"The email address is not exist! Please check!");
            map.put("checkCode",checkCode.toString());
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),map);
        }else{
            //????????????????????????????????????
            response.setHeader("pragma","no-cache");
            response.setHeader("cache-control","no-cache");
            response.setHeader("expires","0");
            //Login
            //1.??????????????????
            int width=120;
            int height=35;
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //2.??????????????????
            //???????????????
            Graphics graphics=image.getGraphics();
            graphics.setColor(Color.PINK);
            graphics.fillRect(0,0,width,height);
            //?????????
            graphics.setColor(Color.BLUE);
            graphics.drawRect(0,0,width-1,height-1);
            
            //???????????????
            graphics.setFont(new Font("Arial",Font.BOLD,28));
            for (int i=1;i<=5;i++){
                char c = character.charAt(random.nextInt(character.length()));
                checkCode.append(c);

                graphics.drawString(c+"",width/6*i,random.nextInt(height/2)+height/2);
            }
            //?????????
            graphics.setColor(Color.MAGENTA);
            for (int i = 0; i < 10; i++) {
                int x=random.nextInt(width);
                int y=random.nextInt(height);
                int x1=random.nextInt(width);
                int y1=random.nextInt(height);
                graphics.drawLine(x,y,x1,y1);
            }
            //?????????????????????loginServlet, ?????????????????????
            HttpSession checkCodeSession = request.getSession();
            checkCodeSession.setAttribute("code",checkCode.toString());
            //3.????????????????????????
            ImageIO.write(image,"jpg",response.getOutputStream());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

}
