import java.awt.*;

public class ShapeInfo {
    public enum Type { RECTANGLE, OVAL, LINE }

    Type type;
    int width, height;
    Color color;

    ShapeInfo(Type type, int radius, Color color) {
        this.type = type;
        this.width  = radius * 2;
        this.height = radius * 2;
        this.color = color;
    }

    void setRadius(float radius)
    {
        this.width = (int)(radius * 2);
        this.height = (int)(radius * 2);
    }
}