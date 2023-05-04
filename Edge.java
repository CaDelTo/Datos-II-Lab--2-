import java.awt.BasicStroke;
import java.awt.Color;

public class Edge {

    private Node start;
    private Node end;
    private int weigth;
    private Color color;
    private BasicStroke Thickness;
    public Edge(Node start, Node end, int weight) {
        this.start = start;
        this.end = end;
        this.weigth = weight;
        this.color = color.white;
        this.Thickness = new BasicStroke(1);
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
    public boolean hasNode(Node node) {
        if (start == node || end == node){
            return true;
        }else{
            return false;
        }
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BasicStroke getThickness() {
        return Thickness;
    }

    public void setThickness(BasicStroke thickness) {
        Thickness = thickness;
    }
    

}