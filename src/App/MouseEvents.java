package App;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEvents implements Runnable{
    private int counter = 0;
    private boolean start = true;
    public void run() {
        while (start){
            double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            System.out.println("X:" + mouseX + " Y:" + mouseY);
//            System.out.println("Y:" + mouseY);
            System.out.println(MouseInfo.getPointerInfo().getDevice());
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (counter == 1000){
                start = false;
            }
        }
    }

    public void createThread(){
        MouseEvents mt = new MouseEvents();

        Thread myThready = new Thread(mt);	//Создание потока "myThready"
        myThready.start();				//Запуск потока

        System.out.println("Главный поток завершён...");
    }
}
