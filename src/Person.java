import java.awt.*;
import java.awt.geom.AffineTransform;

public class Person {
    private int headX;
    private int headY;
    private int headR;
    private int health;
    private int bodyX1, bodyY1, bodyX2, bodyY2;
    private int bowX, bowY;
    private int relbowX, relbowY, handX, handY;
    private int healthX, healthY;
    private double bowAngle;
    private boolean left;
    private int bodyLength;

    public Person(int headR, int bodyX1, int bodyY1, int bodyY2, boolean left) {
        this.headR = headR;
        this.bodyX1 = bodyX1;
        this.bodyY1 = bodyY1;
        this.bodyX2 = bodyX1;
        this.bodyY2 = bodyY2;
        this.headX = bodyX1;
        this.headY = bodyY1 - headR;
        this.health = 5;
        this.bodyLength = bodyY2 - bodyY1;
        if (left) {
            this.bowX = bodyX1 + 2 * headR;
            this.relbowX = bodyX1 + headR;
            this.bowAngle = 22.5;
        } else {
            this.bowX = bodyX1 - 2 * headR;
            this.relbowX = bodyX1 - headR;
            this.bowAngle = 157.5;

        }
        this.handX = bowX;
        this.bowY = bodyY1 - headR;
        this.relbowY = (int) (bodyY1 - 0.5 * headR);
        this.handY = bowY;
        this.left = left;
        healthX = headX - 2 * headR;
        healthY = headY - 2 * headR;
    }

    // getter and setter
    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public int getBodyX(){return bodyX1;}

    public int getHealth() {
        return health;
    }

    public double getBowAngle() {
        return bowAngle;
    }

    public boolean isLeft() {
        return left;
    }

    public int getHeadR() {
        return headR;
    }

    public int getHandX() {
        return handX;
    }

    public int getHandY() {
        return handY;
    }

    public void reduceHealth(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void resetHand() {
        // reset hand position, used after releasing bow
        if (left) {
            this.bowX = bodyX1 + 2 * headR;
            this.relbowX = bodyX1 + headR;
            this.bowAngle = 22.5;
        } else {
            this.bowX = bodyX1 - 2 * headR;
            this.relbowX = bodyX1 - headR;
            this.bowAngle = 157.5;

        }
        this.handX = bowX;
        this.bowY = bodyY1 - headR;
        this.relbowY = (int) (bodyY1 - 0.5 * headR);
        this.handY = bowY;
    }

    public boolean checkHand(int X, int Y) {
        // check whether valid range
        return (left && X > bodyX1 && X < bowX) || (!left && X < bodyX1 && X > bowX);
    }

    public int checkHit(double x, double y) {
        // check whether the arrow hit human or not
        // 1 is hit the person, 0 is no hit
        if ((Math.abs(x - headX) < headR && Math.abs(y - headY) < headR)||(Math.abs(x - bodyX1) < 25 && y >= headY))
            return 1;
        return 0;
    }

    public void updateDrag(int X, int Y) {
        // calculate elbow position when pulling
        if (checkHand(X, Y)) {
            relbowX = X;
            relbowY = Y;
            handX = X;
            handY = Y;
        }
    }

    public void drawBowString(Graphics grap, int tailX, int tailY) {
        // draw bow string when pulling
        Graphics2D grap2D = (Graphics2D) grap;
        grap2D.setStroke(new BasicStroke(6f));
        grap2D.setColor(Color.WHITE);
        if (left) {
            grap2D.drawLine((int) (bowX - 1.2 * headR), (int) (bowY - headR * 0.7), tailX, tailY);
            grap2D.drawLine((int) (bowX - 0.6 * headR), (int) (bowY + headR * 1.5), tailX, tailY);
        } else {
            grap2D.drawLine((int) (bowX -10+ headR), (int) (bowY - headR * 0.5), tailX, tailY);
            grap2D.drawLine((int) (bowX + 0.2 * headR), (int) (bowY + headR * 1.5), tailX, tailY);

        }
    }

    public void drawDefaultBowString(Graphics grap) {
        // draw bow string when not pulling
        Graphics2D grap2D = (Graphics2D) grap;
        grap2D.setStroke(new BasicStroke(6f));
        grap2D.setColor(Color.WHITE);
        if (left) {
            grap2D.drawLine((int) (bowX - 1.2 * headR), (int) (bowY - headR * 0.7), (int) (bowX - 0.6 * headR), (int) (bowY + headR * 1.3));
        } else {
            grap2D.drawLine((int) (bowX - 10+ headR), (int) (bowY - headR * 0.6), (int) (bowX + 0.2 * headR), (int) (bowY + headR * 1.5));
        }
    }

    public void draw(Graphics grap, int transX, int transY) {
        Graphics2D grap2D = (Graphics2D) grap;
//        move frame according to frame position
        AffineTransform tx = new AffineTransform();
        tx.translate(-transX, -transY);
        grap2D.setTransform(tx);

        if (left) {
            grap2D.setColor(Color.RED);
            grap.setColor(Color.RED);
        } else {
            grap2D.setColor(Color.BLUE);
            grap.setColor(Color.BLUE);
        }
        // draw head
        grap.fillOval(headX - headR, headY - headR, headR * 2, headR * 2);
        // draw body
        grap2D.setStroke(new BasicStroke(headR / 5));
        grap2D.drawLine(bodyX1, bodyY1, bodyX2, bodyY2);
//        draw legs
        grap2D.drawLine(bodyX2 - bodyLength / 3, bodyY2 + bodyLength / 2, bodyX2, bodyY2);
        grap2D.drawLine(bodyX2 + bodyLength / 3, bodyY2 + bodyLength / 2, bodyX2, bodyY2);
//        draw hands
        grap2D.drawLine(bodyX1, bodyY1, bowX, bowY);
        grap2D.drawLine(bodyX1, bodyY1, relbowX, relbowY);
        // draw bow
        grap2D.setColor(Color.WHITE);
        if (left)
            grap2D.drawArc((int) (bowX - 1.7 * headR), (int) (bowY - headR * 0.7), (int)(headR * 1.5), (int)(headR * 2.3), (int) -(90 - bowAngle), 180);
        else
            grap2D.drawArc((int) (bowX - 0.3 * headR), (int) (bowY - headR * 0.7), (int)(headR * 1.5), (int)(headR * 2.3), (int) -(90 - bowAngle), 180);
        // draw health bars above head
        grap2D.setColor(Color.GREEN);
        for (int i=0;i<health;i++){
            if(left)
                grap2D.fillRect(healthX+40*i+30, healthY-50, 20 , headR );
            else
                grap2D.fillRect(healthX+40*i, healthY-50, 20 , headR );
        }
    }
}
