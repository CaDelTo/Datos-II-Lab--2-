import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GraphPanel extends JPanel implements MouseListener {
    private Node startNode;
    private Node endNode;
    private JButton deleteButton;
    private Boolean deleteMode = false;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    public Boolean bkr = false;
    int edgeWeight;
    private URL url = getClass().getResource("MapaDark.png");
    Image image = new ImageIcon(url).getImage();

    public GraphPanel() {
        
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        setBackground(Color.black);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        addMouseListener(this);
        JButton btnSearch = new JButton("Búsqueda");
        btnSearch.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showSearchDialog();
        }
        });
        btnSearch.setBorder(new LineBorder(Color.white));
        btnSearch.setPreferredSize(new Dimension(100, 50));
        add(btnSearch, BorderLayout.EAST);
        deleteButton = new JButton("Borrar nodos");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (deleteMode) {
                        deleteMode = false;
                        deleteButton.setText("Borrar nodos");
                    } else {
                        deleteMode = true;
                        deleteButton.setText("Cancelar");
                    }
                }
        });
        deleteButton.setBorder(new LineBorder(Color.white));
        deleteButton.setPreferredSize(new Dimension(100, 50));
        add(deleteButton);
    }       
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        Graphics2D g2 = (Graphics2D)g;
        for (Edge edge : edges) {
            g.setColor(edge.getColor());
            g2.setStroke(edge.getThickness());
            g2.drawLine(edge.getStart().getX(), edge.getStart().getY(), edge.getEnd().getX(), edge.getEnd().getY());
            g.drawString(String.valueOf(edge.getWeigth()), (edge.getStart().getX() + edge.getEnd().getX())/2 , (edge.getStart().getY()+edge.getEnd().getY())/2 - 10);
        }
        for (Node node : nodes) {
            g.setColor(node.getColor());
            g.fillOval(node.getX() - Node.RADIUS, node.getY() - Node.RADIUS, Node.RADIUS * 2, Node.RADIUS * 2);
            g.setFont(node.getFont());
            g2.setColor(Color.white);
            g.drawString(node.getName(), node.getX()-g.getFontMetrics().stringWidth(node.getName())/2, node.getY());
        }
        
            
        
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (deleteMode) {
                Node selectedNode = getNodeAt(e.getX(), e.getY());
                if (selectedNode != null) {
                    deleteNode(selectedNode);
                }
            } else {
                String nodeName = null;
                Node existingNode = null;
                while (existingNode != null || nodeName == null || nodeName.equals("")) {
                    nodeName = JOptionPane.showInputDialog("Nombre del nodo:");
                    existingNode = getNodeByName(nodeName);
                    if (existingNode != null) {
                        JOptionPane.showMessageDialog(this, "Ya existe un nodo con ese nombre. Por favor, seleccione otro:");
                    }
                }
                Node overlappingNode = getNodeAt(e.getX(), e.getY());
                if (overlappingNode != null) {
                    JOptionPane.showMessageDialog(this, "Ya hay un nodo en esa posición. Por favor, seleccione otra posición.");
                    
                }else{
                    Node node = new Node(e.getX(), e.getY(), nodeName);
                    nodes.add(node);
                    repaint();
                }
                
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (startNode == null) {
                startNode = getNodeAt(e.getX(), e.getY());
            } else {
                String string = " ";
                Boolean a = false;
                while (a != true){
                    string = JOptionPane.showInputDialog("Ingrese el peso de la arista:");
                    try {
                        edgeWeight = Integer.parseInt(string);

                    } catch (Exception b) {
                        string = JOptionPane.showInputDialog("El valor debe ser un numero: ");
                    }
                    a = true;
                }
                endNode = getNodeAt(e.getX(), e.getY());
                if (startNode != null && endNode != null && startNode != endNode) {
                    Edge edge = new Edge(startNode, endNode, edgeWeight);
                    edges.add(edge);
                    startNode.connections.put(endNode, string);
                    endNode.connections.put(startNode, string);
                    startNode = null;
                    endNode = null;
                    repaint();
                }
            }
        }      
    }
    private void deleteNode(Node node) {
        // Remove any edges connected to the node
        Iterator<Edge> edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            if (edge.hasNode(node)) {
                edgeIterator.remove();
            }
        }
        // Remove the node from the list of nodes
        nodes.remove(node);
        repaint();
    }
    private Node getNodeByName(String name) {
        for (Node node : nodes) {
            if (node.getName() != null && (node.getName().equals(name) || node.getName2().equals(name))) {
                return node;
            }
        }
        return null;
    }
    private Node getNodeAt(int x, int y) {
        for (Node node : nodes) {
            if (Math.sqrt(Math.pow(node.getX() - x, 2) + Math.pow(node.getY() - y, 2)) <= Node.RADIUS) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    private void showSearchDialog() {
        String nodeNameA = null;
        String nodeNameB = null;
        while (nodeNameA == null || nodeNameA.equals("")) {
            nodeNameA = JOptionPane.showInputDialog("Nombre del nodo de origen:");
            if (nodeNameA == null) {
                return;
            }
            Node nodeA = getNodeByName(nodeNameA);
            if (nodeA == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el nodo especificado. Por favor, ingrese otro nombre.");
                nodeNameA = null;
            }
        }
        while (nodeNameB == null || nodeNameB.equals("")) {
            nodeNameB = JOptionPane.showInputDialog("Nombre del nodo de destino:");
            if (nodeNameB == null) {
                return;
            }
            Node nodeB = getNodeByName(nodeNameB);
            if (nodeB == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el nodo especificado. Por favor, ingrese otro nombre.");
                nodeNameB = null;
            }
        }
        if(nodeNameA.equals(nodeNameB)){
            JOptionPane.showMessageDialog(this, "El nodo origen y el nodo destino ingresados son el mismo. Intenlo de nuevo.");
        }else{
            List<Node> shortestPath = findShortestPath(nodeNameA, nodeNameB);
            if (shortestPath != null) {
                updatePath(shortestPath);
                JOptionPane.showMessageDialog(this, "El camino más corto es:\n" + getNames(shortestPath) + " y su costo es: " + getShortestPathWeight(nodeNameA, nodeNameB));
                resetColors();
                
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un camino entre los nodos especificados.");
            }
        }
        
    }
    public int getShortestPathWeight(String startNode, String endNode) {
        List<Node> shortestPath = findShortestPath(startNode, endNode);
        int totalWeight = 0;
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node currentNode = shortestPath.get(i);
            Node nextNode = shortestPath.get(i + 1);
            Edge edge = getEdgeBetween(currentNode, nextNode);
            totalWeight += edge.getWeigth();
        }
        return totalWeight;
    }
    private List<Node> findShortestPath(String nodeNameA, String nodeNameB) {
        int n = nodes.size();
        double[][] distances = new double[n][n];
        int[][] nextNodes = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                    nextNodes[i][j] = -1;
                } else {
                    Node nodeI = nodes.get(i);
                    Node nodeJ = nodes.get(j);
                    Edge edge = getEdgeBetween(nodeI, nodeJ);
                    if (edge != null) {
                        distances[i][j] = edge.getWeigth();
                        nextNodes[i][j] = j;
                    } else {
                        distances[i][j] = Double.POSITIVE_INFINITY;
                        nextNodes[i][j] = -1;
                    }
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        nextNodes[i][j] = nextNodes[i][k];
                    }
                }
            }
        }
        Node nodeA = getNodeByName(nodeNameA);
        Node nodeB = getNodeByName(nodeNameB);
        if (nodeA == null || nodeB == null) {
            return null;
        }
        int indexA = nodes.indexOf(nodeA);
        int indexB = nodes.indexOf(nodeB);
        if (indexA == -1 || indexB == -1) {
            return null;
        }
        List<Node> shortestPath = new ArrayList<>();
        shortestPath.add(nodeA);
        while (indexA != indexB) {
            if (nextNodes[indexA][indexB] == -1) {
                return null;
            }
            indexA = nextNodes[indexA][indexB];
            shortestPath.add(nodes.get(indexA));
        }
        return shortestPath;
    }
    private Edge getEdgeBetween(Node nodeA, Node nodeB) {
        for (Edge edge : edges) {
            if ((edge.getStart().equals(nodeA) && edge.getEnd().equals(nodeB)) ||
                    (edge.getStart().equals(nodeB) && edge.getEnd().equals(nodeA))) {
                return edge;
            }
        }
        return null;
    }
    private void updatePath(List<Node> path) {
        for (Node node : path) {
            node.setColor(Color.RED);

        }
        for (int i = 0; i < path.size() - 1; i++) {
            Node startNode = path.get(i);
            Node endNode = path.get(i + 1);
            Edge edge = getEdgeBetween(startNode, endNode);
            if (edge != null) {
                edge.setThickness( new BasicStroke(5));
                edge.setColor(Color.RED);
            }
        }
        repaint();
    }
    private List<String> getNames(List<Node> Nodos){
        List<String> names = new ArrayList<>();
        for(Node node: Nodos){
            names.add(node.getName());
        }
        return names;
    }
    private void resetColors() {
        for (Node node : nodes) {
            node.setColor(Color.gray);
        }
        for (Edge edge : edges) {
            edge.setColor(Color.white);
            edge.setThickness(new BasicStroke(1));
        }
        repaint();
    }
}
