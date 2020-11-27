package dao;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CreateImage {

	private static final int width=100;
	private static final int height=30;
	private static final int length=4;
	public static final int linecount=20;
	
	//ȥ�����ױ�ʶ��0oO1I
	private static final String str="23456789"+"ABCDEFGHIJKLMNPQRSTUVWXYZ"+"abcdefghijklmnpqrstuvwxyz";

	private static Random random=new Random();
	
	//���4λ��֤��
	public String createCode(){
		String code="";
		for(int i=0;i<4;i++){
			char c=str.charAt(random.nextInt(str.length()));
			code=code+c;
		}
		return code;
	}
	
	//��ȡ��ͬ��ɫ
	public Color getColor(){
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		
	}
	
	//��ȡ��ͬ��ʽ
	public Font getFont(){
		return new Font("Fixedsys",Font.CENTER_BASELINE,22);
	}
	
	//�����ַ�
	public void drawChar(Graphics g,String code){
		g.setFont(getFont());
		for(int i=0;i<length;i++){
			char c=code.charAt(i);
			g.setColor(getColor());
			g.drawString(c+"", 20*i+10, 20);
		}
	}
	
	//���������
	public void drawLine(Graphics g){
		int x=random.nextInt(width);
		int y=random.nextInt(height);
		int xl=random.nextInt(13);
		int yl=random.nextInt(15);
		g.setColor(getColor());
		g.drawLine(x, y, x+xl, y+yl);
	}
	
	//������֤��ͼƬ
	public BufferedImage CreateImage(String code){
		//�������
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g=image.getGraphics();
		//���ñ�����ɫ�����ƾ��α���
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//��֤�����
		drawChar(g,code);
		//����ߵĻ���
		for(int i=0;i<linecount;i++)
			drawLine(g);
		//����ͼƬ
		g.dispose();
		//�������ɵ�ͼƬ
		return image;
	}
}
