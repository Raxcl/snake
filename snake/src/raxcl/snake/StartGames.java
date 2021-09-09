package raxcl.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //1.绘制一个静态窗口 JFrame
        JFrame jFrame = new JFrame("raxcl - 贪吃蛇小游戏");
        jFrame.setBounds(10,10,900,720);//设置界面大小
        jFrame.setResizable(false);//设置窗口大小不可变
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭事件，游戏可以关闭了

        //绘制窗口 面板 JPanel 可以加入到JFrame
        jFrame.add(new GamePanel());

        jFrame.setVisible(true);//让窗口能够 展现出来
    }
}
