/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphsassignment.exercise1;

import graphsassignment.model.Vertex;
import graphsassignment.model.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas Pereira e Renan Bertoldo
 */
public class MainDijkstra {

    public static void main(String[] args) {

        File file = new File("C:\\temp\\questao1.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "O arquivo C:\\temp\\questao1.txt não foi encontrado!");
        } else {
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                if (!lines.isEmpty()) {
                    Graph graph = new Graph();
                    Vertex[] vertices = null;
                    String initialVertex = null;
                    String finalVertex = null;
                    int indexLine = 0;
                    for (String line : lines) {
                        String lineTr = line.trim();
                        if (!lineTr.trim().isEmpty()) {
                            if (vertices == null) {
                                graph = new Graph();
                                int number = Integer.valueOf(lineTr);
                                vertices = new Vertex[number];
                                for (int i = 0; i < number; i++) {
                                    Vertex ver = new Vertex(Character.toString((char) (65+i)));
                                    vertices[i] = ver;
                                    graph.addVertex(ver, true);
                                }
                            } else {
                                String[] items = line.split(" ");
                                if (initialVertex == null) {
                                    initialVertex = items[0];
                                    finalVertex = items[1];
                                    indexLine = 0;
                                } else {
                                    for (int indexColumn = 0; indexColumn < items.length; indexColumn++) {
                                        String item = items[indexColumn];
                                        if (!"I".equalsIgnoreCase(item)) {                                            
                                            graph.addEdge(vertices[indexLine], vertices[indexColumn], Integer.valueOf(item));
                                        }
                                    }
                                    indexLine++;
                                }
                            }
                        }
                    }
                    
                    if (initialVertex != null) {
                        Dijkstra dijkstra = new Dijkstra(graph, initialVertex);
                        List<Vertex> listVertex = dijkstra.getPathTo(finalVertex);
                        int distance = dijkstra.getDistanceTo(finalVertex);
                        
                        StringBuilder sb = new StringBuilder("Caminho mínimo: ");
                        
                        for (int indexVert = 0; indexVert < listVertex.size(); indexVert ++ ) {
                            Vertex vert = listVertex.get(indexVert);
                            sb.append(vert.getName());
                            if (indexVert != listVertex.size()-1) {                                
                                sb.append("->");
                            }
                        }
                        sb.append("\nCusto: ").append(distance);
                        File fileWrite = new File("C:\\temp\\resposta_questao1.txt");
                        if (!fileWrite.exists()) {
                            fileWrite.createNewFile();
                        }
                        Files.write(fileWrite.toPath(), sb.toString().getBytes());
                        JOptionPane.showMessageDialog(null, "Resposta gerada em C:\\temp\\resposta_questao1.txt");
                        System.out.println(sb.toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O arquivo C:\\temp\\questao1.txt está vazio!");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainDijkstra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainDijkstra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
