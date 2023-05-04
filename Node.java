import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;

public class Node {

    public static final int RADIUS = 20;
    private int x;
    private int y;
    private Color color;
    private String name;
    private String name2;
    private Font font;

    public Hashtable<Node, String> connections;
    public Node(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.color = Color.GRAY;
        this.name = name(name);
        this.name2 = name;
        this.connections = new Hashtable<>();
        this.font = new Font("Arial Narrow", Font.BOLD, 14);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
        
    }

    public Font getFont() {
        return font;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    private String name(String a){
        String[] Array = null;
        Array = a.split("");
        String b = "";
        
        if (Array.length>5){
            b = b + Array[0];
            for(int i = 0; i<Array.length; i++){
                
                if(Array[i].equals(" ")){
                    if (i != Array.length - 1){
                        b = b + Array[i+1];
                    }
                    
                }
                System.out.println(b);
            }
            return b;
        }else{
            return a;
        }
        
    }
}