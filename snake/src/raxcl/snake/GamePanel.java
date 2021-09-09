package raxcl.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length; //蛇的长度
    int[] snakeX = new int[600]; //蛇的坐标X
    int[] snakeY = new int[500]; //蛇的坐标Y
    String fx; //U:上，D：下，L：左，
    boolean isStart;//游戏是否开始

    Timer timer = new Timer(100, this);//定时器

    //定义一个食物
    int foodx;
    int foody;
    Random random = new Random();

    //死亡判断
    boolean isFail = false;
    //积分系统
    int score;

    //构造器
    public GamePanel() {
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//让时间动起来
    }

    //初始化
    public void init() {
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100; //头部坐标
        snakeX[1] = 75;
        snakeY[1] = 100; //第一个身体坐标
        snakeX[2] = 50;
        snakeY[2] = 100; //第二个身体坐标
        fx = "R";
        isStart = false;
        foodx = 25 + 25 * random.nextInt(34);
        foody = 75 + 25 * random.nextInt(24);
        score = 0;
    }


    //画板： 画界面，化蛇
    //Graphics: 画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //清屏
        this.setBackground(Color.WHITE); //设置背景的颜色
        Data.header.paintIcon(this, g, 25, 11); //绘制头部的广告栏
        g.fillRect(25, 75, 850, 600); //绘制游戏区域

        //画一条静态的小蛇
        if (fx.equals("R")) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("D")) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("L")) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("U")) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]); //蛇的身体长度通过length来控制
        }

        //画积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度：" + length, 750, 35);
        g.drawString("分数：" + score, 750, 50);

        //画食物
        Data.food.paintIcon(this, g, foodx, foody);

        //游戏提示：是否开始
        if (isStart == false) {
            //画一个文字，String
            g.setColor(Color.WHITE); //设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//设置字体
            g.drawString("按下空格开始游戏", 300, 300);
        }

        //失败提醒
        if (isFail) {
            //画一个文字，String
            g.setColor(Color.RED); //设置画笔颜色
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));//设置字体
            g.drawString("游戏失败，按下空格重新开始", 200, 300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //接受键盘的输入：监听
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是哪个键
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) { //如果按下的是空格键
            if (isFail) {
                //游戏失败，重新开始
                isFail = false;
                init();//重新初始化游戏
            } else {
                isStart = !isStart;
            }
            repaint();//刷新界面
        }

        //键盘控制走向
        if (keyCode == KeyEvent.VK_RIGHT) {
            fx = "R";
        } else if (keyCode == KeyEvent.VK_DOWN) {
            fx = "D";
        } else if (keyCode == KeyEvent.VK_LEFT) {
            fx = "L";
        } else if (keyCode == KeyEvent.VK_UP) {
            fx = "U";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //定时器，监听时间，帧：执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态,并且没有失败
        if (isStart && isFail == false) {
            //右移
            for (int i = length - 1; i > 0; i--) { //除了脑袋，身体都向前移动
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            //通过控制方向让头部移动
            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;//头部移动
                //边界判断
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;//头部移动
                //边界判断
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;//头部移动
                //边界判断
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;//头部移动
                //边界判断
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            }

            //小蛇吃食物
            if (snakeX[0] == foodx && snakeY[0] == foody) {
                //长度+1
                length++;

                score = score + 10;

                //重新生成食物
                foodx = 25 + 25 * random.nextInt(34);
                foody = 75 + 25 * random.nextInt(24);
            }

            //结束判断
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
            }

            //刷新界面
            repaint();
        }
        timer.start();//让时间动起来
    }
}
