package lab5;

import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeDemo {
    private JTree tree;

    public TreeDemo(ConcurrentLinkedDeque<Jar> coll, String rootName, String first, String second){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
        DefaultMutableTreeNode Empty = new DefaultMutableTreeNode(first);
        DefaultMutableTreeNode Full = new DefaultMutableTreeNode(second);
        root.add(Empty);
        root.add(Full);
        createJar(coll, root);
        tree = new JTree(root);
        /*ImageIcon leafIcon;
        leafIcon = new ImageIcon(".\\img\\Банка.png");*/
    }

    public void createJar(ConcurrentLinkedDeque<Jar> coll, DefaultMutableTreeNode root) {
        DefaultMutableTreeNode Empty = (DefaultMutableTreeNode) root.getFirstChild();
        DefaultMutableTreeNode Full = (DefaultMutableTreeNode) root.getLastChild();
        coll.forEach((item)->{
            if(item.IsEmpty){
            	//Имя??
                Empty.add(new DefaultMutableTreeNode(item.Name));
            }
            else {
                Full.add(new DefaultMutableTreeNode(item.Name));
            }
        });
    }

    public JTree getTree(){
        return tree;
    }
}

