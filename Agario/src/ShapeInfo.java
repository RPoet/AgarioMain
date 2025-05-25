import java.awt.*;

public class ShapeInfo {
    public enum Type { RECTANGLE, OVAL, LINE }

    Type type;
    int width, height;
    Color color;
    float scale;

    ShapeInfo(Type type, int radius, float scale, Color color) {
        this.type = type;
        this.width  = radius * 2;
        this.height = radius * 2;
        this.color = color;
        this.scale = scale;
    }

    void setRadius(float radius)
    {
        this.width = (int)(radius * 2);
        this.height = (int)(radius * 2);
    }
}