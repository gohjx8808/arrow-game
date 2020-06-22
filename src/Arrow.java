import java.awt.*;
import java.awt.geom.AffineTransform;

public class Arrow {
    private double x;
    private double y;
    private int length;
    private double vx;
    private double vy;
    private double angle;
    private int thickness;

    public Arrow(double x, double y, int length, int thickness, double angle) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.thickness = thickness;
        vx = 0.0;
        vy = 0.0;
        this.angle = angle;
    }

    // getter and setter
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public int getThickness() {
        return thickness;
    }

    public double[] getHeadAndTail() {
        // calculate the location of arrow tip and tail and return {tipX, tipY, tailX, tailY}
        double distX = length * Math.cos(angle);
        double distY = length * Math.sin(angle);
        double[] headTailLoc = {this.x + distX, this.y + distY, this.x - distX, this.y - distY};
        return headTailLoc;
    }

    public void setForce(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void setAngle(double x, double y) {
        this.angle = Math.atan2(y, x);
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double ax, double ay) {
        // function to move the arrow at each time interval
        x += vx;
        y += vy;

        // based on wind acceleration (acceleration of x), update the x velocity at each time interval
        vx += ax;
        // based on gravity (acceleration of y), update the y velocity at each time interval
        vy += ay;

        // calculate angle of arrow based on new velocity
        angle = Math.atan2(vy, vx);
    }


    public void draw(Graphics grap, int transX, int transY, Color color) {
        // function to draw arrow on graphics
        Graphics2D grap2D = (Graphics2D) grap;
        // move the graphics based on current frame position
        AffineTransform tx = new AffineTransform();
        tx.translate(-transX, -transY);
        grap2D.setTransform(tx);
        grap2D.setColor(color);
        grap2D.setStroke(new BasicStroke(thickness));
        // draw arrow body
        double[] arrowCoords = getHeadAndTail();
        grap2D.drawLine((int) arrowCoords[0], (int) arrowCoords[1], (int) arrowCoords[2], (int) arrowCoords[3]);
        double distX1 = length * Math.cos(angle+15);
        double distY1 = length * Math.sin(angle+15);
        grap2D.drawLine((int) arrowCoords[0],(int)arrowCoords[1],(int)(arrowCoords[0]+distX1*0.7),(int)(arrowCoords[1]+distY1*0.7));
        double distX2 = length * Math.cos(angle+10);
        double distY2 = length * Math.sin(angle+10);
        grap2D.drawLine((int) arrowCoords[0],(int)arrowCoords[1],(int)(arrowCoords[0]+distX2*0.7),(int)(arrowCoords[1]+distY2*0.7));
        // reset any transformation
        grap2D.setTransform(new AffineTransform());
    }
}

